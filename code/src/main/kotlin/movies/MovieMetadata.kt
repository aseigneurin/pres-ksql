package movies

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(value = [
    "adult", "belongs_to_collection", "budget", "genres", "homepage", "id", "imdb_id", "original_language",
    "original_title", "overview", "popularity", "poster_path", "production_companies", "production_countries",
    "release_date", "revenue", "runtime", "spoken_languages", "status", "tagline", "title", "video",
    "vote_average", "vote_count"])
data class MovieMetadata(
        val adult: String,
        val belongs_to_collection: String,
        val budget: String,
        val genres: String,
        val homepage: String,
        val id: String,
        val imdb_id: String,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: String,
        val poster_path: String,
        val production_companies: String,
        val production_countries: String,
        val release_date: String,
        val revenue: String,
        val runtime: String,
        val spoken_languages: String,
        val status: String,
        val tagline: String,
        val title: String,
        val video: String,
        val vote_average: String,
        val vote_count: String)