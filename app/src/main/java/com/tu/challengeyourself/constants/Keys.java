package com.tu.challengeyourself.constants;

public class Keys {
    public static final String TOKEN = "token";
    public static final String AUTH = "Authorization";
    //Base urls
    public static final String BASE_URL = "http://10.0.2.2:8081/api/";
    public static final String BASE_SECURE_URL = BASE_URL + "secure/";
    //Login endpoints
    public static final String LOGIN_URL = BASE_URL + "login";
    public static final String VALIDATE_URL = BASE_URL + "validate";
    public static final String CREATE_USER_URL = BASE_URL + "user";
    //User endpoints
    public static final String LOGOUT_URL = BASE_SECURE_URL + "logout";
    public static final String GET_USERNAME_URL = BASE_SECURE_URL + "username";
    public static final String CHANGE_PASSWORD_URL = BASE_SECURE_URL + "change/password";
    public static final String CHANGE_USERNAME_URL = BASE_SECURE_URL + "change/username";
    public static final String DELETE_ACCOUNT_URL = BASE_SECURE_URL + "delete/account";
    //Challenge endpoints
    public static final String BASE_CHALLENGES_URL = BASE_SECURE_URL + "challenges";
    public static final String CREATE_CHALLENGES_URL = BASE_CHALLENGES_URL + "/new";
    public static final String GET_CHALLENGES_URL = BASE_CHALLENGES_URL + "/completed";
    public static final String COMPLETE_CHALLENGES_URL = BASE_CHALLENGES_URL + "/complete";
    public static final String DELETE_CHALLENGES_URL = BASE_CHALLENGES_URL + "/%d";
    //Sharing endpoints
    public static final String BASE_SHARING_URL = BASE_SECURE_URL + "share/";
    public static final String GET_ALL_SHARING_URL = BASE_SHARING_URL + "all";
    public static final String GET_HOT_SHARING_URL = BASE_SHARING_URL + "hot";
    public static final String GET_USER_SHARING_URL = BASE_SHARING_URL + "user";
    public static final String CREATE_SHARING_URL = BASE_SHARING_URL + "new";
    public static final String LIKE_SHARING_URL = BASE_SHARING_URL + "like/%d";
    public static final String DELETE_SHARING_URL = BASE_SHARING_URL + "delete/%d";
}
