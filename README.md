# storm-demo-fosscomm2016

This project is the demo of our presentation in FOSSCOMM 2016. 

## Requirements
- Zookeeper
- Kafka
- Storm

Start all the above:

- ``` bin/zookeeper-server-start.sh config/zookeeper.properties```
- ``` bin/kafka-server-start.sh config/server.properties ```
- [Start Storm](http://storm.apache.org/releases/1.0.0/Setting-up-a-Storm-cluster.html)

## Create Kafka Topics

- ``` bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myKafkaTopic ```
- ``` bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myKafkaTopicOut ```

### Check Topics

- ``` bin/kafka-topics.sh --list --zookeeper localhost:2181 ```

## Run Topology

Local:
- ``` storm jar target/StormKafkaDemo-0.1.jar org.apache.storm.flux.Flux --local src/test/resources/topology.yaml ``` 

Remote:
- ``` storm jar target/StormKafkaDemo-0.1.jar org.apache.storm.flux.Flux --remote src/test/resources/topology.yaml ``` 


## Check result

- ``` bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic myKafkaTopicOut --from-beginning ```