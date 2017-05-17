package pl.raziel.jms.jmscorrelationid;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSReceiver {

	public static void main(String[] args) throws JMSException, InterruptedException {

		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_TRADE_REQ.Q");
		MessageConsumer receiver = session.createConsumer(queue);
		TextMessage message = (TextMessage) receiver.receive();
		System.out.printf("processing trade: %s%n", message.getText());
		Thread.sleep(4000);
		String confirmation = "EQ-12345";
		System.out.printf("trade confirmation: %s%n", confirmation);

		TextMessage outmsg = session.createTextMessage(confirmation);
//		no need in queue
//		outmsg.setJMSCorrelationID(message.getJMSMessageID());
		MessageProducer sender = session.createProducer(message.getJMSReplyTo());
		sender.send(outmsg);

		connection.close();
	}
}
