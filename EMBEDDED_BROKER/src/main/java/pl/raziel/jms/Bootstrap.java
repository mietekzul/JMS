package pl.raziel.jms;

import org.apache.activemq.broker.BrokerService;

public class Bootstrap {
	public static void main(String[] args) {
		new Thread(() -> new Bootstrap().startBroker()).start();
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
}
