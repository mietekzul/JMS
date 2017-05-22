package pl.raziel.jms.filebasedechnique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JMSReceiver implements MessageListener {

	private static String targetFile = "";

	private OutputStream os;

	@Override
	public void onMessage(Message message) {
		try {
			String filename = ((TextMessage) message).getText();
			System.out.println("Message received: " + filename);
			writeFileToTarget(readFileFromTemp(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] readFileFromTemp(String tempFileName) throws IOException {
		File file = new File(tempFileName);
		try (InputStream is = new FileInputStream(file)) {
			byte[] bytes = new byte[(int) file.length()];
			is.read(bytes);
			return bytes;
		}
	}

	public void writeFileToTarget(byte[] bytes) throws IOException {
		try (OutputStream os = new FileOutputStream(targetFile)) {
			os.write(bytes);
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
			System.out.println("Waiting for messages...");
		} catch (Exception up) {
			up.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(() -> new JMSReceiver()).start();
	}
}
