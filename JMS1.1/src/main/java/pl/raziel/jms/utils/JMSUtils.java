package pl.raziel.jms.utils;

import javax.jms.JMSException;
import javax.jms.Message;

public class JMSUtils {
	public static void displayHeaders(Message message) throws JMSException {
		System.out.println("JMSDeliveryMode__ " + message.getJMSDeliveryMode());
		System.out.println("JMSExpiration__ " + message.getJMSExpiration());
		System.out.println("JMSPriority__ " + message.getJMSPriority());
		System.out.println("JMSMessageID__ " + message.getJMSMessageID());
		System.out.println("JMSRedelivered__ " + message.getJMSRedelivered());
		System.out.println("JMSTimestamp__ " + message.getJMSTimestamp());
		System.out.println("JMSType__ " + message.getJMSType());
		System.out.println("JMSDestination__ " + message.getJMSDestination());
		System.out.println("JMSReplyTo__ " + message.getJMSReplyTo());
//		System.out.println("JMSDeliveryTime__ " + message.getJMSDeliveryTime());
	}
}
