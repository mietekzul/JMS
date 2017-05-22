package pl.raziel.jms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.Enumeration;

public class JMS2Browser {
	public static void main(String[] args) throws JMSException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			Enumeration<?> e = jmsContext.createBrowser(queue).getEnumeration();

			while (e.hasMoreElements()) {
				System.out.println(((Message) e.nextElement()).getBody(String.class));
			}
		}
	}
}
