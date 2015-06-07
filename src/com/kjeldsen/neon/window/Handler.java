package com.kjeldsen.neon.window;

import java.awt.Graphics;
import java.util.LinkedList;

import com.kjeldsen.neon.framework.GameObject;
import com.kjeldsen.neon.framework.ObjectId;
import com.kjeldsen.neon.objects.Block;

public class Handler {

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void tick() {
		for(GameObject currentObject: objects) {
			currentObject.tick(objects);
		}
	}

	public void render(Graphics g) {
		for(GameObject currentObject: objects) {
			currentObject.render(g);
		}
	}
	
	public void addObject(GameObject gameObject) {
		this.objects.add(gameObject);
	}
	
	public void removeObject(GameObject gameObject) {
		this.objects.remove(gameObject);
	}
	
	public void createLevel() {
		for( int i=0; i<Game.WIDTH+32; i += 32) {
			addObject(new Block(i, Game.HEIGHT-32, ObjectId.BLOCK));
		}
	}
}
