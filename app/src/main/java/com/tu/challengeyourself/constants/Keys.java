package com.tu.challengeyourself.constants;

public class Keys {
    public static final String TOKEN = "token";
    public static final String AUTH = "Authorization";


    public static final String BASE_URL = "http://10.0.2.2:8081/api/";
    public static final String BASE_SECURE_URL = BASE_URL + "secure/";

    public static final String LOGIN_URL = BASE_URL + "login";
    public static final String VALIDATE_URL = BASE_URL + "validate";
    public static final String CREATE_USER_URL = BASE_URL + "user";

    public static final String LOGOUT_URL = BASE_SECURE_URL + "logout";
    public static final String GET_USERNAME_URL = BASE_SECURE_URL + "username";
    public static final String CHANGE_PASSWORD_URL = BASE_SECURE_URL + "change/password";
    public static final String CHANGE_USERNAME_URL = BASE_SECURE_URL + "change/username";
    public static final String DELETE_ACCOUNT_URL = BASE_SECURE_URL + "delete/account";
}
