package com.tu.challengeyourself.models.dto;

import com.google.gson.annotations.Expose;

public class LikesDTO {

    @Expose
    private int likesCount;
    @Expose
    private boolean isLiked;

    public LikesDTO() {
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
