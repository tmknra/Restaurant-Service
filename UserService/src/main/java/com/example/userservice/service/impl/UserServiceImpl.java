package com.example.userservice.service.impl;

import com.example.userservice.dto.in.UserInDto;
import com.example.userservice.dto.out.UserOutDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserOutDto createUser(UserInDto user) {
        UserEntity save = userRepository.save(userMapper.userInDtoToUserEntity(user));
        System.out.println(save);
        System.out.println(userMapper.userEntityToUserOutDto(save));
        return userMapper.userEntityToUserOutDto(save);
    }

    @Override
    public UserOutDto updateUser(UserInDto user, long userId) throws UserNotFoundException {
        UserEntity userById = findUserById(userId);
        if (user.getLastname()!=null)
            userById.setLastname(user.getLastname());
        if (user.getName()!=null)
            userById.setName(user.getName());
        if (user.getPatronymic()!=null)
            userById.setPatronymic(user.getPatronymic());
        if (user.getEmail()!=null)
            userById.setEmail(user.getEmail());
        if (user.getRegistration_date()!=null)
            userById.setRegistrationDate(user.getRegistration_date());
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

    private UserEntity findUserById(long userId) throws UserNotFoundException {
        Optional<UserEntity> byId = userRepository.findById(userId);
        if (byId.isEmpty())
            throw new UserNotFoundException();
        return byId.get();
    }
}
