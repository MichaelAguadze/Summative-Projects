package com.company.summative.controller;

import com.company.summative.model.Book;
import com.company.summative.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookRepository repository;


    @GetMapping("/book")
    public List<Book> getBooks() {
        return repository.findAll();
    }


    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable int id) {

        Optional<Book> returnVal = repository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @GetMapping("/book/author/{authorId}")
    public List<Book> getAllBooksByAuthorId(@PathVariable int authorId) {

        List<Book> bookList = repository.findAllBooksByAuthorId(authorId);
        if (bookList.size() > 0) {
            return bookList;
        } else {
            return null;
        }
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        String title = book.getTitle();

        if ( title.length() < 1){
            throw new IllegalArgumentException("Book title should have at least one character");
        }
        return repository.save(book);
    }

    @PutMapping("/book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody Book book) {
        repository.save(book);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id) {
        repository.deleteById(id);
    }

}
