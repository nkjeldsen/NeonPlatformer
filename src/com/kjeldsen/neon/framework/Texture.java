package com.kjeldsen.neon.framework;

import java.awt.image.BufferedImage;

import com.kjeldsen.neon.window.BufferedImageLoader;

public class Texture {

	SpriteSheet bs, ps;
	
	private BufferedImage blockSheet = null;
	private BufferedImage playerSheet = null;
	
	public BufferedImage[] block = new BufferedImage[2];
	public BufferedImage[] player = new BufferedImage[14];
	public BufferedImage[] playerJump = new BufferedImage[6];
	
	public Texture() {
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			blockSheet = loader.loadImage("/block_sheet.png");
			playerSheet = loader.loadImage("/player_sheet.png");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		bs = new SpriteSheet(blockSheet);
		ps = new SpriteSheet(playerSheet);
		
		getTextures();
	}

	private void getTextures() {
		block[0] = bs.grabImage(0, 0, 32, 32); //dirt block
		block[1] = bs.grabImage(1, 0, 32, 32); //grass block
		
		player[0] = ps.grabImage(0, 0, 32, 64); //idle player right
		player[1] = ps.grabImage(1, 0, 32, 64); //walking animation
		player[2] = ps.grabImage(2, 0, 32, 64); //walking animation
		player[3] = ps.grabImage(3, 0, 32, 64); //walking animation
		player[4] = ps.grabImage(4, 0, 32, 64); //walking animation
		player[5] = ps.grabImage(5, 0, 32, 64); //walking animation
		player[6] = ps.grabImage(6, 0, 32, 64); //walking animation
		
		player[7] = ps.grabImage(19, 0, 32, 64); //idle player left
		player[8] = ps.grabImage(18, 0, 32, 64); //walking animation
		player[9] = ps.grabImage(17, 0, 32, 64); //walking animation
		player[10] = ps.grabImage(16, 0, 32, 64); //walking animation
		player[11] = ps.grabImage(15, 0, 32, 64); //walking animation
		player[12] = ps.grabImage(14, 0, 32, 64); //walking animation
		player[13] = ps.grabImage(13, 0, 32, 64); //walking animation
		
		//jumping animation
		playerJump[0] = ps.grabImage(7, 1, 32, 64);
		playerJump[1] = ps.grabImage(8, 1, 32, 64);
		playerJump[2] = ps.grabImage(9, 1, 32, 64);
		playerJump[3] = ps.grabImage(10, 1, 32, 64);
		playerJump[4] = ps.grabImage(11, 1, 32, 64);
		playerJump[5] = ps.grabImage(12, 1, 32, 64);
	}
}
