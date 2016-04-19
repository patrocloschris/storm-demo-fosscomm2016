# storm-demo-fosscomm-2016

This project is the demo of our presentation at FOSSCOMM (2016) conference. 

Event: [Big Data Streaming processing using Apache Storm‏](http://fosscomm.cs.unipi.gr/index.php/event/adrianos-dadis/?lang=en)


## Maintainers

- @qiozas
- @patrocloschris

Question & Comments : [@qiozas](https://twitter.com/qiozas)

## Requirements
- Zookeeper (3.4.5+)
- Kafka (0.8.2.2 or 0.9.x) (for 0.9 need to remove exclusion from pom.xml)
- Storm (1.0)

Start all the above:

- ``` bin/zookeeper-server-start.sh config/zookeeper.properties```
- ``` bin/kafka-server-start.sh config/server.properties ```
- [Start Storm](http://storm.apache.org/releases/1.0.0/Setting-up-a-Storm-cluster.html)

## Create Kafka Topics

- ``` bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic myKafkaTopic ```
- ``` bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic myKafkaTopicOut ```

### Check Topics

- ``` bin/kafka-topics.sh --list --zookeeper localhost:2181 ```

## Run Topology

Local:
- ``` storm jar target/StormKafkaDemo-0.1.jar org.apache.storm.flux.Flux --local src/test/resources/topology.yaml ``` 

Remote:
- ``` storm jar target/StormKafkaDemo-0.1.jar org.apache.storm.flux.Flux --remote src/test/resources/topology.yaml ``` 


## Check result

- ``` bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic myKafkaTopicOut --from-beginning ```