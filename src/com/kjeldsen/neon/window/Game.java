package com.kjeldsen.neon.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.KeyInput;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.objects.Block;
import com.kjeldsen.neon.objects.Player;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 968770233274510255L;

	private BufferedImage level = null;
	
	private boolean running = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	
	public static boolean DEBUG = false;
	
	Handler handler;
	Camera camera;
	
	Random rand = new Random();
	
	public void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level.png");
		
		camera = new Camera(0, 0);
		
		handler = new Handler();
		loadImageLevel(level);
		
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
		
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		g2d.translate(camera.getX(), camera.getY());
		handler.render(graphics);
		g2d.translate(-camera.getX(), -camera.getY());
		
		//                                             //
		/////////////////////////////////////////////////
		
		graphics.dispose();
		bs.show();
	}

	private void loadImageLevel(BufferedImage levelImage) {
		int w = levelImage.getWidth();
		int h = levelImage.getHeight();
		
		for(int x=0; x<w; x++) {
			for(int y=0; y<h; y++) {
				int pixel = levelImage.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 255 && blue == 255) handler.addObject(new Block(x*32, y*32, ObjectId.BLOCK));
				if(red == 0 && green == 0 && blue == 255) handler.addObject(new Player(x*32, y*32, handler, ObjectId.PLAYER));
			}
		}
	}
	
	public static void main(String[] args) {
		new Window(800, 600, "Neon Platform Game Prototype", new Game());
	}
}
