package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSSender {
	public static void main(String[] args) throws JMSException {

		try (Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61888").createConnection()) {
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			MessageProducer sender = session.createProducer(queue);
			TextMessage message = session.createTextMessage("BUY AAPL 1000 SHARES");
			sender.send(message);
			System.out.println("Message sent.");
		}
	}


}
