package dk.martinrohwedder.tfgapi.mappers;

import dk.martinrohwedder.tfgapi.dtos.QuestionDto;
import dk.martinrohwedder.tfgapi.entities.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionDto toDto(Question question);
}
