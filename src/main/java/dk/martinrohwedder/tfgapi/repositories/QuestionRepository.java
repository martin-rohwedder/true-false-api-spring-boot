package dk.martinrohwedder.tfgapi.repositories;

import dk.martinrohwedder.tfgapi.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByCategoryTitle(String categoryTitle);
}
