package com.company.summative.repository;


import com.company.summative.model.Author;
import com.company.summative.model.Book;
import com.company.summative.model.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    PublisherRepository publisherRepository;

    @Before
    public void setUp() throws Exception {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    public void addGetDeleteBook() {

        Author author = new Author();
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Publisher publisher = new Publisher();
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        publisher = publisherRepository.save(publisher);

        Book book = new Book();
        book.setAuthorId(author.getId());
        book.setPublisherId(publisher.getId());
        book.setTitle("Terry Yhan");
        book.setIsbn("isbn8028");
        book.setPublish_date(LocalDate.of(2019, 05, 02));
        book.setPrice(new BigDecimal("56.00"));

        book = bookRepository.save(book);
        Optional<Book> book1 = bookRepository.findById(book.getId());

        assertEquals(book1.get(), book);

        bookRepository.deleteById(book.getId());

        book1 = bookRepository.findById(book.getId());

        assertFalse(book1.isPresent());
    }

    @Test
    public void updateBook() {

        Author author = new Author();
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        publisher = publisherRepository.save(publisher);

        Book book = new Book();
        book.setAuthorId(author.getId());
        book.setPublisherId(publisher.getId());
        book.setTitle("Teryy Yhan");
        book.setIsbn("isbn8028");
        book.setPublish_date(LocalDate.of(2018, 05, 02));
        book.setPrice(new BigDecimal("12.00"));
        book = bookRepository.save(book);

        Optional<Book> book1 = bookRepository.findById(book.getId());
        book.setTitle("TitleUpdate");
        book.setIsbn("updatedisbn");

        bookRepository.save(book);

        Optional<Book> book2 = bookRepository.findById(book.getId());

        assertFalse(book1.get().equals(book2));

        assertEquals(book2.get(), book);
    }

    @Test
    public void getAllBooks() {

        Author author = new Author();
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        publisher = publisherRepository.save(publisher);

        Book book = new Book();
        book.setAuthorId(author.getId());
        book.setPublisherId(publisher.getId());
        book.setTitle("Teryy Yhan");
        book.setIsbn("isbn8028");
        book.setPublish_date(LocalDate.of(2018, 05, 02));
        book.setPrice(new BigDecimal("65.00"));

        book = bookRepository.save(book);

        Book book2 = new Book();
        book2.setAuthorId(author.getId());
        book2.setPublisherId(publisher.getId());
        book2.setTitle("Jungle");
        book2.setIsbn("isbn883");
        book2.setPublish_date(LocalDate.of(2011, 8, 1));
        book2.setPrice(new BigDecimal("74.45"));

        book2 = bookRepository.save(book2);

        List<Book> bookList = bookRepository.findAll();

        assertEquals(bookList.size(), 2);
    }

    @Test
    public void getBooksByAuthorId() {
        Author author = new Author();
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        publisher = publisherRepository.save(publisher);

        Book book = new Book();
        book.setAuthorId(author.getId());
        book.setPublisherId(publisher.getId());
        book.setTitle("Teryy Yhan");
        book.setIsbn("isbn8028");
        book.setPublish_date(LocalDate.of(2018, 05, 02));
        book.setPrice(new BigDecimal("19.00"));

        book = bookRepository.save(book);

        Book book2 = new Book();
        book2.setAuthorId(author.getId());
        book2.setPublisherId(publisher.getId());
        book2.setTitle("Jungle");
        book2.setIsbn("isbn883");
        book2.setPublish_date(LocalDate.of(2011, 8, 1));
        book2.setPrice(new BigDecimal("74.45"));

        book2 = bookRepository.save(book2);


        Author author2 = new Author();
        author2.setFirst_name("Michael");
        author2.setLast_name("Aguadze");
        author2.setStreet("Corprew Ave");
        author2.setCity("Norfolk");
        author2.setState("VA");
        author2.setPostalCode("23504");
        author2.setPhone("757-474-9373");
        author2.setEmail("michael@netflix.com");

        author2 = authorRepository.save(author2);

        Book book3 = new Book();
        book3.setAuthorId(author2.getId());
        book3.setPublisherId(publisher.getId());
        book3.setTitle("Taint");
        book3.setIsbn("isbn8723");
        book3.setPublish_date(LocalDate.of(1973, 8, 9));
        book3.setPrice(new BigDecimal("94.38"));

        book3 = bookRepository.save(book3);

        Integer authorID = author.getId();
        List<Book> bookList = bookRepository.findAllBooksByAuthorId(authorID);

        assertEquals(bookList.size(), 2);

        assertTrue(bookList.contains(book));
        assertTrue(bookList.contains(book2));
    }


}
