package pl.raziel.jms2;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

public class JMS2Receiver {

	public static void main(String[] args) {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			final String body = jmsContext.createConsumer(queue).receiveBody(String.class);
			System.out.println(body);
		}
	}
}
