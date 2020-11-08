package com.ggcorreia.coolfeeds.scheduler;

import com.ggcorreia.coolfeeds.consumer.RSSFeedConsumerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FeedConsumerScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedConsumerScheduler.class);

    @Value("${consumer.nos.url}")
    private String nosUrl;

    @Value("${consumer.bbc.url}")
    private String bbcUrl;

    private RSSFeedConsumerImpl rssFeedConsumer;

    public FeedConsumerScheduler(final RSSFeedConsumerImpl rssFeedConsumer) {
        this.rssFeedConsumer = rssFeedConsumer;
    }

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void consume(){
        LOGGER.info("Feed consumer triggered!");
        rssFeedConsumer.consume(nosUrl, "nos", "NOS Nieuws");
        rssFeedConsumer.consume(bbcUrl, "bbc", "BBC News");
    }

}
