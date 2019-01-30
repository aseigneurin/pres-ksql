# A table

```shell
kafka-console-consumer --bootstrap-server localhost:9092 --topic movies --from-beginning
```

```sql
CREATE TABLE movies ( \
    adult VARCHAR, \
    belongs_to_collection VARCHAR, \
    budget VARCHAR, \
    genres VARCHAR, \
    homepage VARCHAR, \
    id VARCHAR, \
    imdb_id VARCHAR, \
    original_language VARCHAR, \
    original_title VARCHAR, \
    overview VARCHAR, \
    popularity VARCHAR, \
    poster_path VARCHAR, \
    production_companies VARCHAR, \
    production_countries VARCHAR, \
    release_date VARCHAR, \
    revenue VARCHAR, \
    runtime VARCHAR, \
    spoken_languages VARCHAR, \
    status VARCHAR, \
    tagline VARCHAR, \
    title VARCHAR, \
    video VARCHAR, \
    vote_average VARCHAR, \
    vote_count VARCHAR) \
WITH (KAFKA_TOPIC = 'movies', \
    VALUE_FORMAT='JSON', \
    KEY = 'id');

SHOW TABLES;

DESCRIBE EXTENDED movies;

SELECT ID, TITLE FROM MOVIES WHERE TITLE LIKE 'Toy %';
```

# A stream

```sql
CREATE STREAM ratings ( \
        userId VARCHAR, \
        movieId VARCHAR, \
        rating DOUBLE, \
        timestamp BIGINT \
      ) \
WITH (KAFKA_TOPIC = 'ratings', VALUE_FORMAT='JSON');

DESCRIBE EXTENDED ratings;

SELECT USERID, MOVIEID, RATING FROM RATINGS;
```

```sql
SELECT USERID, MOVIEID, TITLE, RATING \
FROM RATINGS \
LEFT JOIN MOVIES ON RATINGS.MOVIEID = MOVIES.ID;
```

# A query running in the background

```sql
CREATE STREAM ratings_enriched \
WITH (KAFKA_TOPIC = 'ratings_enriched', VALUE_FORMAT='JSON') \
AS SELECT USERID, MOVIEID, TITLE, RATING \
FROM RATINGS \
LEFT JOIN MOVIES ON RATINGS.MOVIEID = MOVIES.ID;
```

```sql
EXPLAIN CSAS_RATINGS_ENRICHED_0;
```

```shell
kafka-console-consumer --bootstrap-server localhost:9092 --topic ratings_enriched
```

Kill the KSQL CLI...

```sql
TERMINATE CSAS_RATINGS_ENRICHED_0;
DROP STREAM ratings_enriched;
```

# Non-interactive mode

```shell
cd dev/kafka-movies
ksql-server-start ~/software/confluent-5.1.0/etc/ksql/ksql-server.properties --queries-file src/main/resources/query1.sql
```

(Start a second KSQL server)

# Events

```shell
$ kafka-console-consumer --bootstrap-server localhost:9092 --topic events
```

```sql
CREATE STREAM events ( \
        id VARCHAR, \
        info VARCHAR, \
        source VARCHAR, \
        userid BIGINT
      ) \
WITH (KAFKA_TOPIC = 'events', VALUE_FORMAT='JSON');

SELECT * FROM EVENTS;

SELECT SOURCE, WINDOWSTART(), WINDOWEND(), COUNT(*) \
    FROM EVENTS \
    WINDOW TUMBLING(SIZE 5 SECONDs) \
    GROUP BY SOURCE;

SELECT \
    SOURCE, \
    TIMESTAMPTOSTRING(WINDOWSTART(), 'yyyy-MM-dd HH:mm:ss'), \
    TIMESTAMPTOSTRING(WINDOWEND(), 'yyyy-MM-dd HH:mm:ss'), \
    COUNT(*) \
    FROM EVENTS \
    WINDOW TUMBLING(SIZE 5 SECONDs) \
    GROUP BY SOURCE;
```
