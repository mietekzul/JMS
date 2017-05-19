package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSReceiver {
	public static void main(String[] args) throws JMSException, InterruptedException {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");
		MessageConsumer receiver = session.createConsumer(queue);
		TextMessage message1 = (TextMessage) receiver.receive();
		System.out.println("Message1 received: " + message1.getText());
		Thread.sleep(6000);

		TextMessage message2 = (TextMessage) receiver.receive();
		System.out.println("Message2 received: " + message2.getText());
		Thread.sleep(6000);
		session.commit();
		connection.close();
	}
}
