package com.company.summative.controller;

import com.company.summative.model.Publisher;
import com.company.summative.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PublisherController {
    @Autowired
    PublisherRepository repository;

    @GetMapping("/publisher")
    public List<Publisher> getPublishers() {
        return repository.findAll();
    }

    @GetMapping("/publisher/{id}")
    public Publisher getPublisherById(@PathVariable int id) {
        Optional<Publisher> returnVal = repository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping("/publisher")
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher addPublisher(@RequestBody Publisher publisher) {

        String publisher_name = publisher.getName();
        if (publisher_name.length() < 1){
            throw new IllegalArgumentException("Publisher name should have at least one character");
        }
        return repository.save(publisher);
    }

    @PutMapping("/publisher")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePublisher(@RequestBody Publisher publisher) {
        repository.save(publisher);
    }

    @DeleteMapping("/publisher/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable int id) {
        repository.deleteById(id);
    }
}
