package pl.raziel.jms2.headers;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import static pl.raziel.jms2.utils.JMSUtils.displayHeaders;

public class JMSSender {

	public static void main(String[] args) throws JMSException {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");
		MessageProducer sender = session.createProducer(queue);
		TextMessage message = session.createTextMessage("BUY AAPL 1000 SHARES");

//		sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//		sender.setTimeToLive(new Date().getTime() + 10000);

		sender.setPriority(9);
		message.setJMSReplyTo(queue);

		displayHeaders(message);
		sender.send(message);
		System.out.println("\nMessage sent.\n");
		displayHeaders(message);
		connection.close();
	}
}
