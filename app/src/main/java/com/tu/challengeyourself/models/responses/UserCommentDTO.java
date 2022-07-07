package com.tu.challengeyourself.models.responses;

import com.google.gson.annotations.Expose;

public class UserCommentDTO {
    @Expose
    private int id;
    @Expose
    private String content;
    @Expose
    private String username;
    @Expose
    private boolean owner;

    public UserCommentDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
