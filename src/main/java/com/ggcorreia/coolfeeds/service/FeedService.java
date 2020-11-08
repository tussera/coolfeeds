package com.ggcorreia.coolfeeds.service;

import com.ggcorreia.coolfeeds.model.Feed;
import com.ggcorreia.coolfeeds.repository.FeedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedService {

    private FeedRepository repository;

    public FeedService(final FeedRepository repository) {
        this.repository = repository;
    }

    public List<Feed> getAll(){
        return repository.findAll();
    }

    public Optional<Feed> getBySourceId(String sourceId){
        return repository.findById(sourceId);
    }

    public Feed save(Feed feed){
        return repository.save(feed);
    }

    public void remove(Feed feed){
        repository.delete(feed);
    }
}
