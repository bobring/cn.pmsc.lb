package com.pmsc.observeftplog;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Hello world!
 *
 */
public class KafkaProducer 
{
    private final Producer<String, String> producer;
    private String TOPIC;

    public KafkaProducer(String custom_topic){
    	TOPIC = custom_topic;
    	
        Properties props = new Properties();
        //此处配置的是kafka的端口
        props.put("metadata.broker.list", "10.14.83.112:9092");

        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");

        /**
         * 用来控制一个produce请求怎样才能算完成，准确的说，是有多少broker必须已经提交数据到log文件，并向leader发送ack，可以设置如下的值：

0，意味着producer永远不会等待一个来自broker的ack，这就是0.7版本的行为。这个选项提供了最低的延迟，但是持久化的保证是最弱的，当server挂掉的时候会丢失一些数据。
1，意味着在leader replica已经接收到数据后，producer会得到一个ack。这个选项提供了更好的持久性，因为在server确认请求成功处理后，client才会返回。如果刚写到leader上，还没来得及复制leader就挂了，那么消息才可能会丢失。
-1，意味着在所有的ISR都接收到数据后，producer才得到一个ack。这个选项提供了最好的持久性，只要还有一个replica存活，那么数据就不会丢失。
         */
        props.put("request.required.acks","-1");

        producer = new Producer<String, String>(new ProducerConfig(props));
    }

    public void produce(int message_num, String message) {
    	
    	String key = String.valueOf(message_num);
        producer.send(new KeyedMessage<String, String>(TOPIC, key ,message));
        System.out.println(message+" "+key);
        
        producer.close();
//        producer.send(new KeyedMessage<String, String>(TOPIC, "1" ,"hello"));
    }
//
//    public static void main( String[] args )
//    {
//        new KafkaProducer().produce();
//    }
}