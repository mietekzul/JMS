package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class JMSSubscriber implements MessageListener {
	public static void main(String[] args) {
		new Thread(() -> new JMSSubscriber()).start();
	}

	public JMSSubscriber() {
		try {
			TopicConnection connection =
					new ActiveMQConnectionFactory("tcp://localhost:61616").createTopicConnection();
			connection.start();
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("EM_TRADE.T");

			TopicSubscriber subscriber = session.createSubscriber(topic);
			subscriber.setMessageListener(this);
			System.out.println("Waiting for messages... ");
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
