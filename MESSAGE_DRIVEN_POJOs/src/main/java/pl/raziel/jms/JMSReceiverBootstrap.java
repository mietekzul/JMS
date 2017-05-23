package pl.raziel.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMSReceiverBootstrap {
	public static void main(String[] args) {
		new Thread(() -> new JMSReceiverBootstrap().startReceivers()).start();
	}

	private void startReceivers() {
		new ClassPathXmlApplicationContext("app-context.xml");
		System.out.println("MDP Receivers Started");
	}
}
