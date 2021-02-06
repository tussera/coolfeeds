package com.ggcorreia.coolfeeds.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExtractedInfo {

    private String description;
    private String title;
    private String pubDate;
    private String guid;
    private String image;

}
