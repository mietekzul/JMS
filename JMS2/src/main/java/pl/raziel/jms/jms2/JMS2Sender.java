package pl.raziel.jms.jms2;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.Queue;

public class JMS2Sender {

	public static void main(String[] args) {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
//		cf.setProperty(ConnectionConfiguration.imqAddressList, "mq://localhost:7676");

		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			jmsContext
					.createProducer()
//					.setDeliveryDelay(5000)	// message is sent but broker is managing the delay - there could be huge queue and finally it could sent be longer than a 5 seconds
					.setProperty("TraderName", "Daniel")
//					.setTimeToLive(10000)
					.setDeliveryMode(DeliveryMode.NON_PERSISTENT)
					.send(queue, "SELL APPL 1500 SHARES");
			System.out.println("message sent");
		}
	}
}
