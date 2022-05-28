package com.tu.challengeyourself.models;

public class Challenge {

    private String category;
    private String challengeName;
    private String unit;
    private String description;
    private int completedCount;
    private int targetCount;

    public Challenge(String category, String challengeName, String unit, String description, int completedCount, int targetCount) {
        this.category = category;
        this.challengeName = challengeName;
        this.unit = unit;
        this.description = description;
        this.completedCount = completedCount;
        this.targetCount = targetCount;
    }

    public Challenge(String challengeName, String description, int completedCount, int targetCount) {
        this.challengeName = challengeName;
        this.description = description;
        this.completedCount = completedCount;
        this.targetCount = targetCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }
}
