package com.ssafy.utf.api.service;

import com.ssafy.utf.api.request.UserJoinReq;
import com.ssafy.utf.api.request.UserUpdateReq;
import com.ssafy.utf.db.entity.user.User;
import com.ssafy.utf.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User joinUser(UserJoinReq userJoinReq) {
        User user = new User();
        user.setUserName(userJoinReq.getUserName());
        user.setSocialLoginType(userJoinReq.getSocialLoginType());
        user.setSocialId(userJoinReq.getSocialId());
        user.setEmail(userJoinReq.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserUpdateReq userUpdateReq) {
        User user = userRepository.findById(userUpdateReq.getUserId());
        user.setUserName(userUpdateReq.getUserName());
        user.setEmail(userUpdateReq.getEmail());
        userRepository.save(user);
        return user;
    }
}
