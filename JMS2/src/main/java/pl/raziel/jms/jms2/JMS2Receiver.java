package pl.raziel.jms.jms2;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

public class JMS2Receiver {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			Message message = jmsContext.createConsumer(queue).receive();
			String body = message.getBody(String.class);
			String traderName = message.getStringProperty("TraderName");
			System.out.println(body + ", " + traderName);
		}
	}
}