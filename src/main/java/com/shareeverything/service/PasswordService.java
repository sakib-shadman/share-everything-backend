package com.shareeverything.service;

public interface PasswordService {
    boolean matchPassword(String password, String hash);

    String encode(String password);
}
