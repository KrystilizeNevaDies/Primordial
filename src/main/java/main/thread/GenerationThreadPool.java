package main.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GenerationThreadPool {
	public static ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);
}