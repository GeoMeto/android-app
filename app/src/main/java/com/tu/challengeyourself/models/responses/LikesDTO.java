package com.tu.challengeyourself.models.responses;

import com.google.gson.annotations.Expose;

public class LikesDTO {

    @Expose
    private int likesCount;
    @Expose
    private boolean liked;

    public LikesDTO() {
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
