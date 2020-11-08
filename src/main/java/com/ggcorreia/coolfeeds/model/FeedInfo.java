package com.ggcorreia.coolfeeds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "feed_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedInfo {

    @Id
    private String guid;
    private String title;
    @Column(columnDefinition="TEXT")
    private String description;
    private String pubDate;
    private String imgLink;

}
