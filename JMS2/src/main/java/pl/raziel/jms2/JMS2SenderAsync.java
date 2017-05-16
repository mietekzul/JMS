package pl.raziel.jms2;


import javax.jms.CompletionListener;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.concurrent.CountDownLatch;


public class JMS2SenderAsync {

	public static void main(String[] args) throws InterruptedException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");

			CountDownLatch latch = new CountDownLatch(1);
			CompletionListener cl = new MyCompletionListener(latch);

			jmsContext
					.createProducer()
					.setAsync(cl)
//					.setAsync(new MyCompletionListener(new CountDownLatch(1)))
					.send(queue, "Test message");
			System.out.println("Message sent");

			for (int i = 0; i < 5; i++) {
				System.out.println("processing...");
				Thread.sleep(1500);
			}

			System.out.println("end processing.");
		}
	}
}


