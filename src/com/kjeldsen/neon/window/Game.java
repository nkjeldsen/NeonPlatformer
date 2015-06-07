package com.kjeldsen.neon.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.KeyInput;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.framework.Texture;
import com.kjeldsen.neon.objects.Block;
import com.kjeldsen.neon.objects.Flag;
import com.kjeldsen.neon.objects.Player;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 968770233274510255L;

	private BufferedImage level = null, level2 = null, background = null;
	
	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	
	public static boolean DEBUG = false;
	private static Texture texture;
	
	private Handler handler;
	private Camera camera;
	
	public static int LEVEL = 1; 
	
	public void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		texture = new Texture();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		background = loader.loadImage("/background.png");
		
		camera = new Camera(0, 0);
		handler = new Handler(camera, loader);
		
		this.addKeyListener(new KeyInput(handler));
	}
	
	public synchronized void start() {
		if( running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick() {
		handler.tick();
		
		for(GameObject currentObject: handler.objects) {
			if(currentObject.getId() == ObjectId.PLAYER) {
				camera.tick(currentObject);
				break;
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) graphics;
		
		/////////////////////////////////////////////////
		//                  DRAW HERE                  //
		
		graphics.setColor(new Color(25, 191, 224));
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		graphics.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		
		g2d.translate(camera.getX(), camera.getY());
		handler.render(graphics);
		g2d.translate(-camera.getX(), -camera.getY());
		
		//                                             //
		/////////////////////////////////////////////////
		
		graphics.dispose();
		bs.show();
	}
	
	public static Texture getInstance() {
		return texture;
	}
	
	public static void main(String[] args) {
		new Window(800, 600, "Neon Platform Game Prototype", new Game());
	}
}
