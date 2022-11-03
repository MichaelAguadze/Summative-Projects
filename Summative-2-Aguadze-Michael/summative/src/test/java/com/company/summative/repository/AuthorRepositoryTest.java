package com.company.summative.repository;

import com.company.summative.model.Author;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @Before
    public void setUp() throws Exception {
        authorRepository.deleteAll();
    }

    @Test
    public void addGetDeleteAuthor() {

       Author author = new Author();
        author.setId(1);
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Optional<Author> author1 = authorRepository.findById(author.getId());

        assertEquals(author1.get(), author);

        authorRepository.deleteById(author.getId());

        author1 = authorRepository.findById(author.getId());

        assertFalse(author1.isPresent());

    }

    @Test
    public void updateAuthor() {

        Author author = new Author();
        author.setId(1);
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Optional<Author> author1 = authorRepository.findById(author.getId());

        author.setStreet("Update St.");
        author.setCity("Austin");
        author.setState("TX");
        author.setPostalCode("75673");

        authorRepository.save(author);

        Optional<Author> author2 = authorRepository.findById(author.getId());

        assertFalse(author1.get().equals(author2));

        assertEquals(author2.get(), author);

        authorRepository.deleteAll();
    }

    @Test
    public void getAllAuthors() {

        Author author = new Author();
        author.setId(1);
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        author = authorRepository.save(author);

        Author author2 = new Author();
        author2.setId(2);
        author2.setFirst_name("Mahamadu");
        author2.setLast_name("Bawumis");
        author2.setStreet("Nima");
        author2.setCity("Accra");
        author2.setState("GR");
        author2.setPostalCode("0938");
        author2.setPhone("093-737-9383");
        author2.setEmail("maham@npp.com");

        author2 = authorRepository.save(author2);

        List<Author> authorsList = authorRepository.findAll();
        assertEquals(authorsList.size(), 2);
        assertTrue(authorsList.contains(author2));
        assertTrue(authorsList.contains(author));

        authorRepository.deleteAll();
    }

}
