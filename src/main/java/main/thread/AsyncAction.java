package main.thread;

import java.util.function.BiConsumer;

public class AsyncAction<A, B> implements Runnable {
	
	private BiConsumer<A, B> consumer;
	private A first;
	private B second;
	
	public AsyncAction(BiConsumer<A, B> consumer, A first, B second) {
		this.consumer = consumer;
		this.first = first;
		this.second = second;
	}

	@Override
	public void run() {
		consumer.accept(first, second);
	}
}