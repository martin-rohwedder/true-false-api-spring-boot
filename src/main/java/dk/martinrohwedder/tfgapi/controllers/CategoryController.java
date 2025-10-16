package dk.martinrohwedder.tfgapi.controllers;

import dk.martinrohwedder.tfgapi.dtos.CategoryDto;
import dk.martinrohwedder.tfgapi.mappers.CategoryMapper;
import dk.martinrohwedder.tfgapi.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable long id) {
        var category = categoryRepository.findById(id);
        return category.map(value -> ResponseEntity.ok(categoryMapper.toDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    
}
