package com.ggcorreia.coolfeeds.consumer;

import com.ggcorreia.coolfeeds.model.Feed;
import com.ggcorreia.coolfeeds.model.FeedInfo;
import com.ggcorreia.coolfeeds.parser.FeedParser;
import com.ggcorreia.coolfeeds.parser.XMLFeedParserImpl;
import com.ggcorreia.coolfeeds.service.FeedInfoService;
import com.ggcorreia.coolfeeds.service.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RSSFeedConsumerImpl implements FeedConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSSFeedConsumerImpl.class);

    private static final int MAX_ITEMS = 10;

    private FeedService feedService;
    private FeedInfoService feedInfoService;

    public RSSFeedConsumerImpl(final FeedService feedService, final FeedInfoService feedInfoService) {
        this.feedService = feedService;
        this.feedInfoService = feedInfoService;
    }

    @Override
    public Feed consume(final String sourceUrl, final String sourceId, final String sourceDescription) {
        LOGGER.info(String.format("Consuming %s Feed..", sourceDescription));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(sourceUrl, String.class);

        FeedParser feedParser = new XMLFeedParserImpl();
        Feed feed = feedParser.parse(sourceId, sourceDescription, response.getBody(), MAX_ITEMS);

        Optional<Feed> dbFeed = feedService.getBySourceId(sourceId);

        if(dbFeed.isPresent()){
            LOGGER.info("Feed already present. Removing..");
            feedService.remove(dbFeed.get());
        }

        for (FeedInfo feedInfo : feed.getItems()) {
            LOGGER.info("Inserting new feeds..");
            feedInfoService.save(feedInfo);
        }

        feedService.save(feed);

        LOGGER.info(String.format("[%s] Items consumed!", feed.getItems().size()));
        return feed;
    }

}
