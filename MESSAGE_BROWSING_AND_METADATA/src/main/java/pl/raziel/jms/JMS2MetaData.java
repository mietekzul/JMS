package pl.raziel.jms;

import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import java.util.Enumeration;

public class JMS2MetaData {
	public static void main(String[] args) throws JMSException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();

		try (JMSContext jmsContext = cf.createContext()) {
			ConnectionMetaData metaData = jmsContext.getMetaData();

			System.out.println(" ");
			System.out.printf("JMS Version: %s%n", metaData.getJMSVersion());
			System.out.printf("JMS Provider: %s%n", metaData.getJMSProviderName());
			System.out.printf("JMS Provider Version: %s%n", metaData.getProviderVersion());
			System.out.printf("JMSX Properties Supported: %n");
			Enumeration<?> e = metaData.getJMSXPropertyNames();
			while (e.hasMoreElements()) {
				System.out.printf("\t%s%n", e.nextElement());
			}
		}
	}
}
