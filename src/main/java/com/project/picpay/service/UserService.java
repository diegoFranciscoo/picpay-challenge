package com.project.picpay.service;

import com.project.picpay.domain.user.User;
import com.project.picpay.dto.UserPostDTO;
import com.project.picpay.exceptions.userException.UserNotFound.UserNotFoundException;
import com.project.picpay.mapper.UserMapper;
import com.project.picpay.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public User save(UserPostDTO userDTO) {
        return userRepository.save(mapper.toUser(userDTO));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
}
