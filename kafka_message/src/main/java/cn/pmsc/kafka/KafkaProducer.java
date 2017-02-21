package cn.pmsc.kafka;

import java.util.Date;
import java.util.Properties;

import org.w3c.dom.NodeList;

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

    public KafkaProducer(String kafka_hosts){
    	
        Properties props = new Properties();
        //此处配置的是kafka的端口
        props.put("metadata.broker.list", kafka_hosts);
//        props.put("metadata.broker.list", "10.14.83.112:9092");
        
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

    public void send(String custom_topic, String message) {
    	
    	String key = String.valueOf(new Date().getTime());
        producer.send(new KeyedMessage<String, String>(custom_topic, key ,message));
        System.out.println(key+" "+message);
        
        producer.close();
//        producer.send(new KeyedMessage<String, String>(TOPIC, "1" ,"hello"));
    }
//
    public static void main(String[] args) {
    	if(args.length < 3) {
			throw new NullPointerException(
					"enter the config xml, the topic[t1/t2/t3] and message");
		}
    	
    	String expression;
    	NodeList nlist;
    	StringBuffer kafka_hosts = new StringBuffer();;
    	
    	if(XMLUtil.ReadXML(args[0])) {
    		expression = "/configuration/kafkaServers/host";
    		
    		if((nlist = XMLUtil.selectNodes(expression)) != null) {
    			for(int i=0;i<nlist.getLength();i++){
    				kafka_hosts.append(nlist.item(i).getTextContent() + ",");
    			}
    			kafka_hosts.setLength(kafka_hosts.length() - 1); //去掉结尾的逗号
    			
    			KafkaProducer kp = new KafkaProducer(kafka_hosts.toString());
    			kp.send(args[1], args[2]);
    		} else {
    			System.out.println("failed to find " + expression + " in xml: " + args[0]);
    		}
    	} else {
    		System.out.println("xml file read error: " + args[0]);
    	}
    }
}