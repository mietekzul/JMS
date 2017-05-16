package pl.raziel.jms2;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import java.text.DecimalFormat;

public class JMS2SharedPublisher {
	public static void main(String[] args) {

		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");
			DecimalFormat df = new DecimalFormat("##.00");
			String price = df.format(98.0 + Math.random());
			jmsContext.createProducer().send(topic, price);
			System.out.println("Message published");
		}
	}
}