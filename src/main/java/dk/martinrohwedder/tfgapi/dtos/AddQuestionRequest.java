package dk.martinrohwedder.tfgapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO used when a user is adding a new question
 */
@Getter
@Setter
public class AddQuestionRequest {
    @JsonProperty("questionText")
    private String text;

    @JsonProperty("correctAnswer")
    private boolean answer;

    @JsonProperty("categoryId")
    private long categoryId;
}
