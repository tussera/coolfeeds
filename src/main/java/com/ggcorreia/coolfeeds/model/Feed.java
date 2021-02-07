package com.ggcorreia.coolfeeds.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "feed")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feed {

    @Id
    private String sourceId;

    private String sourceDescription;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_sourceId")
    private List<FeedItem> items = new ArrayList<>();

}
