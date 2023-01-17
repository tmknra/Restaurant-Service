package com.example.user_service.service.impl;

import com.example.user_service.dto.DeleteRestaurantOwnerDto;
import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.exception.InvalidPasswordException;
import com.example.user_service.exception.UserAlreadyExistsException;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
        log.info("Initialized UserService");
    }

    @Override
    @Transactional
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(String.format("User 'email:{%s}' already exist", user.getEmail()));
        }
        UserEntity save = userRepository.save(userMapper.userInDtoToUserEntity(user));
        log.info("Created new user 'email:{}'", save.getEmail());
        return userMapper.userEntityToUserOutDto(save);
    }

    @Override
    @Transactional
    public UserOutDto updateUser(UserInDto user, long userId)
            throws UserNotFoundException, UserAlreadyExistsException {
        UserEntity userById = findUserById(userId);

        userById.setLastname(Objects.requireNonNullElse(user.getLastname(), userById.getLastname()));
        userById.setName(Objects.requireNonNullElse(user.getName(), userById.getName()));
        userById.setPatronymic(Objects.requireNonNullElse(user.getPatronymic(), userById.getPatronymic()));

        if (!user.getEmail().equals(userById.getEmail()) && userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException(String.format("This 'email:{%s}' is already taken", user.getEmail()));
        else {
            userById.setEmail(Objects.requireNonNullElse(user.getEmail(), userById.getEmail()));
            log.info("User email changed '{}' -> '{}'", userById.getEmail(), user.getEmail());
        }
        log.info("Updated user info 'email:{}'", user.getEmail());
        return userMapper.userEntityToUserOutDto(userRepository.save(userById));
    }

    // TODO: add flag isDeleted
    @Override
    public ResponseEntity<?> deleteUser(Long userId) throws UserNotFoundException {
        userRepository.delete(findUserById(userId));
        log.info("Deleted user 'id:{}'", userId);

        rabbitTemplate.convertAndSend("deleteOwnerQueue", new DeleteRestaurantOwnerDto(userId));
        log.info("Sent message to RabbitMQ with deleteOwnerQueue");

        HashMap<String, String> message = new HashMap<>();
        message.put("message", "User successfully deleted.");
        return ResponseEntity.ok(message);
    }

    @Override
    public UserOutDto getUserById(Long id) throws UserNotFoundException {
        UserEntity userById = findUserById(id);
        log.info("Got user 'id:{}' from database and sent it to client", userById.getId());
        return userMapper.userEntityToUserOutDto(userById);
    }

    @Override
    public List<UserOutDto> getAllUsers(Pageable pageable) {
        List<UserEntity> all = userRepository.findAll(pageable).stream().toList();
        List<UserOutDto> outDtoList = new ArrayList<>();
        for (UserEntity userEntity : all) {
            outDtoList.add(userMapper.userEntityToUserOutDto(userEntity));
        }
        log.info("Got users list with pageable parameters 'page:{}', 'size:{}', 'sorted by:{}' and sent it to client",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return outDtoList;
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePasswordById(ChangePasswordInDto changePasswordInDto)
            throws UserNotFoundException, InvalidPasswordException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(changePasswordInDto.getEmail());
        if (byEmail.isEmpty())
            throw new UserNotFoundException("User does not exist with email: " + changePasswordInDto.getEmail());
        UserEntity userEntity = byEmail.get();

        if (!changePasswordInDto.getOldPassword().matches(userEntity.getPassword()))
            throw new InvalidPasswordException("Wrong old password!");
        userEntity.setPassword(changePasswordInDto.getNewPassword());
        log.info("Changed user 'email:{}' password", changePasswordInDto.getEmail());
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "Password successfully changed");
        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<?> updateUserToRestaurant(UpdateRestaurantOwnerDto updateRestaurantOwnerDto)
            throws UserNotFoundException {
        findUserById(updateRestaurantOwnerDto.getOldUserId());
        findUserById(updateRestaurantOwnerDto.getNewUserId());
        rabbitTemplate.convertAndSend("updateOwnerQueue", updateRestaurantOwnerDto);
        log.info("Sent message to RabbitMQ with updateOwnerQueue");
        HashMap<String, Long> message = new HashMap<>();
        message.put("newOwner", updateRestaurantOwnerDto.getNewUserId());
        return ResponseEntity.ok(message);
    }

    private UserEntity findUserById(long userId) throws UserNotFoundException {
        Optional<UserEntity> byId = userRepository.findById(userId);
        if (byId.isEmpty())
            throw new UserNotFoundException(String.format("User id %d does not exist!", userId));
        return byId.get();
    }
}
