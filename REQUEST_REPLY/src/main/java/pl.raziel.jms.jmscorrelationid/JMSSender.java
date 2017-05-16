package pl.raziel.jms.jmscorrelationid;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSSender {

	public static void main(String[] args) throws JMSException {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queueReq = session.createQueue("EM_TRADE_REQ.Q");
		Queue queueResp = session.createQueue("EM_TRADE_RESP.Q");
		TextMessage message = session.createTextMessage("BUY AAPL 1000 SHARES");
		message.setJMSReplyTo(queueResp);
		MessageProducer sender = session.createProducer(queueReq);
		sender.send(message);
		System.out.println("\nMessage sent.\n");

		String filter = "JMSCorrelationID = '" + message.getJMSMessageID() + "'";
		MessageConsumer receiver = session.createConsumer(queueResp, filter);
		TextMessage msgresp = (TextMessage) receiver.receive();
		System.out.printf("confirmation received = %s%n", msgresp.getText());

		connection.close();
	}
}
