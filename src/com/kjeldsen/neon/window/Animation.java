package com.kjeldsen.neon.window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	
	private BufferedImage[] images;
	private BufferedImage currentImage;
	
	public Animation(int speed, BufferedImage... images) {
		this.speed = speed;
		this.frames = images.length;
		this.images = images;
	}
	
	public void runAnimation() {
		index++;
		if(index > speed) {
			index = 0;
			nextFrame();
		}
	}

	private void nextFrame() {
		for(int i=0; i<frames; i++) {
			if(count == i) {
				currentImage = images[i];
			}
		}
		
		count++;
		
		if(count > frames) {
			count = 0;
		}
	}
	
	public void drawAnimation(Graphics g, int x, int y) {
		g.drawImage(currentImage, x, y, null);
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
		g.drawImage(currentImage, x, y, scaleX, scaleY, null);
	}
}
