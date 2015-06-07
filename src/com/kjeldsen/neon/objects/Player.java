package com.kjeldsen.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.framework.Texture;
import com.kjeldsen.neon.window.Animation;
import com.kjeldsen.neon.window.Camera;
import com.kjeldsen.neon.window.Game;
import com.kjeldsen.neon.window.Handler;

public class Player extends GameObject {

	private static final float MAX_SPEED = 10;
	private float width = 32, height = 64;
	private float gravity = 0.5f;
	private int facing = 1;
	private Handler handler;
	private Texture tex = Game.getInstance();
	
	private Animation playerWalkRight, playerWalkLeft;
		
	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		
		playerWalkRight = new Animation(15, tex.player[0], tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6]);
		playerWalkLeft = new Animation(15, tex.player[7], tex.player[8], tex.player[9], tex.player[10], tex.player[11], tex.player[12], tex.player[13]);
	}

	@Override
	public void tick(LinkedList<GameObject> objects) {
		x += velX;
		y += velY;
		
		if(velX > 0) {
			facing = 1;
		} else if(velX < 0) {
			facing = -1;
		}
		
		if(falling || jumping) {
			velY += gravity;
			if( velY > MAX_SPEED ) {
				velY = MAX_SPEED;
			}
		}
		
		collision(objects);
		
		playerWalkLeft.runAnimation();
		playerWalkRight.runAnimation();
	}
	
	private void collision(LinkedList<GameObject> objects) {
		for(int i=0; i<handler.objects.size(); i++) {
			GameObject currentObject = objects.get(i);
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
			} else if(currentObject.getId() == ObjectId.FLAG) {
				if( getBounds().intersects(currentObject.getBounds())) {
					handler.switchLevel();
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if( jumping ) {
			if( facing == 1 ) {
				g.drawImage(tex.playerJump[2], (int)x, (int)y, null);
			} else {
				g.drawImage(tex.playerJump[3], (int)x, (int)y, null);
			}
		} else {
			if(velX != 0) {
				if( facing > 0 ) {
					playerWalkRight.drawAnimation(g, (int) x, (int) y);
				} else {
					playerWalkLeft.drawAnimation(g, (int) x, (int) y);	
				}
			} else {
				if( facing > 0 ) {
					g.drawImage(tex.player[0], (int) x, (int) y, null);
				} else {
					g.drawImage(tex.player[7], (int) x, (int) y, null);
				}
			}
		}
		
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

	public int getFacing() {
		return facing;
	}
}