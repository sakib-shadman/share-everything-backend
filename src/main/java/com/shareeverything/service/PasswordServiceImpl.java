package com.shareeverything.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService{

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public boolean matchPassword(String password, String hash) {
        if(password == null) {
            return false;
        }
        return encoder.matches(password, hash);
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}
