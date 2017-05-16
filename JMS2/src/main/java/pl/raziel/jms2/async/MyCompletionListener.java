package pl.raziel.jms2.async;

import javax.jms.CompletionListener;
import javax.jms.Message;
import java.util.concurrent.CountDownLatch;

class MyCompletionListener implements CompletionListener {

	public CountDownLatch latch = null;

	public MyCompletionListener(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void onCompletion(Message message) {
		latch.countDown();
		System.out.println("message acknowledged.");
	}

	@Override
	public void onException(Message message, Exception e) {
		latch.countDown();
		System.err.println("unable to send a message.");
	}
}
