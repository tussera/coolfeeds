# Cool Feeds

This application pulls the latest 10 Feeds from configured sources. RSS from NOS and BBC are
implemented.

## Configuration

The Consumer pulls data every 5 min (300 sec) by default. In case you want change it, you can set
the ``scheduler.fixedRate`` in the application.properties file.

## Test

To execute only the tests, run the command ``mvn test`` from the command line.

## Run and Check

To run the application, run the command ``mvn spring-boot:run`` from the command line.

The application run at 8080 port and teh GraphiQL interface can be accessed
in ``http://localhost:8080/graphiql``

There are two endpoints available in order to retrieve Feeds from the application:

Get All Feeds

````
query {
  getAllFeeds{
    sourceId
    sourceDescription
    items {
      guid
      title
      description
      pubDate
      imgLink
    }
  }
}
````

Get Feeds By Source

````
query {
  getFeedsBySource(sourceId: "nos"){
    sourceId,
    sourceDescription
    items{
      guid
      title
      description
      pubDate
      imgLink
    }
  }
}
````

Have fun! :)