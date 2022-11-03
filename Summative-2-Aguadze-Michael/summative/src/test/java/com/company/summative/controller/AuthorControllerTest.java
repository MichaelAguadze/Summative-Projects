package com.company.summative.controller;

import com.company.summative.model.Author;
import com.company.summative.repository.AuthorRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    private Author author;
    private String authorJson;
    private List<Author> allAuthor = new ArrayList<>();
    private String allAuthorJson;

    @Before
    public void setup() throws Exception {
        author = new Author();
        author.setId(1);
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GA");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");

        authorJson = mapper.writeValueAsString(author);

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

        allAuthor.add(author);
        allAuthor.add(author2);

        allAuthorJson = mapper.writeValueAsString(allAuthor);

    }

    @Test
    public void shouldCreateNewAuthorOnPostRequest() throws Exception {
        Author input = new Author();
        input.setId(1);
        input.setFirst_name("Nana");
        input.setLast_name("Addo");
        input.setStreet("Gbawe");
        input.setCity("Accra");
        input.setState("GR");
        input.setPostalCode("68989");
        input.setPhone("020-678-74839");
        input.setEmail("ghanaahokyere@gh.com");
        String inputJson = mapper.writeValueAsString(input);


        doReturn(author).when(repository).save(input);

        mockMvc.perform(
                        post("/author")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturnAllAuthor() throws Exception {
        doReturn(allAuthor).when(repository).findAll();

        mockMvc.perform(get("/author"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAuthorById() throws Exception {
        Author author = new Author();
        author.setId(1);
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GR");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahokyere@gh.com");
        doReturn(Optional.of(author)).when(repository).findById(1);

        mockMvc.perform(get("/author/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {

        Author author = new Author();
        author.setId(1);
        author.setFirst_name("Nana");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GR");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahntiokyere@gh.com");

        String inputJson = mapper.writeValueAsString(author);
        mockMvc.perform(
                        put("/author")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/author/8")).andExpect(status().isNoContent());
    }

    @Test
    public void shouldBStatusOkForNonExistentAuthorId() throws Exception {
        doReturn(Optional.empty()).when(repository).findById(56);

        mockMvc.perform(
                        get("/author/56"))
                .andExpect(status().isOk()
                );

    }


    @Test
    public void shouldHandleEmptyAuthorName() throws Exception {

        Author author = new Author();
        author.setId(1);
        author.setFirst_name("");
        author.setLast_name("Addo");
        author.setStreet("Gbawe");
        author.setCity("Accra");
        author.setState("GR");
        author.setPostalCode("68989");
        author.setPhone("020-678-74839");
        author.setEmail("ghanaahntiokyere@gh.com");

        String inputJson = mapper.writeValueAsString(author);
        mockMvc.perform(
                        post("/author")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


}
