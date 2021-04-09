package com.mensgratiae.backend.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USERS_API = "/users/**";
    public static final String SWAGGER_UI_URL = "/swagger-ui.html";
    public static final String SWAGGER_UI_WEBJARS = "/webjars/**";
    public static final String SWAGGER_UI_RESOURCES = "/swagger-resources/**";
    public static final String SWAGGER_UI_V2 = "/v2/**";
}
