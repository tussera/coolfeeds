package com.ggcorreia.coolfeeds.repository;

import com.ggcorreia.coolfeeds.model.FeedInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedInfoRepository extends JpaRepository<FeedInfo, Long> {
}
