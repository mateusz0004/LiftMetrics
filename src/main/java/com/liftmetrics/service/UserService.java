package com.liftmetrics.service;

import com.liftmetrics.model.User;
import com.liftmetrics.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register (User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
