package com.ggcorreia.coolfeeds.consumer;

import com.ggcorreia.coolfeeds.model.Feed;

public interface FeedConsumer {
    Feed consume(String sourceUrl, String sourceId, String sourceDescription);
}
