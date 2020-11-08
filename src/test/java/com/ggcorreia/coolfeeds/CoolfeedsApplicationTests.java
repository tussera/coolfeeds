package com.ggcorreia.coolfeeds;

import com.ggcorreia.coolfeeds.controller.FeedController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CoolfeedsApplicationTests {

    @Autowired
    private FeedController feedController;

    @Test
    public void contextLoads() {
        assertThat(feedController).isNotNull();
    }

}
