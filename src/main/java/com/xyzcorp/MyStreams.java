package com.xyzcorp;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

public class MyStreams {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,
                "my-orders-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.Integer().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Integer> stream = builder.stream("my-orders"); // Key: State, Value: Amount

        //You work in this space


        Topology topology = builder.build();

        KafkaStreams streams = new KafkaStreams(topology, props);

        streams.setUncaughtExceptionHandler(exception -> {
            exception.printStackTrace();
            return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
        });

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }
}