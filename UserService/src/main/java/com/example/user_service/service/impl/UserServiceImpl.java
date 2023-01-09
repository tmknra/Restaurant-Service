package com.example.user_service.service.impl;

import com.example.user_service.dto.DeleteRestaurantOwnerDto;
import com.example.user_service.dto.UpdateRestaurantOwnerDto;
import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExists {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExists("User already exist!");
        }
        UserEntity save = userRepository.save(userMapper.userInDtoToUserEntity(user));
        return userMapper.userEntityToUserOutDto(save);
    }

    @Override
    @Transactional
    public UserOutDto updateUser(UserInDto user, long userId) throws UserNotFoundException, UserAlreadyExists {
        UserEntity userById = findUserById(userId);
        if (user.getLastname() != null)
            userById.setLastname(user.getLastname());
        if (user.getName() != null)
            userById.setName(user.getName());
        if (user.getPatronymic() != null)
            userById.setPatronymic(user.getPatronymic());
        if (user.getEmail() != null) {
            if (userRepository.existsByEmail(user.getEmail()))
                throw new UserAlreadyExists("This email is already taken: " + user.getEmail());
            else
                userById.setEmail(user.getEmail());
        }
        return userMapper.userEntityToUserOutDto(userRepository.save(userById));
    }

    // TODO: add flag isDeleted
    @Override
    public ResponseEntity<?> deleteUser(Long userId) throws UserNotFoundException {
        userRepository.delete(findUserById(userId));
        rabbitTemplate.convertAndSend("deleteOwnerQueue", new DeleteRestaurantOwnerDto(userId));
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "User successfully deleted.");
        return ResponseEntity.ok(message);
    }

    @Override
    public UserOutDto getUserById(Long id) throws UserNotFoundException {
        UserEntity userById = findUserById(id);
        return userMapper.userEntityToUserOutDto(userById);
    }

    @Override
    public List<UserOutDto> getAllUsers() {
        List<UserEntity> all = userRepository.findAll();
        List<UserOutDto> outDtoList = new ArrayList<>();
        for (UserEntity userEntity : all) {
            outDtoList.add(userMapper.userEntityToUserOutDto(userEntity));
        }
        return outDtoList;
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePasswordById(ChangePasswordInDto changePasswordInDto) throws UserNotFoundException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(changePasswordInDto.getEmail());
        if (byEmail.isEmpty())
            throw new UserNotFoundException("User does not exist with email: " + changePasswordInDto.getEmail());
        UserEntity userEntity = byEmail.get();

        if (!changePasswordInDto.getOldPassword().matches(userEntity.getPassword()))
            throw new RuntimeException("Invalid old password!");
        userEntity.setPassword(changePasswordInDto.getNewPassword());
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
