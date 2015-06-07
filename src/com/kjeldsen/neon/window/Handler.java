package com.kjeldsen.neon.window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.objects.Block;
import com.kjeldsen.neon.objects.Flag;
import com.kjeldsen.neon.objects.Player;

public class Handler {

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	private Camera camera;
	private BufferedImage level1;
	private BufferedImage level2;
	
	public Handler(Camera camera, BufferedImageLoader loader) {
		this.camera = camera;
		level1 = loader.loadImage("/level1.png");
		level2 = loader.loadImage("/level2.png");
		loadImageLevel(level1);
	}
	
	public void tick() {
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).tick(objects);
		}
	}

	public void render(Graphics g) {
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).render(g);
		}
	}
	
	public void addObject(GameObject gameObject) {
		this.objects.add(gameObject);
	}
	
	public void removeObject(GameObject gameObject) {
		this.objects.remove(gameObject);
	}
	
	public void createLevel() {
		for( int i=0; i<2*Game.WIDTH+32; i += 32) {
			addObject(new Block(i, Game.HEIGHT-32, 0, ObjectId.BLOCK));
		}
		
		for( int i=0; i<Game.HEIGHT+32; i += 32) {
			addObject(new Block(0, i, 0, ObjectId.BLOCK));
		}
		
		for( int i=0; i<Game.HEIGHT+32; i += 32) {
			addObject(new Block(2*Game.WIDTH-32, i, 0, ObjectId.BLOCK));
		}
		
		for( int i=0; i<Game.WIDTH+32; i += 32) {
			addObject(new Block(Game.WIDTH/4+i, Game.HEIGHT-Game.HEIGHT/4-32, 0, ObjectId.BLOCK));
		}
	}
	
	public void loadImageLevel(BufferedImage levelImage) {
		int w = levelImage.getWidth();
		int h = levelImage.getHeight();
		
		for(int x=0; x<w; x++) {
			for(int y=0; y<h; y++) {
				int pixel = levelImage.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 255 && blue == 255) addObject(new Block(x*32, y*32, 0, ObjectId.BLOCK));
				if(red == 0 && green == 0 && blue == 255) addObject(new Player(x*32, y*32, this, ObjectId.PLAYER));
				if(red == 255 && green == 255 && blue == 0) addObject(new Flag(x*32, y*32, ObjectId.FLAG));
			}
		}
	}
	
	public void clearLevel() {
		objects.clear();
	}
	
	public void switchLevel() {
		clearLevel();
		camera.setX(0);
		
		switch(Game.LEVEL) {
		case 1:
			loadImageLevel(level2);
			Game.LEVEL += 1;
		case 2:
			loadImageLevel(level1);
			Game.LEVEL -= 1;
		}
	}
}
