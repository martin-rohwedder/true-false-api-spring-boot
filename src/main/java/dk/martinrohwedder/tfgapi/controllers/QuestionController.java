package dk.martinrohwedder.tfgapi.controllers;

import dk.martinrohwedder.tfgapi.dtos.QuestionDto;
import dk.martinrohwedder.tfgapi.mappers.QuestionMapper;
import dk.martinrohwedder.tfgapi.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/${spring.application.api-version}/questions")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<Iterable<QuestionDto>> findAllQuestions(@RequestParam(name = "categoryTitle", required = false) String categoryTitle) {
        if (categoryTitle != null) {
            var questions = questionRepository.findAllByCategoryTitle(categoryTitle)
                    .stream()
                    .map(questionMapper::toDto)
                    .toList();
            if (questions.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(questions);
            }
        }

        return ResponseEntity.ok(questionRepository.findAll()
                .stream()
                .map(questionMapper::toDto)
                .toList());
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
