package dk.martinrohwedder.tfgapi.repositories;

import dk.martinrohwedder.tfgapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
