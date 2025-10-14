package dk.martinrohwedder.tfgapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class CategoryDto {
    @JsonProperty("categoryId")
    private long id;

    @JsonProperty("categoryTitle")
    private String title;

    @JsonProperty("categoryDescription")
    private String description;

    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    private Date updatedAt;
}
