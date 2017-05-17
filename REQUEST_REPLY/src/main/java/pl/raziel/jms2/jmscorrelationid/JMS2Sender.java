package pl.raziel.jms2.jmscorrelationid;


import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.UUID;

public class JMS2Sender {

	public static void main(String[] args) {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext()) {
			Queue queueReq = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
			Queue queueResp = jmsContext.createQueue("EM_JMS2_TRADE_RESP.Q");

			String uuid = UUID.randomUUID().toString();

			jmsContext
					.createProducer()
					.setJMSReplyTo(queueResp)
					.setProperty("Uuid", uuid)
					.send(queueReq, "BUY APPL 1000 SHARES");
			System.out.println("message sent");

			String filter = "MessageLink = '" + uuid + "'";
			String conf = jmsContext.createConsumer(queueResp, filter).receiveBody(String.class);
			System.out.println("conf: " + conf);
		}
	}
}
