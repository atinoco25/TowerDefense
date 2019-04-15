
package enemy;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** This class contains the Tank enemy 
* It is part of a 2nd level of enemies (regular speed, regular health, and regular power)
*/
public class TankEnemy extends Enemy {
	private static final int INITIAL_HEALTH = 40; //initial health of enemy
	private static final int INITIAL_SPEED = 5; //initial speed of enemy
	private static final int INITIAL_POWER = 2; //initial power of enemy
	private static final Image sprite = new Image("file:img/tankAnimation.png"); //sprite of enemy
	private static Image selectedSprite = new Image("file:img/selectedEnemies/tankAnimationSelected.png"); //sprite that runs when this enemy is selected
	private static Image characterImage = new Image("file:img/charactersImages/tankChar.png"); //image of the enemy
	private static final int sx = 7, sy = 0, sw = 50, sh = 60; //values for the sprite
	private static final int sxChange = 65, sxMax = 450, syUp = 0, syDown = 130, syRight = 195, syLeft = 65; //values for the sprite
	
	/*
	 * Constructor of the class
	 * This constructor only initializes all the variables in the super class
	 */
	public TankEnemy(int xCoor, int yCoor) {
		super(INITIAL_HEALTH, INITIAL_SPEED, INITIAL_POWER, sprite, selectedSprite, characterImage, xCoor, yCoor, sx, sy, sw, sh, 35, 60);
		setUpChangesInSprite(sxChange, sxMax, syUp, syDown, syRight, syLeft);
	}

	/*
	 * Returns the name of the enemy
	 */
	@Override
	public String getName() {
		return "Tank";
	}
	
	/*
	 * Returns a description of the enemy
	 */
	@Override
	public String toString() {
		return "Health = " + super.getHealth() + "\nSpeed = " + super.getSpeed() +"\nPower = " + super.getPower();
	}

}