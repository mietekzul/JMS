package pl.raziel.jms;

import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;

import com.sun.messaging.ConnectionFactory;

public class JMS2Receiver implements MessageListener {

	public JMS2Receiver() {
		try {
			ConnectionFactory cf = new ConnectionFactory();
			JMSContext jmsContext = cf.createContext();
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			jmsContext.createConsumer(queue).setMessageListener(this);
			System.out.println("waiting on messages");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onMessage(Message message) {
		try {			
			System.out.println(message.getBody(String.class));
			if (message.getLongProperty("count") == 1000) {
				long endTime = new Long(new java.util.Date().getTime());
				System.out.println("\nElapsed Time: " + (endTime - message.getLongProperty("startTime")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) throws Exception {		
	    new Thread() { 
	    	public void run() { 
	    		new JMS2Receiver();
	    }}.start();
	}
}
		
		













