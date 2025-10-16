package dk.martinrohwedder.tfgapi.mappers;

import dk.martinrohwedder.tfgapi.dtos.CategoryDto;
import dk.martinrohwedder.tfgapi.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}
