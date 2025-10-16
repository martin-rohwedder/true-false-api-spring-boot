package dk.martinrohwedder.tfgapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Request object used when a user is adding or updating a category
 */
@Getter
@Setter
public class CategoryRequest {
    @JsonProperty("categoryTitle")
    private String title;

    @JsonProperty("categoryDescription")
    private String description;
}
