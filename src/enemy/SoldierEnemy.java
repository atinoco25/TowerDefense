package enemy;

import javafx.scene.image.Image;

/** This class contains the Soldier enemy 
* It is part of a 1st level of enemies (slow, not much health, and a little power)
*/
public class SoldierEnemy extends Enemy{
	private static int INITIAL_HEALTH = 20; //initial health of enemy
	private static int INITIAL_SPEED = 3; //initial speed of enemy
	private static int INITIAL_POWER = 1; //initial power of enemy
	private static Image sprite = new Image("file:img/soldierAnimation.png"); //sprite of enemy
	private static Image selectedSprite = new Image("file:img/selectedEnemies/soldierAnimationSelected.png"); //sprite that runs when this enemy is selected
	private static Image characterImage = new Image("file:img/charactersImages/soldierChar.png"); //image of the enemy
	private static int sx = 15, sy = 0, sw = 35, sh = 60; //values for the sprite
	private static int sxChange = 65, sxMax = 490, syUp = 135, syDown = 0, syRight = 65, syLeft = 195; //more values for sprite
	
	/*
	 * Constructor of the class
	 * This constructor only initializes all the variables in the super class
	 */
	public SoldierEnemy(int xCoor, int yCoor) {
		super(INITIAL_HEALTH, INITIAL_SPEED, INITIAL_POWER, sprite, selectedSprite, characterImage, xCoor, yCoor, sx, sy, sw, sh, 35, 60);
		setUpChangesInSprite(sxChange, sxMax, syUp, syDown, syRight, syLeft);
	}

	/*
	 * Returns the name of the enemy
	 */
	@Override
	public String getName() {
		return "Soldier";
	}
	
	/*
	 * Returns a description of the enemy
	 */
	@Override
	public String toString() {
		return "Health = " + super.getHealth() + "\nSpeed = " + super.getSpeed() +"\nPower = " + super.getPower();

	}
}