package com.ggcorreia.coolfeeds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.ggcorreia.coolfeeds.model.Feed;
import com.ggcorreia.coolfeeds.model.FeedItem;
import com.ggcorreia.coolfeeds.service.FeedService;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CoolfeedsApplicationTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private FeedService feedService;

    static List<Feed> feeds = new ArrayList<>();

    static Optional<Feed> feed = Optional.empty();

    @BeforeAll
    static void setUp() {
        feeds.add(insertFeed("nos"));
        feeds.add(insertFeed("bbc"));

        feed = Optional.of(insertFeed("nos"));
    }

    @Test
    void whenGetAllFeedsThenReturnAllFeeds() throws IOException {
        doReturn(feeds).when(feedService).getAll();
        var response = graphQLTestTemplate
            .postForResource("graphql/getAllFeeds.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.getAllFeeds[0].sourceId")).isEqualTo("nos");
        assertThat(response.get("$.data.getAllFeeds[1].sourceId")).isEqualTo("bbc");
    }

    @Test
    void whenGetFeedBySourceThenReturnSourceFeeds() throws IOException {
        doReturn(feed).when(feedService).getBySourceId("nos");
        var response = graphQLTestTemplate
            .postForResource("graphql/getFeedsBySource.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.getFeedsBySource.sourceId")).isEqualTo("nos");
    }

    private static Feed insertFeed(String sourceId) {
        var feed = new Feed();
        feed.setSourceId(sourceId);
        feed.setSourceDescription(String.format("%s Description", sourceId));
        var feedItem = new FeedItem();
        feedItem.setGuid("1");
        feedItem.setTitle(String.format("%s News Title", sourceId));
        feedItem.setDescription("News Test Description");
        feedItem.setPubDate("2021-01-01");
        feedItem.setImgLink("http://test.link.img");
        feed.getItems().add(feedItem);
        return feed;
    }

}
