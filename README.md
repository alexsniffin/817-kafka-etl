# 817-kafka-etl
Simulation of a Kafka ETL process with Financial Data

Contains two separate applications:
* Producer
* Consumer

### Producer
Producer application is a Scala-based Kafka client for simulating the streaming of daily financial data.

### Consumer
Consumer application is a Node.js Kafka client for consuming the financial data in real-time using socket.io.

### Before Getting Started
You'll need the following:
* Java 8+
* SBT 13.16+ (Will install Scala)
* Kafka 2.11
* Zookeeper 3.3.6

For Kafka and Zookeeper, you'll need both of these locally on your machine to run these applications, follow the instructions at:
https://kafka.apache.org/quickstart

Stop once you have both installed and create a new topic "findata", this will be the topic that both applications will use.

Make sure you have both Kafka and Zookeeper running before continuing.

### Running the Applications
Producer uses SBT for package management, building, and running the application. To start, go in the producer directory and run:
```
> sbt
> run --topic findata --broker localhost:9092 --delay 1000
```
Where the usage is:
```
--topic <value>
    the topic to produce to
--broker <value>
    the address on where the broker cluster is located
--delay <value>
    the delay on which to simulate for a single day's process
--help
    this dialog
```

Now to run the consumer, you'll need to use NPM, go in the consumer directory and run:
```
> npm start
```
The server is hosted on port 3001, and should be available at http://127.0.0.1:3001/

Additionally, you can use a Kafka terminal consumer to see results:
```
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic findata --from-beginning
```

#### Results
Once running, you’ll notice data being streamed to the consumer application. Results are stored in-memory in Kafka’s cluster instance and can be accessed quickly and easily.

This simulation demo's the affective use of using real-time data streaming through an ETL process. Consumer applications can quickly access new data in-memory on the fly. This style of architecture can be used further with a pub-sub design where applications will receive data when it's readily available.

The problem this helps to solve is the elimination of point-to-point ETL processes where data processing was typically treated as a pipeline, resulting in spaghetti architecture. Kafka becomes a single-source-of-truth, or a “data” backbone that makes data governance and management simple. Additionally, Kafka provides an easy to access API and simple to understand concepts that make using it easy for developers.

#### Potential additions and ideas:
* Include another consumer application that transforms the data set and writes out to another topic
* Integrate long-term data storage with Apache Parquet, Avro, and Atlas for easy data governance and lineage 
* Experiment with replication for disaster recovery
