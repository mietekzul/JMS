package pl.raziel.jms.filebasedechnique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class JMSSender {

	private Connection connection;
	private Session session;
	private MessageProducer sender;
	private String uuid;

	private static String sourceFile = "";
	private static String tempDir = "";

	public void sendMessage() throws JMSException, IOException {

		String filename = copyFileToTemp();
		TextMessage message = session.createTextMessage(filename);
		sender.send(message);
		System.out.println("Message sent");

		connection.close();
		System.exit(0);    // wtf?
	}

	public String copyFileToTemp() throws IOException {
		File inFile = new File(sourceFile);
		String outFile = tempDir + UUID.randomUUID();

		try (InputStream is = new FileInputStream(inFile)) {
			try (OutputStream os = new FileOutputStream(outFile)) {
				byte[] bytes = new byte[(int) inFile.length()];
				is.read(bytes);
				os.write(bytes);
				return outFile;
			}
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
