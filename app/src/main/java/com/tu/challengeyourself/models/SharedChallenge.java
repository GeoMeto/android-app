package com.tu.challengeyourself.models;

import com.tu.challengeyourself.models.dto.CompletedChallengeDTO;

public class SharedChallenge extends BaseEntity {
    private Integer id;
    private boolean isDeleted;
    private User user;
    private CompletedChallengeDTO completedChallenge;
}
