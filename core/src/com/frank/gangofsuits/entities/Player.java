package com.frank.gangofsuits.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.frank.gangofsuits.GangOfSuits;
import com.frank.gangofsuits.stages.DeathScreen;
import com.frank.gangofsuits.stages.IntroStage;
import com.frank.gangofsuits.utilities.Constants;

enum PlayerState {
	ALIVE, DEAD
}
public class Player {
	private Sprite sprite;
	private PlayerState state = PlayerState.ALIVE;
	private int health = 100;
	private GangOfSuits game;
	private boolean invincible = false;
	
	public Player(GangOfSuits game) {
		this.game = game;
		
		sprite = new Sprite(new Texture(Gdx.files.internal("spritesheets/char_sprite.png")));
		sprite.setPosition((Constants.WORLD_WIDTH / 2) - (sprite.getWidth()), (Constants.WORLD_HEIGHT / 2) - (sprite.getHeight()));
		sprite.setRotation(0);
	}
	
	public void draw(Batch batch) {
		batch.draw(sprite, sprite.getX(), sprite.getY());
	}
	public void update() {
		if (health <= 0 && !invincible) {
			state = PlayerState.DEAD;
			game.setScreen(new DeathScreen());
		}

		float playerSpeed = 10.0f;
		float velocityX = 0.0f;
		float velocityY = 0.0f;
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			velocityX -= Gdx.graphics.getDeltaTime() * playerSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.D)) 
			velocityX += Gdx.graphics.getDeltaTime() * playerSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.W)) 
			velocityY += Gdx.graphics.getDeltaTime() * playerSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.S)) 
			velocityY -= Gdx.graphics.getDeltaTime() * playerSpeed;

		float length = (float) Math.sqrt((Math.pow(velocityX, 2)) + (Math.pow(velocityY, 2)));
		if (length > 0.0f) {
			velocityX /= length;
			velocityY /= length;
		}

		// Only round the x value, rounding the y value will make diagonal movement choppy
	    IntroStage.camera.translate(Math.round(velocityX), velocityY);
	    Vector3 camPosition = IntroStage.camera.position;
	    sprite.setPosition(camPosition.x, camPosition.y);
	}
	public PlayerState getState() {
		return state;
	}
	public void setPlayerState(PlayerState newState) {
		this.state = newState;
	}
	public boolean isInvinsible() {
		return invincible;
	}
	public void setInvincible(boolean isInvincible) {
		this.invincible = isInvincible;
	}
	public Vector2 getPosition() {
		return new Vector2(sprite.getX(), sprite.getY());
	}
}
