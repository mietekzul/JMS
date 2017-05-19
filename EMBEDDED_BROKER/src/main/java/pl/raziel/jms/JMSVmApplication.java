package pl.raziel.jms;

import org.apache.activemq.broker.BrokerService;

import java.util.Arrays;
import java.util.List;

public class JMSVmApplication {

	private List<String> trades = Arrays.asList();

	public static void main(String[] args) {
		new Thread(() -> {
			JMSVmApplication app = new JMSVmApplication();
			app.startBroker();
			app.startTradeProcessors();
			app.processTrades();
		}).start();
	}

	public void startBroker() {
		try {
			BrokerService brokerService = new BrokerService();
			brokerService.addConnector("tcp://localhost:61888");
			brokerService.setBrokerName("embedded1");
			brokerService.setPersistent(false);
			brokerService.start();
			System.out.println("embedded broker started");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startTradeProcessors() {
		new JMSVmReceiver(1);
	}

	private void processTrades() {
		JMSVmSender sender = new JMSVmSender();
		for (String trade : trades) {
			sender.sendMessage(trade);
		}
	}
}
