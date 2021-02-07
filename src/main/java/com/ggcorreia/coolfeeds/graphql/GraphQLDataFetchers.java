package com.ggcorreia.coolfeeds.graphql;

import com.ggcorreia.coolfeeds.service.FeedService;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    FeedService feedService;

    public GraphQLDataFetchers(FeedService feedService) {
        this.feedService = feedService;
    }

    public DataFetcher getFeedsBySourceDataFetcher() {
        return dataFetchingEnvironment -> {
            String source = dataFetchingEnvironment.getArgument("sourceId");
            return feedService.getBySourceId(source);
        };
    }

    public DataFetcher getAllFeedsDataFetcher() {
        return dataFetchingEnvironment -> feedService.getAll();
    }

}
