package com.forohub.service;

import com.forohub.model.Topic;
import com.forohub.repository.TopicRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Data
@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    // Eliminar un tópico por ID
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    // Obtener todos los tópicos
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Crear un nuevo tópico
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    // Verificar si el tópico existe por ID
    public boolean isTopicExist(Long id) {
        return topicRepository.existsById(id);
    }

    // Verificar si el tópico ya existe (con el mismo título y mensaje)
    public boolean isDuplicateTopic(Topic topic) {
        return topicRepository.findByTitleAndMessage(topic.getTitle(), topic.getMessage()).isPresent();
    }

    // Verificar si el título y mensaje ya existen para la actualización (evitar duplicados)
    public boolean isDuplicateTopicForUpdate(Long id, Topic topic) {
        Optional<Topic> existingTopic = topicRepository.findByTitleAndMessage(topic.getTitle(), topic.getMessage());
        return existingTopic.isPresent() && !existingTopic.get().getId().equals(id);
    }

    // Actualizar un tópico
    public Topic updateTopic(Long id, Topic topic) {
        Optional<Topic> existingTopicOpt = topicRepository.findById(id);
        if (existingTopicOpt.isPresent()) {
            Topic existingTopic = existingTopicOpt.get();
            existingTopic.setTitle(topic.getTitle());
            existingTopic.setMessage(topic.getMessage());
            existingTopic.setStatus(topic.getStatus());
            existingTopic.setAuthor(topic.getAuthor());
            existingTopic.setCourse(topic.getCourse());
            return topicRepository.save(existingTopic);
        } else {
            throw new RuntimeException("Tópico no encontrado para actualización.");
        }
    }
}
