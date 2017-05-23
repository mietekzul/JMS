package pl.raziel.jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

public class JMSSender {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");

		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");

//		MessageCreator mc = session -> {
//			TextMessage message = session.createTextMessage("BUY AAPL 2000 SHARES");
//			message.setStringProperty("Trader", "Mark");
//			return message;
//		};

		MessagePostProcessor mpp = message -> {
			message.setStringProperty("Trader", "Mark");
			return message;
		};

		jmsTemplate.convertAndSend((Object) "BUY AAPL 2000 SHARES", mpp);
		System.out.println("Message sent");
	}
}
