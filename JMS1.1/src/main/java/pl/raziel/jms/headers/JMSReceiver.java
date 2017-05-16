package pl.raziel.jms.headers;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import static pl.raziel.jms.utils.JMSUtils.displayHeaders;

public class JMSReceiver {

	public static void main(String[] args) throws JMSException {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");
		MessageConsumer receiver = session.createConsumer(queue);
		TextMessage message = (TextMessage) receiver.receive();

		displayHeaders(message);
		System.out.println("\nMessage__ " + message.getText());
		connection.close();
	}
}
