package com.ggcorreia.coolfeeds.service;

import com.ggcorreia.coolfeeds.model.FeedItem;
import com.ggcorreia.coolfeeds.repository.FeedItemRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedItemService {

    private FeedItemRepository repository;

    public FeedItemService(final FeedItemRepository repository) {
        this.repository = repository;
    }

    public FeedItem save(FeedItem feedItem) {
        return repository.save(feedItem);
    }

}
