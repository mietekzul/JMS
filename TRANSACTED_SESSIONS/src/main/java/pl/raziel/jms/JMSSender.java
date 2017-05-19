package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSSender {
	public static void main(String[] args) throws Exception {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE.Q");
		MessageProducer sender = session.createProducer(queue);

		TextMessage message1 = session.createTextMessage("BUY AAPL 1000 SHARES");
		sender.send(message1);
		System.out.println("Message 1 sent.");

		Thread.sleep(4000);

//		if (true) {
//			connection.close();
//			throw new Exception();
//		}

		TextMessage message2 = session.createTextMessage("BUY IBM 2000 SHARES");
		sender.send(message2);
		System.out.println("Message 2 sent.");

		session.commit();
		connection.close();
	}
}
