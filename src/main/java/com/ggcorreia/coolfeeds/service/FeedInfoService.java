package com.ggcorreia.coolfeeds.service;

import com.ggcorreia.coolfeeds.model.FeedInfo;
import com.ggcorreia.coolfeeds.repository.FeedInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedInfoService {

    private FeedInfoRepository repository;

    public FeedInfoService(final FeedInfoRepository repository) {
        this.repository = repository;
    }

    public FeedInfo save(FeedInfo feedInfo){
        return repository.save(feedInfo);
    }

}
