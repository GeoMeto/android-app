package com.tu.challengeyourself.models.dto;

import java.time.LocalDateTime;

public class CompletedChallengeDTO {

    private Integer id;
    private String name;
    private String measurement;
    private String comment;
    private String description;
    private Boolean isPositive;
    private Integer result;
    private Integer target;
    private Boolean isShared;
    private Boolean isCompleted;
    private Boolean isDeleted;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompletedChallengeDTO() {
    }

    public CompletedChallengeDTO(int id, String name, String measurement, String comment, String description, Boolean isPositive, int result, int target, Boolean isShared, Boolean isCompleted, Boolean isDeleted, int userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.measurement = measurement;
        this.comment = comment;
        this.description = description;
        this.isPositive = isPositive;
        this.result = result;
        this.target = target;
        this.isShared = isShared;
        this.isCompleted = isCompleted;
        this.isDeleted = isDeleted;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPositive() {
        return isPositive;
    }

    public void setPositive(Boolean positive) {
        isPositive = positive;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
