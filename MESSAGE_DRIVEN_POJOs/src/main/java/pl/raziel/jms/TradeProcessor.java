package pl.raziel.jms;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class TradeProcessor implements SessionAwareMessageListener {

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("Message: " + msg.getText());

			MessageProducer sender = session.createProducer(msg.getJMSReplyTo());
			TextMessage confMsg = session.createTextMessage("12345");
			confMsg.setJMSCorrelationID(msg.getJMSCorrelationID());
			sender.send(confMsg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
