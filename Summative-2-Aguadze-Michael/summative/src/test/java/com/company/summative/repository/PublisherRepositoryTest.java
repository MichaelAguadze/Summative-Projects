package com.company.summative.repository;


import com.company.summative.model.Publisher;
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
public class PublisherRepositoryTest {
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
    public void addGetDeletePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        publisher = publisherRepository.save(publisher);

        Optional<Publisher> publisher1 = publisherRepository.findById(publisher.getId());

        assertEquals(publisher1.get(), publisher);

        publisherRepository.deleteById(publisher.getId());

        publisher1 = publisherRepository.findById(publisher.getId());

        assertFalse(publisher1.isPresent());
    }

    @Test
    public void updatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        publisher = publisherRepository.save(publisher);

        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setCity("AT");
        Optional<Publisher> publisher1 = publisherRepository.findById(publisher.getId());

    }


    @Test
    public void getAllPublishers() {

        Publisher publisher = new Publisher();
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        Publisher publisher2 = new Publisher();
        publisher2.setName("Ama");
        publisher2.setState("VA");
        publisher2.setStreet("Bollen St");
        publisher2.setPostalCode("23745");
        publisher2.setCity("GO");
        publisher2.setPhone("928-383-7483");
        publisher2.setEmail("ama@press.com");

        publisher = publisherRepository.save(publisher);
        publisher2 = publisherRepository.save(publisher2);
        List<Publisher> publisherList = publisherRepository.findAll();

        assertEquals(2, publisherList.size());
        assertTrue(publisherList.contains(publisher));
        assertTrue(publisherList.contains(publisher2));

    }

}
