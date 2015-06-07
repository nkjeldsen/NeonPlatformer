package com.kjeldsen.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;

public class Block extends GameObject {

	public Block(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	@Override
	public void tick(LinkedList<GameObject> objects) {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawRect((int) x, (int) y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
}
