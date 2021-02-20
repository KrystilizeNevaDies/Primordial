package main.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPools {
	public static ExecutorService CHUNKGENERATION = Executors.newFixedThreadPool(4);
	public static ExecutorService BLOCKGROUPPLACEMENT = Executors.newFixedThreadPool(2);
}