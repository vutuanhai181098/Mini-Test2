package com.example.minitest2.service;

import com.example.minitest2.dto.UserDto;
import com.example.minitest2.entity.User;
import com.example.minitest2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found user"));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
