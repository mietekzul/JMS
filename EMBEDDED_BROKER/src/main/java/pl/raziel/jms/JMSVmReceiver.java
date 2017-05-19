package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSVmReceiver implements MessageListener {
	private int id;

	public JMSVmReceiver(int id) {
		this.id = id;
		try (Connection connection = new ActiveMQConnectionFactory("vm://embedded1").createConnection()) {
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_EMBEDDED_TRADE.Q");
			MessageConsumer receiver = session.createConsumer(queue);
			receiver.setMessageListener(this);
			System.out.println("Receiver (" + id + ") Waiting for messages");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			Thread.sleep(1000);
			System.out.println("Trade receivied: (" + id + "): " + textMessage.getText());
		} catch (InterruptedException | JMSException e) {
			e.printStackTrace();
		}

	}
}
