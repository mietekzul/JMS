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

public class JMSReceiver implements MessageListener {
	public static void main(String[] args) {
		new Thread(() -> new JMSReceiver()).start();
	}

	public JMSReceiver() {
		try {
			Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE_FILTER.Q");
			MessageConsumer receiver = session.createConsumer(queue, "Stage = 'open'");
			receiver.setMessageListener(this);
			System.out.println("Waiting for the messages");
			System.out.println("filter: " + receiver.getMessageSelector());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println(msg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
