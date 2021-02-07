package com.ggcorreia.coolfeeds.consumer;

import com.ggcorreia.coolfeeds.model.Feed;
import com.ggcorreia.coolfeeds.model.FeedItem;
import com.ggcorreia.coolfeeds.parser.XMLFeedParserImpl;
import com.ggcorreia.coolfeeds.service.FeedItemService;
import com.ggcorreia.coolfeeds.service.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RSSFeedConsumerImpl implements FeedConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSSFeedConsumerImpl.class);

    private static final int MAX_ITEMS = 10;

    private FeedService feedService;
    private FeedItemService feedItemService;

    public RSSFeedConsumerImpl(final FeedService feedService, final FeedItemService feedItemService) {
        this.feedService = feedService;
        this.feedItemService = feedItemService;
    }

    @Override
    public Feed consume(final String sourceUrl, final String sourceId, final String sourceDescription) {
        LOGGER.info(String.format("Consuming %s Feed..", sourceDescription));

        var response = getSourceResponse(sourceUrl);
        var feed = parseFeed(sourceId, sourceDescription, response);
        var dbFeed = feedService.getBySourceId(sourceId);

        if (dbFeed.isPresent()) {
            feedService.remove(dbFeed.get());
        }

        for (FeedItem feedItem : feed.getItems()) {
            feedItemService.save(feedItem);
        }

        feedService.save(feed);

        LOGGER.info(String.format("[%s] Items consumed!", feed.getItems().size()));
        return feed;
    }

    private Feed parseFeed(String sourceId, String sourceDescription, ResponseEntity<String> response) {
        var feedParser = new XMLFeedParserImpl();
        var feed = feedParser.parse(sourceId, sourceDescription, response.getBody(), MAX_ITEMS);
        return feed;
    }

    private ResponseEntity<String> getSourceResponse(String sourceUrl) {
        var restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(sourceUrl, String.class);
        return response;
    }

}
