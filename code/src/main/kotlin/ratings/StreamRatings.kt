package ratings

import csvMapper
import objectMapper
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.io.File
import java.util.*


// Pre-requesite:
//     kafka-topics --create --topic ratings --partitions 8 --replication-factor 1 --zookeeper localhost:2181

fun main(args: Array<String>) {
    // constants (hard-coded, could be params)
    val filename = "data/movielens/ratings_small.csv"
    val brokers = "localhost:9092"
    val topic = "ratings"

    // read the file of ratings
    val file = File(filename)
    val schema = csvMapper.schemaFor(Rating::class.java).withHeader()
    val reader = csvMapper.readerFor(Rating::class.java).with(schema)
    val ratings = reader.readValues<Rating>(file)

    // prepare the Kafka producer
    val props = Properties()
    props["bootstrap.servers"] = brokers
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["value.serializer"] = StringSerializer::class.java.canonicalName
    val producer = KafkaProducer<String, String>(props)

    // send movies to the Kafka topic
    println("Sending ratings to the '$topic' topic...")
    ratings.forEach {
        val record = ProducerRecord(topic, it.movieId, objectMapper.writeValueAsString(it))
        producer.send(record).get()
        println("Sent rating: userId=${it.userId}, movieId=${it.movieId}, rating=${it.rating}")
        Thread.sleep(1000)
    }
}
