package dk.martinrohwedder.tfgapi.controllers;

import dk.martinrohwedder.tfgapi.dtos.QuestionRequest;
import dk.martinrohwedder.tfgapi.dtos.QuestionDto;
import dk.martinrohwedder.tfgapi.mappers.QuestionMapper;
import dk.martinrohwedder.tfgapi.repositories.CategoryRepository;
import dk.martinrohwedder.tfgapi.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final CategoryRepository categoryRepository;

    // GET: /api/questions
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

    // GET /api/questions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> findQuestionById(@PathVariable long id) {
        var question = questionRepository.findById(id);
        if (question.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(questionMapper.toDto(question.get()));
    }

    // POST: /api/questions
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody QuestionRequest request, UriComponentsBuilder uriBuilder) {
        // Fetch category
        var category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Map request to question and save it.
        var question = questionMapper.toEntity(request);
        question.setCategory(category.get());
        questionRepository.save(question);
        var questionDto = questionMapper.toDto(question);

        // Create header location with the uri for fetching the new question
        var uriLocation = uriBuilder.path("/api/questions/{id}").buildAndExpand(questionDto.getId()).toUri();

        return ResponseEntity.created(uriLocation).body(questionDto);
    }

    // DELETE: /api/questions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeQuestion(@PathVariable long id) {
        var question = questionRepository.findById(id);
        if (question.isPresent()) {
            questionRepository.delete(question.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
