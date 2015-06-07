package com.kjeldsen.neon.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.kjeldsen.neon.window.Handler;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(GameObject temp: handler.objects) {
			if(temp.getId() == ObjectId.PLAYER) {
				if(key == KeyEvent.VK_D) temp.setVelX(5);
				if(key == KeyEvent.VK_A) temp.setVelX(-5);
				if(key == KeyEvent.VK_SPACE && !temp.isJumping()) {
					temp.setJumping(true);
					temp.setVelY(-15);
				}
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(GameObject temp: handler.objects) {
			if(temp.getId() == ObjectId.PLAYER) {
				if(key == KeyEvent.VK_D) temp.setVelX(0);
				if(key == KeyEvent.VK_A) temp.setVelX(0);
			}
		}		
	}
}
