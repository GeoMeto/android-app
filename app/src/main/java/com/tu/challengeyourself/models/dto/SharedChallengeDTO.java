package com.tu.challengeyourself.models.dto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.List;

public class SharedChallengeDTO {
    @Expose
    private int id;
    @Expose
    private Integer likeCount;
    @Expose
    private Boolean isDeleted;
    @Expose
    private Boolean isLiked;
    @Expose
    private int userId;
    @Expose
    private CompletedChallengeDTO completedChallengeDTO;
    @Expose
    private List<UserCommentDTO> userCommentDTO;

    @Expose(serialize = false, deserialize = true)
    private LocalDateTime createdAt;


    public SharedChallengeDTO() {

    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CompletedChallengeDTO getCompletedChallengeDTO() {
        return completedChallengeDTO;
    }

    public void setCompletedChallengeDTO(CompletedChallengeDTO completedChallengeDTO) {
        this.completedChallengeDTO = completedChallengeDTO;
    }

    public List<UserCommentDTO> getUserCommentDTO() {
        return userCommentDTO;
    }

    public void setUserCommentDTO(List<UserCommentDTO> userCommentDTO) {
        this.userCommentDTO = userCommentDTO;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
