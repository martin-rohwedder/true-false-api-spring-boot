package dk.martinrohwedder.tfgapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object used when a user is adding or updating a question
 */
@Getter
@Setter
public class QuestionRequest {
    @JsonProperty("questionText")
    private String text;

    @JsonProperty("correctAnswer")
    private boolean answer;

    @JsonProperty("categoryId")
    private long categoryId;
}
