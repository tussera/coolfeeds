package com.ggcorreia.coolfeeds.parser;

import com.ggcorreia.coolfeeds.model.Feed;
import com.ggcorreia.coolfeeds.model.FeedInfo;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class XMLFeedParserImpl implements FeedParser{

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PUB_DATE = "pubDate";
    private static final String GUID = "guid";
    private static final String IMAGE = "enclosure";
    private static final String ITEM = "item";
    private static final String EMPTY_STRING = "";

    @Override
    public Feed parse(final String sourceId, String sourceDescription, String content, int maxItems) {
        Feed feed = null;
        try {
            var isHeader = true;
            var extractedInfo = new ExtractedInfo();

            var inputFactory = XMLInputFactory.newInstance();
            var inputStream = new ByteArrayInputStream(content.getBytes());
            var eventReader = inputFactory.createXMLEventReader(inputStream);
            var itemsAdded = 0;
            while (eventReader.hasNext() && itemsAdded < maxItems) {
                var event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    var localPart = event.asStartElement().getName().getLocalPart();
                    switch (localPart) {
                        case ITEM:
                            if (isHeader) {
                                isHeader = false;
                                feed = Feed.builder()
                                        .sourceId(sourceId)
                                        .sourceDescription(sourceDescription)
                                        .items(new ArrayList<>())
                                        .build();
                            }
                            event = eventReader.nextEvent();
                            break;
                        case TITLE:
                            extractedInfo.setTitle(getElementInfo(eventReader));
                            break;
                        case DESCRIPTION:
                            extractedInfo.setDescription(getDescription(eventReader));
                            break;
                        case IMAGE:
                            extractedInfo.setImage(getImgInfo(event));
                            break;
                        case GUID:
                            extractedInfo.setGuid(getElementInfo(eventReader));
                            break;
                        case PUB_DATE:
                            extractedInfo.setPubDate(getElementInfo(eventReader));
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        var feedInfo = FeedInfo.builder()
                                .guid(extractedInfo.getGuid())
                                .title(extractedInfo.getTitle())
                                .description(extractedInfo.getDescription())
                                .pubDate(extractedInfo.getPubDate())
                                .imgLink(extractedInfo.getImage())
                                .build();
                        feed.getItems().add(feedInfo);
                        event = eventReader.nextEvent();
                        itemsAdded++;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }

    private String getElementInfo(XMLEventReader eventReader) throws XMLStreamException {
        var result = EMPTY_STRING;
        var event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private String getDescription(XMLEventReader eventReader) throws XMLStreamException {
        return eventReader.getElementText();
    }

    private String getImgInfo(XMLEvent event) {
        var result = "";
        var type = "";
        var iterator = event.asStartElement().getAttributes();
        while (iterator.hasNext())
        {
            var attribute = iterator.next();
            if(attribute.getName().toString() == "url"){
                result = attribute.getValue();
            }else if(attribute.getName().toString() == "type"){
                type = attribute.getValue();
            }
        }
        return type.contains("image") ? result : "";
    }
}
