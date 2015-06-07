package com.kjeldsen.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;

public class Player extends GameObject {

	private static final float MAX_SPEED = 10;
	private float width = 32, height = 64;
	private float gravity = 0.5f;
	
	public Player(float x, float y, ObjectId id) {
		super(x, y, id);
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
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height); 
	}

}
