package com.zzh.kafka.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区器
 */
public class MyKafkaPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取发送的消息
        String msg = value.toString();
        int partition;
        if (msg.contains("HelloWorld")) {
            partition = 1;
        } else {
            partition = 0;
        }
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
