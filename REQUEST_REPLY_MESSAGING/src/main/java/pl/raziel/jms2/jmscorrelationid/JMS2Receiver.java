package pl.raziel.jms2.jmscorrelationid;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

public class JMS2Receiver {

	public static void main(String[] args) throws JMSException, InterruptedException {

		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
			Message message = jmsContext.createConsumer(queue).receive();
			String payload = message.getBody(String.class);
			System.out.printf("processing trade: %s%n", payload);
			Thread.sleep(4000);
			String confirmation = "EQ-98765";
			System.out.printf("trade confirmation: %s%n", confirmation);

			jmsContext
					.createProducer()
					.setProperty("MessageLink", message.getStringProperty("Uuid"))
					.send(message.getJMSReplyTo(), confirmation);
		}
	}
}
