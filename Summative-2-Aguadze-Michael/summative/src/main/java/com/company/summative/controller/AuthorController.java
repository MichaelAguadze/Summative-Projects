package com.company.summative.controller;

import com.company.summative.model.Author;
import com.company.summative.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {
    @Autowired
    AuthorRepository repository;

    @GetMapping("/author")
    public List<Author> getAuthors() {
        return repository.findAll();
    }


    @GetMapping("/author/{id}")
    public Author getAuthorById(@PathVariable int id) {
        Optional<Author> returnVal = repository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody Author author) {

        String first_name = author.getFirst_name();
        String last_name = author.getLast_name();

        if ( first_name.length() < 1){
            throw new IllegalArgumentException("First name should have at least one character");
        }

        if ( last_name.length() < 1){
            throw new IllegalArgumentException("Last name should have at least one character");
        }
        return repository.save(author);
    }

    @PutMapping("/author")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthor(@RequestBody Author artist) {
        repository.save(artist);
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable int id) {
        repository.deleteById(id);
    }

}
