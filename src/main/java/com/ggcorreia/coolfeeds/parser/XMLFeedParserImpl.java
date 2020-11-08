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

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";
    static final String IMAGE = "enclosure";
    static final String ITEM = "item";

    @Override
    public Feed parse(final String sourceId, String sourceDescription, String content, int maxItems) {
        Feed feed = null;
        try {
            boolean isHeader = true;
            String description = "";
            String title = "";
            String pubdate = "";
            String guid = "";
            String image = "";

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new ByteArrayInputStream(content.getBytes());
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);
            int itemsAdded = 0;
            while (eventReader.hasNext() && itemsAdded < maxItems) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();
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
                            title = getElementInfo(event, eventReader);
                            break;
                        case DESCRIPTION:
                            description = getDescription(eventReader);
                            break;
                        case IMAGE:
                            image = getImgInfo(event);
                            break;
                        case GUID:
                            guid = getElementInfo(event, eventReader);
                            break;
                        case PUB_DATE:
                            pubdate = getElementInfo(event, eventReader);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        FeedInfo feedInfo = FeedInfo.builder()
                                .guid(guid)
                                .title(title)
                                .description(description)
                                .pubDate(pubdate)
                                .imgLink(image)
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

    private String getElementInfo(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private String getDescription(XMLEventReader eventReader) throws XMLStreamException {
        return eventReader.getElementText();
    }

    private String getImgInfo(XMLEvent event) {
        String result = "";
        String type = "";
        Iterator<Attribute> iterator = event.asStartElement().getAttributes();
        while (iterator.hasNext())
        {
            Attribute attribute = iterator.next();
            if(attribute.getName().toString() == "url"){
                result = attribute.getValue();
            }else if(attribute.getName().toString() == "type"){
                type = attribute.getValue();
            }
        }
        return type.contains("image") ? result : "";
    }
}
