package com.tu.challengeyourself.models.dto;

public class UserCommentDTO {
    private int id;
    private String content;

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
}
