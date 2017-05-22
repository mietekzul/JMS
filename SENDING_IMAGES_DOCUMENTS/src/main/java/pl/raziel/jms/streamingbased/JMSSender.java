package pl.raziel.jms.streamingbased;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class JMSSender {

	private Connection connection;
	private Session session;
	private MessageProducer sender;

	private static String sourceFile = "";
	private static String tempDir = "";

	public void sendMessage() throws IOException, JMSException {

		String uuid = UUID.randomUUID().toString();

		InputStream is = new FileInputStream(sourceFile);
		sendStream(null, "START", uuid);

		byte[] bytes = new byte[1_000_000];
		while (is.read(bytes) >= 0) {
			sendStream(bytes, null, uuid);
		}

		sendStream(null, "END", uuid);
		is.close();

		System.out.println("Image sent complete");
		connection.close();
		System.exit(0);
	}

	public void sendStream(byte[] bytes, String marker, String uuid) throws JMSException {
		BytesMessage msg = session.createBytesMessage();
		msg.setStringProperty("JMSXGroupID", uuid);
		if (bytes == null) {
			msg.setStringProperty("sequenceMarker", marker);
			System.out.println("Sending " + marker);
		} else {
			msg.writeBytes(bytes);
			System.out.println("Streaming...");
		}
	}

	public JMSSender() throws JMSException {
		connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("EM_IMAGE.Q");
		sender = session.createProducer(queue);
	}

	public static void main(String[] args) throws JMSException, IOException {
		new JMSSender().sendMessage();
	}
}
