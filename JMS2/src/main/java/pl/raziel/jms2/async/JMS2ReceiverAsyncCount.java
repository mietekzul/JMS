package pl.raziel.jms2.async;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Created by dlok on 16/05/2017.
 */
public class JMS2ReceiverAsyncCount implements MessageListener {

	JMSContext jmsContext = null;

	public JMS2ReceiverAsyncCount() {
		try {
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			jmsContext = cf.createContext(Session.SESSION_TRANSACTED);
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			consumer.setMessageListener(this);
			System.out.println("waiting for the messages");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("Received: " + message.getBody(String.class));

			if (message.getIntProperty("JMSXDeliveryCount") > 2) {
				System.err.println("Can't process message - sending to DLQ");
				jmsContext.commit();
				return;
			}

			Thread.sleep(5000);
			// simulated error
			jmsContext.rollback();
			System.err.println("error processing message, retries = " + message.getIntProperty("JMSXDeliveryCount"));
		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(() -> new JMS2ReceiverAsyncCount()).start();
	}
}
