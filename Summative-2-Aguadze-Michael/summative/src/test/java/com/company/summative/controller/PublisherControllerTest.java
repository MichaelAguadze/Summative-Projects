package com.company.summative.controller;


import com.company.summative.model.Publisher;
import com.company.summative.repository.PublisherRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    private Publisher publisher;
    private String publisherJson;
    private List<Publisher> allPublisher = new ArrayList<>();
    private String allPublisherJson;

    @Before
    public void setup() throws Exception {
        publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Kofi");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");


        publisherJson = mapper.writeValueAsString(publisher);

        Publisher publisher2 = new Publisher();
        publisher2.setName("Mahamadu");
        publisher2.setState("MA");
        publisher2.setStreet("One Sein st.");
        publisher2.setPostalCode("94481");
        publisher2.setCity("BO");
        publisher2.setPhone("923-939-8374");
        publisher2.setEmail("maham@press.com");

        allPublisher.add(publisher);
        allPublisher.add(publisher2);

        allPublisherJson = mapper.writeValueAsString(allPublisher);
    }

    @Test
    public void shouldCreateNewPublisherOnPostRequest() throws Exception {
        Publisher input = new Publisher();
        input.setId(5);
        input.setName("Kennedy");
        input.setState("NJ");
        input.setStreet("22 Stese st.");
        input.setPostalCode("98482");
        input.setCity("RT");
        input.setPhone("183-829-9839");
        input.setEmail("ken@press.com");

        String inputJson = mapper.writeValueAsString(input);

        doReturn(publisher).when(repository).save(input);

        mockMvc.perform(
                        post("/publisher")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(publisherJson));

    }

    @Test
    public void shouldReturnPublisherById() throws Exception {
        doReturn(Optional.of(publisher)).when(repository).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/publisher/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(publisherJson))
                );
    }

    @Test
    public void shouldBStatusOkForNonExistentPublisherId() throws Exception {
        doReturn(Optional.empty()).when(repository).findById(25);

        mockMvc.perform(
                        get("/publisher/25"))
                .andExpect(status().isOk()
                );

    }


    @Test
    public void shouldReturnAllPublishers() throws Exception {
        doReturn(allPublisher).when(repository).findAll();

        mockMvc.perform(
                        get("/publisher"))
                .andExpect(status().isOk())
                .andExpect(content().json(allPublisherJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(
                        put("/publisher")
                                .content(publisherJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/publisher/1")).andExpect(status().isNoContent());
    }

    @Test
    public void shouldHandleEmptyName() throws Exception {

        publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("");
        publisher.setState("GA");
        publisher.setStreet("One Pin st.");
        publisher.setPostalCode("94803");
        publisher.setCity("AT");
        publisher.setPhone("084-847-3909");
        publisher.setEmail("kofi@pess.com");

        String inputJson = mapper.writeValueAsString(publisher);
        mockMvc.perform(
                        post("/publisher")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}
