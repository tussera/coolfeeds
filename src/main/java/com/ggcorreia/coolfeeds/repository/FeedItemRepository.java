package com.ggcorreia.coolfeeds.repository;

import com.ggcorreia.coolfeeds.model.FeedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedItemRepository extends JpaRepository<FeedItem, Long> {

}
