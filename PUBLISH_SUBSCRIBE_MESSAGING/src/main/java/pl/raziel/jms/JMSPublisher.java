package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.text.DecimalFormat;

public class JMSPublisher {
	public static void main(String[] args) {


		try (Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection()) {
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("EM_TRADE.T");
			MessageProducer publisher = session.createProducer(topic);

			String price = new DecimalFormat("##.00").format(95.0 + Math.random());
			TextMessage message = session.createTextMessage("AAPL " + price);

			publisher.send(message);
			System.out.println("Message sent: AAPL " + price);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
