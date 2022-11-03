package com.company.summative.controller;

import com.company.summative.model.Author;
import com.company.summative.model.Book;
import com.company.summative.model.Publisher;
import com.company.summative.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Book book;
    private String bookJson;
    private List<Book> allBooks = new ArrayList<>();
    private String allBooksJson;

    @Before
    public void setup() throws Exception {
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


        Publisher publisher = new Publisher();
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("Atlanta");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");


        book = new Book();
        book.setId(1);
        book.setAuthorId(author.getId());
        book.setPublisherId(publisher.getId());
        book.setTitle("SnowWhite");
        book.setIsbn("isbn4902");
        book.setPrice(new BigDecimal("15.00"));

        bookJson = mapper.writeValueAsString(book);

        Book book2 = new Book();
        book2.setAuthorId(author.getId());
        book2.setPublisherId(publisher.getId());
        book2.setTitle("Terry Yhan");
        book2.setIsbn("isbn8028");
        book2.setPrice(new BigDecimal("49.99"));

        allBooksJson = mapper.writeValueAsString(allBooks);
    }

    @Test
    public void shouldCreateNewBookOnPostRequest() throws Exception {
        Book inputBook = new Book();
        inputBook.setId(1);
        inputBook.setAuthorId(1);
        inputBook.setPublisherId(2);
        inputBook.setTitle("Terry Yhan");
        inputBook.setIsbn("isbn8028");
        inputBook.setPrice(new BigDecimal("49.99"));
        String inputJson = mapper.writeValueAsString(inputBook);

        doReturn(book).when(repo).save(inputBook);

        mockMvc.perform(
                        post("/book")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(bookJson));
    }

    @Test
    public void shouldReturnBookById() throws Exception {
        doReturn(Optional.of(book)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/book/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(bookJson))
                );
    }

    @Test
    public void shouldBStatusOkForNonExistentId() throws Exception {
        doReturn(Optional.empty()).when(repo).findById(55);

        mockMvc.perform(
                        get("/book/55"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void shouldReturnAllBooks() throws Exception {
        doReturn(allBooks).when(repo).findAll();

        mockMvc.perform(
                        get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(allBooksJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(
                        put("/book")
                                .content(bookJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/book/1")).andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturnAllBooksByAuthorId() throws Exception {
        Book inputBook = new Book();
        inputBook.setId(1);
        inputBook.setAuthorId(1);
        inputBook.setPublisherId(2);
        inputBook.setTitle("Terry Yhan");
        inputBook.setIsbn("isbn8028");
        inputBook.setPrice(new BigDecimal("49.99"));
        String inputJson = mapper.writeValueAsString(inputBook);

        doReturn(inputBook).when(repo).save(inputBook);

        bookJson = mapper.writeValueAsString(inputBook);

        mockMvc.perform(
                        post("/book")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(bookJson));

        mockMvc.perform(
                        get("/book/author/1")
                                .content(bookJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldHandleEmptyTitle() throws Exception {

        book = new Book();
        book.setId(1);
        book.setAuthorId(4);
        book.setPublisherId(5);
        book.setTitle("");
        book.setIsbn("isbn4902");
        book.setPrice(new BigDecimal("35.00"));

        String inputJson = mapper.writeValueAsString(book);
        mockMvc.perform(
                        post("/book")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
