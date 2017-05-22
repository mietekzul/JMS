package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import java.util.Enumeration;

public class JMSBrowser {
	public static void main(String[] args) throws JMSException {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");

		QueueBrowser queueBrowser = session.createBrowser(queue);
		Enumeration<?> e = queueBrowser.getEnumeration();

		int msgCount = 0;
		while (e.hasMoreElements()) {
			e.nextElement();
			msgCount++;
		}
		System.out.println("There are " + msgCount + " messages in the queue");

		queueBrowser.close();
		connection.close();
	}
}
