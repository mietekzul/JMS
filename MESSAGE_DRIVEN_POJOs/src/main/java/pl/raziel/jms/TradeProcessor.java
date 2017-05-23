package pl.raziel.jms;

public class TradeProcessor/* implements SessionAwareMessageListener */ {

	/*@Override
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
	}*/

	public void placeTrade(String body) {
		System.out.println("Message: " + body);
	}
}
