package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

public class JMSSubscriber implements MessageListener {
	public static void main(String[] args) {
		new Thread(() -> new JMSSubscriber()).start();
	}

	public JMSSubscriber() {
		try {
			TopicConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createTopicConnection();
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
	}
}
