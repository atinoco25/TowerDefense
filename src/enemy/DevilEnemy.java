
package enemy;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class contains the flash enemy 
 * It is part of a 3rd level of enemies (faster, more health, and more  power)
 */
public class DevilEnemy extends Enemy{
	private static final int INITIAL_HEALTH = 60; //initial health of enemy
	private static final int INITIAL_SPEED = 8; //initial speed of enemy
	private static final int INITIAL_POWER = 3; //initial power of enemy
	private static final Image sprite = new Image("file:img/devilAnimation.png"); //sprite of enemy
	private static Image selectedSprite = new Image("file:img/selectedEnemies/devilAnimationSelected.png"); //sprite that runs when this enemy is selected
	private static Image characterImage = new Image("file:img/charactersImages/devilChar.png"); //image of the enemy
	private static int sx = 2, sy = 0, sw = 62, sh = 40; //values for the sprite
	private static int sxChange = 63, sxMax = 200, syUp = 78, syDown = 0, syRight = 135, syLeft = 200; //values for the sprite
	
	/*
	 * Constructor of the class
	 * This constructor only initializes all the variables in the super class
	 */
	public DevilEnemy(int xCoor, int yCoor) {
		super(INITIAL_HEALTH, INITIAL_SPEED, INITIAL_POWER, sprite, selectedSprite, characterImage, xCoor, yCoor, sx, sy, sw, sh, 50, 60);
		setUpChangesInSprite(sxChange, sxMax, syUp, syDown, syRight, syLeft);
	}

	/*
	 * Returns the name of the enemy
	 */
	@Override
	public String toString() {
		return "Health = " + super.getHealth() + "\nSpeed = " + super.getSpeed() +"\nPower = " + super.getPower();
	}

	/*
	 * Returns a description of the enemy
	 */
	@Override
	public String getName() {
		return "Devil";
	}
}