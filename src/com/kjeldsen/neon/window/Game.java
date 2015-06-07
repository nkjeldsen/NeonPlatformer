package com.kjeldsen.neon.window;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 968770233274510255L;

	private boolean running = false;
	private Thread thread;
	
	public synchronized void start() {
		if( running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		System.out.println("Thread running");
	}
	
	public static void main(String[] args) {
		new Window(800, 600, "Neon Platform Game Prototype", new Game());
	}
}
