package dk.martinrohwedder.tfgapi.controllers;

import dk.martinrohwedder.tfgapi.dtos.CategoryDto;
import dk.martinrohwedder.tfgapi.dtos.CategoryRequest;
import dk.martinrohwedder.tfgapi.mappers.CategoryMapper;
import dk.martinrohwedder.tfgapi.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // GET: /api/categories
    @GetMapping
    public ResponseEntity<Iterable<CategoryDto>> findAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList());
    }

    // GET: /api/categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable long id) {
        var category = categoryRepository.findById(id);
        return category.map(value -> ResponseEntity.ok(categoryMapper.toDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: /api/categories
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryRequest request, UriComponentsBuilder uriBuilder) {
        // Map request to category and save it
        var category = categoryMapper.toEntity(request);
        categoryRepository.save(category);
        var categoryDto = categoryMapper.toDto(category);

        // Create header location with the uri for fetching the new question
        var uriLocation = uriBuilder.path("/api/categories/{id}").buildAndExpand(categoryDto.getId()).toUri();

        return ResponseEntity.created(uriLocation).body(categoryDto);
    }

    // PUT: /api/categories/{id}
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long id, @RequestBody CategoryRequest request) {
        // Fetch the category
        var category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Map request and update the category
        var updatedCategory = categoryMapper.toEntity(request);
        updatedCategory.setId(category.get().getId());
        categoryRepository.save(updatedCategory);

        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }

    // DELETE: /api/categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCategory(@PathVariable long id) {
        var category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
