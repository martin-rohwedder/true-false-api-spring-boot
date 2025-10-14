package dk.martinrohwedder.tfgapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class QuestionDto {
    @JsonProperty("questionId")
    private long id;

    @JsonProperty("questionText")
    private String text;

    @JsonProperty("correctAnswer")
    private boolean answer;

    @JsonProperty("category")
    private CategoryDto category;

    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    private Date updatedAt;
}
