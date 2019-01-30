package movies

import csvMapper
import objectMapper
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.io.File
import java.util.*


// Pre-requesite:
//     kafka-topics --create --topic movies --partitions 8 --replication-factor 1 --zookeeper localhost:2181

fun main(args: Array<String>) {
    // constants (hard-coded, could be params)
    val filename = "data/movielens/movies_metadata_small.csv"
    val brokers = "localhost:9092"
    val topic = "movies"

    // prepare the CSV reader
    val file = File(filename)
    val schema = csvMapper.schemaFor(MovieMetadata::class.java).withHeader()
    val reader = csvMapper.readerFor(MovieMetadata::class.java).with(schema)

    // prepare the Kafka producer
    val props = Properties()
    props["bootstrap.servers"] = brokers
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["value.serializer"] = StringSerializer::class.java.canonicalName
    val producer = KafkaProducer<String, String>(props)

    // send movies to the Kafka topic
    println("Sending movies to the '$topic' topic...")
    while (true) {
        val movies = reader.readValues<MovieMetadata>(file)
        movies.forEach {
            val record = ProducerRecord(topic, it.id, objectMapper.writeValueAsString(it))
            producer.send(record).get()
            Thread.sleep(1000)
        }
    }
}
