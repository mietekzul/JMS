package pl.raziel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import java.util.Enumeration;

public class JMSMetaData {

	public static void main(String[] args) throws JMSException {
		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
		ConnectionMetaData metaData = connection.getMetaData();

		System.out.println(" ");
		System.out.printf("JMS Version: %s%n", metaData.getJMSVersion());
		System.out.printf("JMS Provider: %s%n", metaData.getJMSProviderName());
		System.out.printf("JMS Provider Version: %s%n", metaData.getProviderVersion());
		System.out.printf("JMSX Properties Supported: %n");
		Enumeration<?> e = metaData.getJMSXPropertyNames();
		while (e.hasMoreElements()) {
			System.out.printf("\t%s%n", e.nextElement());
		}

		connection.close();
	}
}
