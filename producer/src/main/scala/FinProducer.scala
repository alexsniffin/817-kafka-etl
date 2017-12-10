import java.io.File
import java.util.Properties

import com.github.tototoshi.csv._
import net.liftweb.json._
import net.liftweb.json.Serialization.write
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import constants.Constants
import schema.FinData

import scala.util.Try

/**
  * Producer application to write data to our Kafka topic
  *
  * @param reader Reader object referencing static reference of fin data
  */
class FinProducer(reader: CSVReader, topic: String, broker: String, delay: Long) {

  // Setup config
  val props = new Properties()
  props.put("bootstrap.servers", broker)
  props.put("client.id", "ScalaProducer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  /**
    * Simulates a stream of data from the static data reference
    */
  def simulateDataFeed(): Unit = {
    // Producer
    val producer = new KafkaProducer[String, String](props)

    println("Create Kafka producer")

    // Iterator
    val it = reader.iterator

    // Iterate through the dataset
    while(it.hasNext) {
      singleDayProcess({
        implicit val formats = DefaultFormats

        val row: Array[String] = it.next().toArray

        // Create FinData object
        val record = FinData(
          row(0), row(1), row(2).toDouble, row(3).toDouble, row(4).toDouble, row(6).toLong, row(7).toDouble,
          row(8).toDouble, row(9).toDouble, row(10).toDouble, row(11).toDouble, row(12).toDouble, row(13).toLong
        )

        val jsonString = write(record)

        println("Producing record: " + jsonString)

        // Produce the record
        val data = new ProducerRecord[String, String](topic, jsonString) //todo

        // Send the record
        producer.send(data)

        println("Record sent, waiting till next day")
      })(delay)
    }
  }

  /**
    * Simulates a single day's process
    *
    * @param delay Delay for a day
    * @param f Function to execute after delay
    */
  def singleDayProcess(f: => Unit)(delay: Long): Unit = {
    f
    Thread.sleep(delay)
  }

}

/**
  * Companion object that creates a reader of static data to simulate
  */
object FinProducer {

  // Invoke our driver class
  def apply(topic: Any, broker: Any, delay: Any) = {

    // Read in the data from the CSV
    val data = CSVReader.open(new File(Constants.DATA_FILE))

    // Create class instance
    new FinProducer(data, topic.toString, broker.toString, Try(delay.asInstanceOf[Number].longValue()).get)
  }

}
