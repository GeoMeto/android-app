package com.tu.challengeyourself.models.dto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class CompletedChallengeDTO {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String measurement;
    @Expose
    private String comment;
    @Expose
    private String description;
    @Expose
    private Boolean isPositive;
    @Expose
    private Integer result;
    @Expose
    private Integer target;
    @Expose
    private Boolean isShared;
    @Expose
    private Boolean isCompleted;
    @Expose
    private Boolean isDeleted;
    @Expose
    private Integer userId;

    public CompletedChallengeDTO() {
    }

    public CompletedChallengeDTO(int id, String name, String measurement, String comment, String description, Boolean isPositive, int result, int target, Boolean isShared, Boolean isCompleted, Boolean isDeleted, int userId) {
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
}
