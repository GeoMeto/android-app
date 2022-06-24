package com.tu.challengeyourself.models;

public class UserComment extends BaseEntity {
    private int id;
    private String content;
    private SharedChallenge sharedChallenge;
}
