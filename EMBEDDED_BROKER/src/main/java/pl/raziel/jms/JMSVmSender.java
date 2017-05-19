package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSVmSender {
	private MessageProducer sender;
	private Session session;

	public JMSVmSender() {
		try (Connection connection = new ActiveMQConnectionFactory("vm://embedded1").createConnection()) {
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_EMBED_TRADE.Q");
			sender = session.createProducer(queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String trade) {
		try {
			TextMessage message = session.createTextMessage(trade);
			sender.send(message);
			System.out.println("Message sent");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
