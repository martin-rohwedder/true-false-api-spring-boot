package dk.martinrohwedder.tfgapi.repositories;

import dk.martinrohwedder.tfgapi.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
