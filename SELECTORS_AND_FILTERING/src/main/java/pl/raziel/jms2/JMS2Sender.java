package pl.raziel.jms2;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * TODO: EXAMPLE from the course is not working, try to figure it out someday
 */
//@JMSConnectionFactory(value = "mq://localhost:7677")
@JMSConnectionFactoryDefinition(name = "myConnectionFactory", properties = {"addressList=mq://localhost:7676"})
public class JMS2Sender {
	public static void main(String[] args) throws JMSException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			jmsContext
					.createProducer()
					.setProperty("Validated", false)
					.send(queue, "BUY AAPL 1000 SHARES");
			System.out.println("Message sent");
		}
	}
}
