package com.fujitsu.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Base testing class for setting up mockMvc and primary API request methods.
 * Test classes use a separate in-memory database instance (defined in application-test.properties).
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class BaseTest {

    private final static String BASE_URI = "/api/";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected ResultActions performGet(String path, Object ...params) throws Exception {
        return mockMvc.perform(
                get(BASE_URI + path, params)
                        .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performPost(String path, Object data) throws Exception {
        return mockMvc.perform(
                post(BASE_URI + path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data))
                        .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performPut(String path, Long id, Object data) throws Exception {
        return mockMvc.perform(
                put(BASE_URI + path, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data))
                        .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performDelete(String path, Long id) throws Exception {
        return mockMvc.perform(
                delete(BASE_URI + path, id)
                        .accept(MediaType.APPLICATION_JSON));
    }
}
