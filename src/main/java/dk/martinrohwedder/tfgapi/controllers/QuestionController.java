package dk.martinrohwedder.tfgapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    @GetMapping("/")
    public String index() {
        return "Hello from Spring Boot Application!!";
    }
}
