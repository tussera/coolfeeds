package com.ggcorreia.coolfeeds.repository;

import com.ggcorreia.coolfeeds.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, String> {
}
