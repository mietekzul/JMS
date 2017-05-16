package pl.raziel.jms.jms2.shared;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;

public class JMS2SharedSubscriber implements MessageListener {

	public JMS2SharedSubscriber() {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");

			JMSConsumer jmsConsumer = jmsContext.createConsumer(topic, "sub:3e");
			jmsConsumer.setMessageListener(this);

			System.out.println("waiting for messages");
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println(message.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(() -> new JMS2SharedSubscriber());
	}
}