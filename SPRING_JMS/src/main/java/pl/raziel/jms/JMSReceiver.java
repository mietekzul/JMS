package pl.raziel.jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class JMSReceiver {
	public static void main(String[] args) throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");

		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		System.out.println("Waiting 4 messages");
		TextMessage message = (TextMessage) jmsTemplate.receiveSelected("Trader = 'Mark'");		// filter

		System.out.println("Message: " + message.getText());
		System.out.println("Trader: " + message.getStringProperty("Trader"));

		System.exit(0);
	}
}
