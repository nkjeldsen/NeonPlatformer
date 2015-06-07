package com.kjeldsen.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.window.Game;
import com.kjeldsen.neon.window.Handler;

public class Player extends GameObject {

	private static final float MAX_SPEED = 10;
	private float width = 32, height = 64;
	private float gravity = 0.5f;
	private Handler handler;
		
	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;		
	}

	@Override
	public void tick(LinkedList<GameObject> objects) {
		x += velX;
		y += velY;
		
		if(falling || jumping) {
			velY += gravity;
			if( velY > MAX_SPEED ) {
				velY = MAX_SPEED;
			}
		}
		
		collision(objects);
	}
	
	private void collision(LinkedList<GameObject> objects) {
		for(GameObject currentObject: handler.objects) {
			if(currentObject.getId() == ObjectId.BLOCK) {
				if(getBoundsTop().intersects(currentObject.getBounds())) {
					y = currentObject.getY() + (float) currentObject.getBounds().getHeight();
					velY = 0;
				}
				
				if(getBounds().intersects(currentObject.getBounds())) {
					y = currentObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}
				else {
					falling = true;
				}
				
				if(getBoundsRight().intersects(currentObject.getBounds())) {
					x = currentObject.getX() - width;					
				}

				if(getBoundsLeft().intersects(currentObject.getBounds())) {
					x = currentObject.getX() + (float) currentObject.getBounds().getWidth();
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int) x, (int) y, (int) width, (int) height);

		if( Game.DEBUG ) {
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.red);
			g2d.draw(getBounds());
			g2d.draw(getBoundsRight());
			g2d.draw(getBoundsLeft());
			g2d.draw(getBoundsTop());
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) (x + (width/4)), (int) (y+height/2), (int) width/2, (int) height/2); 
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) (x + (width/4)), (int) (y), (int) width/2, (int) height/2); 
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle((int) (x + width -5), (int) y+5, (int) 5, (int) height-10); 
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y+5, (int) 5, (int) height-10); 
	}
}