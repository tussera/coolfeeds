package com.ggcorreia.coolfeeds;

import com.ggcorreia.coolfeeds.model.Feed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedAPITests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGettingALlFeeds_thenReturnAllFeeds(){

        ResponseEntity<Feed[]> response = restTemplate.getForEntity("http://localhost:" + port + "/feeds", Feed[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Arrays.stream(response.getBody()).count());
        assertEquals("nos", response.getBody()[0].getSourceId());
        assertEquals(10, response.getBody()[0].getItems().size());
        assertEquals("bbc", response.getBody()[1].getSourceId());
        assertEquals(10, response.getBody()[1].getItems().size());
    }

    @Test
    public void whenGettingFeedByNosSource_thenReturnNosFeeds(){
        ResponseEntity<Feed> response = restTemplate.getForEntity("http://localhost:" + port + "/feeds/nos", Feed.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("nos", response.getBody().getSourceId());
        assertEquals(10, response.getBody().getItems().size());
    }

    @Test
    public void whenGettingFeedByBbcSource_thenReturnNosFeeds(){
        ResponseEntity<Feed> response = restTemplate.getForEntity("http://localhost:" + port + "/feeds/bbc", Feed.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("bbc", response.getBody().getSourceId());
        assertEquals(10, response.getBody().getItems().size());
    }

    @Test
    public void whenGettingFeedByUnknownSource_thenReturnNotFound(){
        ResponseEntity<Feed> response = restTemplate.getForEntity("http://localhost:" + port + "/feeds/test", Feed.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
