package pl.raziel.jms.streamingbased;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class JMSReceiver implements MessageListener {

	private static String targetFile = "";

	private OutputStream os;

	@Override
	public void onMessage(Message message) {
		try {
			if (message.propertyExists("sequenceMarker")) {
				String marker = message.getStringProperty("sequenceMarker");
				System.out.println("Received Marker: " + marker);

				if ("START".equals(marker)) {
					os = new FileOutputStream(targetFile);
				}

				if ("END".equals(marker)) {
					os.close();
				}
			} else {
				BytesMessage msg = (BytesMessage) message;
				System.out.println("Received message");
				byte[] bytes = new byte[new Long(msg.getBodyLength()).intValue()];
				msg.readBytes(bytes);
				os.write(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JMSReceiver() {
		try {
			Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_IMAGE.Q");
			MessageConsumer receiver = session.createConsumer(queue);
			receiver.setMessageListener(this);
			os = new FileOutputStream(targetFile);
			System.out.println("Waiting for messages");
		} catch (JMSException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(() -> new JMSReceiver()).start();
	}
}
