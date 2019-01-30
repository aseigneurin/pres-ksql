CREATE TABLE movies (
    adult VARCHAR,
    belongs_to_collection VARCHAR,
    budget VARCHAR,
    genres VARCHAR,
    homepage VARCHAR,
    id VARCHAR,
    imdb_id VARCHAR,
    original_language VARCHAR,
    original_title VARCHAR,
    overview VARCHAR,
    popularity VARCHAR,
    poster_path VARCHAR,
    production_companies VARCHAR,
    production_countries VARCHAR,
    release_date VARCHAR,
    revenue VARCHAR,
    runtime VARCHAR,
    spoken_languages VARCHAR,
    status VARCHAR,
    tagline VARCHAR,
    title VARCHAR,
    video VARCHAR,
    vote_average VARCHAR,
    vote_count VARCHAR)
WITH (KAFKA_TOPIC = 'movies',
    VALUE_FORMAT='JSON',
    KEY = 'id');
    
CREATE STREAM ratings (
        userId VARCHAR,
        movieId VARCHAR,
        rating DOUBLE,
        timestamp BIGINT
      )
WITH (KAFKA_TOPIC = 'ratings', VALUE_FORMAT='JSON');

CREATE STREAM ratings_enriched_noninteractive
WITH (KAFKA_TOPIC = 'ratings_enriched', VALUE_FORMAT='JSON')
AS SELECT USERID, MOVIEID, TITLE, RATING
FROM RATINGS
LEFT JOIN MOVIES ON RATINGS.MOVIEID = MOVIES.ID;
