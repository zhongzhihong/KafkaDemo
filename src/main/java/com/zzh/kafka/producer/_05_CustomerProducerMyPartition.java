package com.zzh.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 使用自定义分区发送
 */
public class _05_CustomerProducerMyPartition {
    public static void main(String[] args) {
        // 1、配置连接属性
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop101:9092,hadoop102:9092,hadoop103:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.zzh.kafka.config.MyKafkaPartitioner");

        // 2、创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 3、发送数据（不同的发送方式测试时分开测试）
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", 0, "", "hello" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // 异常信息为空代表正常返回
                    if (e == null) {
                        System.out.println("主题：" + recordMetadata.topic() + "分区：" + recordMetadata.partition());
                    }
                }
            });
        }

        // 4、关闭资源
        kafkaProducer.close();
    }
}
