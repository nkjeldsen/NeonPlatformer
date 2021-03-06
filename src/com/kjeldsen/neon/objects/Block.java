package com.kjeldsen.neon.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.framework.Texture;
import com.kjeldsen.neon.window.Game;

public class Block extends GameObject {

	Texture tex = Game.getInstance();
	private int type;

	public Block(float x, float y, int type, ObjectId id) {
		super(x, y, id);
		this.type = type;
	}

	@Override
	public void tick(LinkedList<GameObject> objects) {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(tex.block[type], (int) x, (int) y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
}
