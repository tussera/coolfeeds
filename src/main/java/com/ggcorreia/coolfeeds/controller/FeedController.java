package com.ggcorreia.coolfeeds.controller;

import com.ggcorreia.coolfeeds.model.Feed;
import com.ggcorreia.coolfeeds.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FeedController {

    private FeedService feedService;

    public FeedController(final FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feeds/{sourceId}")
    ResponseEntity getFeedBySource(@PathVariable String sourceId){
        Optional<Feed> feed = feedService.getBySourceId(sourceId);

        if(feed.isPresent()){
            return ResponseEntity.ok(feed.get());
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/feeds")
    ResponseEntity getAllFeeds(){
        return ResponseEntity.ok(feedService.getAll());
    }
}
