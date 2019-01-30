package ratings

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(value = ["userId", "movieId", "rating", "timestamp"])
data class Rating(
        val userId: String,
        val movieId: String,
        val rating: Float,
        val timestamp: Long)