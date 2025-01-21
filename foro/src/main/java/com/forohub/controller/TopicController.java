package com.forohub.controller;

import com.forohub.model.Topic;
import com.forohub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        // Verificar si el tópico existe
        Optional<Topic> topic = topicService.getTopicById(id);
        if (topic.isPresent()) {
            return ResponseEntity.ok(topic.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);  // El tópico no se encuentra
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        // Verificar si el tópico existe
        if (!topicService.isTopicExist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El tópico con el ID " + id + " no existe.");
        }

        // Eliminar el tópico
        topicService.deleteTopic(id);

        // Devolver respuesta con el mensaje de éxito
        return ResponseEntity.status(HttpStatus.OK) // Cambiar a 200 OK
                .body("Tópico eliminado con éxito.");
    }


    @GetMapping
    public List<Topic> getTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping
    public ResponseEntity<String> createTopic(@Valid @RequestBody Topic topic) {
        if (topicService.isDuplicateTopic(topic)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tópico con el mismo título y mensaje ya existe.");
        }
        topicService.createTopic(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tópico creado con éxito.");
    }

    // Endpoint para actualizar el tópico
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTopic(@PathVariable Long id, @Valid @RequestBody Topic topic) {
        // Verificar si el tópico existe
        if (!topicService.isTopicExist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tópico con el ID " + id + " no existe.");
        }

        // Verificar si el nuevo título y mensaje ya están en uso
        if (topicService.isDuplicateTopicForUpdate(id, topic)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tópico con el mismo título y mensaje ya existe.");
        }

        // Actualizar el tópico
        topicService.updateTopic(id, topic);
        return ResponseEntity.status(HttpStatus.OK).body("Tópico actualizado con éxito.");
    }
}
