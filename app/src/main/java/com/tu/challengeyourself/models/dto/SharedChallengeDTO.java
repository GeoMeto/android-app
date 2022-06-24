package com.tu.challengeyourself.models.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SharedChallengeDTO {

    private int id;
    private Boolean isDeleted;
    private int userId;
    private CompletedChallengeDTO completedChallengeDTO;
    private List<UserCommentDTO> userCommentDTO;
    private LocalDateTime createdAt;

}
