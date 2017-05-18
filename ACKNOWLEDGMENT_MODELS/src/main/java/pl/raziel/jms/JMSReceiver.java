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
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");
		MessageConsumer receiver = session.createConsumer(queue);
		TextMessage message = (TextMessage) receiver.receive();
		System.out.println("Message received: " + message.getText());
		Thread.sleep(6000);
		System.out.println("Message processed: " + message.getText());
		message.acknowledge();
		connection.close();
	}
}
