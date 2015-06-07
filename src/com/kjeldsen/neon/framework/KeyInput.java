package com.kjeldsen.neon.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.kjeldsen.neon.objects.Bullet;
import com.kjeldsen.neon.objects.Player;
import com.kjeldsen.neon.window.Handler;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		Bullet bullet = null;		
		for(GameObject temp: handler.objects) {
			if(temp.getId() == ObjectId.PLAYER) {
				Player player = (Player) temp;
				
				if(key == KeyEvent.VK_D) temp.setVelX(5);
				if(key == KeyEvent.VK_A) temp.setVelX(-5);
				if(key == KeyEvent.VK_W && !temp.isJumping()) {
					temp.setJumping(true);
					temp.setVelY(-15);
				}
				if(key == KeyEvent.VK_SPACE) {
					bullet = new Bullet(player.getX(), player.getY()+32, ObjectId.BULLET, player.getFacing()*5);
				}
			}
		}
		
		if(bullet != null) {
			handler.addObject(bullet);
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
