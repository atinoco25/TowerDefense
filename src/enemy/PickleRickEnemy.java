package enemy;

import javafx.scene.image.Image;

/** This class contains the pickle rick enemy 
* It is part of a 3rd level of enemies (faster, more health, and more  power)
*/
public class PickleRickEnemy extends Enemy{
	private static int INITIAL_HEALTH = 60; //initial health of enemy
	private static int INITIAL_SPEED = 8; //initial speed of enemy
	private static int INITIAL_POWER = 3; //initial power of enemy
	private static Image sprite = new Image("file:img/pickleRick.png"); //sprite of enemy
	private static Image selectedSprite = new Image("file:img/selectedEnemies/pickleRickSelected.png"); //sprite that runs when this enemy is selected
	private static Image characterImage = new Image("file:img/charactersImages/pickleRickImage.png"); //image of the enemy
	private static int sx = 15, sy = 3, sw = 95, sh = 157; //values for the sprite
	private static int sxChange = 130, sxMax = 380, syUp = 335, syDown = 3, syRight = 500, syLeft = 170; //more values for sprite
	
	/*
	 * Constructor of the class
	 * This constructor only initializes all the variables in the super class
	 */
	public PickleRickEnemy(int xCoor, int yCoor) {
		super(INITIAL_HEALTH, INITIAL_SPEED, INITIAL_POWER, sprite, selectedSprite, characterImage, xCoor, yCoor, sx, sy, sw, sh, 35, 60);
		setUpChangesInSprite(sxChange, sxMax, syUp, syDown, syRight, syLeft);
	}

	/*
	 * Returns the name of the enemy
	 */
	public String getName() {
		return "Pickle Rick";
	}
	
	/*
	 * Returns a description of the enemy
	 */
	@Override
	public String toString() {
		return "Health = " + super.getHealth() + "\nSpeed = " + super.getSpeed() +"\nPower = " + super.getPower();
	}
}