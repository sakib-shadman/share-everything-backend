package com.shareeverything.security;

public class Constants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";
    public static final long JWT_TOKEN_VALIDITY = 12 * 60 * 60 * 1000;
    public static final String SECRET = "2q6Vykj2px";
}