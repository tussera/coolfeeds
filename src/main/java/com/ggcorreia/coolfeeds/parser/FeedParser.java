package com.ggcorreia.coolfeeds.parser;

import com.ggcorreia.coolfeeds.model.Feed;

public interface FeedParser {
    Feed parse(String sourceId, String sourceDescription, String content, int maxItems);
}
