package dk.martinrohwedder.tfgapi.controllers;

import dk.martinrohwedder.tfgapi.dtos.QuestionDto;
import dk.martinrohwedder.tfgapi.mappers.QuestionMapper;
import dk.martinrohwedder.tfgapi.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @GetMapping
    public Iterable<QuestionDto> findAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> findQuestionById(@PathVariable Long id) {
        var question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questionMapper.toDto(question));
    }
}
