package com.kjeldsen.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;

public class Bullet extends GameObject {

	public Bullet(float x, float y, ObjectId id, int velX) {
		super(x, y, id);
		this.velX = velX;
	}

	@Override
	public void tick(LinkedList<GameObject> objects) {
		x += velX;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
