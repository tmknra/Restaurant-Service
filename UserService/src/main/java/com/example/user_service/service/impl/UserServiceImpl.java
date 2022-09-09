package com.example.user_service.service.impl;

import com.example.user_service.dto.in.ChangePasswordInDto;
import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.exception.UserAlreadyExists;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserOutDto createUser(UserInDto user) throws UserAlreadyExists {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExists("User already exist!");
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
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

    @Override
    public void deleteUser(long userId) throws UserNotFoundException {
        userRepository.delete(findUserById(userId));
    }

    @Override
    public UserOutDto getUserById(long id) throws UserNotFoundException {
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
    public void changePasswordById(ChangePasswordInDto changePasswordInDto) throws UserNotFoundException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(changePasswordInDto.getEmail());
        if (byEmail.isEmpty())
            throw new UserNotFoundException("User does not exist with email: " + changePasswordInDto.getEmail());
        UserEntity userEntity = byEmail.get();

        if (!encoder.matches(changePasswordInDto.getOldPassword(), userEntity.getPassword()))
            throw new RuntimeException("Invalid old password!");
        userEntity.setPassword(encoder.encode(changePasswordInDto.getNewPassword()));
    }

    private UserEntity findUserById(long userId) throws UserNotFoundException {
        Optional<UserEntity> byId = userRepository.findById(userId);
        if (byId.isEmpty())
            throw new UserNotFoundException("User does not exist!");
        return byId.get();
    }
}
