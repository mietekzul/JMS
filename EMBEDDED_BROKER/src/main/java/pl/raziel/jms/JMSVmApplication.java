package pl.raziel.jms;

import org.apache.activemq.broker.BrokerService;

import java.util.Arrays;
import java.util.List;

public class JMSVmApplication {

	private List<String> trades = Arrays.asList(
			"BUY AAPL 2000",
			"BUY IBM 4400",
			"BUT ATT 2400",
			"SELL AAPL 1000",
			"SELL IBM 2200",
			"SELL ATT 1200"
	);

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
			System.out.println("Embedded broker started");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startTradeProcessors() {
		new JMSVmReceiver(1);
		new JMSVmReceiver(2);
		new JMSVmReceiver(3);
		new JMSVmReceiver(4);
		new JMSVmReceiver(5);
		new JMSVmReceiver(6);
		new JMSVmReceiver(7);
		new JMSVmReceiver(8);
	}

	private void processTrades() {
		JMSVmSender sender = new JMSVmSender();
		for (String trade : trades) {
			sender.sendMessage(trade);
		}
	}
}
