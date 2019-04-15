package enemy;

import javafx.scene.image.Image;

/** This class contains the Wizard Morty enemy 
* It is part of a 2nd level of enemies (regular speed, regular health, and regular power)
*/
public class WizardMortyEnemy extends Enemy{
	private static int INITIAL_HEALTH = 40; //initial health of enemy
	private static int INITIAL_SPEED = 5; //initial speed of enemy
	private static int INITIAL_POWER = 2; //initial power of enemy
	private static Image sprite = new Image("file:img/wizardMorty.png"); //sprite of enemy
	private static Image selectedSprite = new Image("file:img/selectedEnemies/wizardMortySelected.png"); //sprite that runs when this enemy is selected
	private static Image characterImage = new Image("file:img/charactersImages/wizardMortyChar.png"); //image of the enemy
	private static int sx = 25, sy = 3, sw = 80, sh = 157; //values for the sprite
	private static int sxChange = 130, sxMax = 300, syUp = 335, syDown = 3, syRight = 500, syLeft = 170; //values for the sprite
	
	/*
	 * Constructor of the class
	 * This constructor only initializes all the variables in the super class
	 */
	public WizardMortyEnemy(int xCoor, int yCoor) {
		super(INITIAL_HEALTH, INITIAL_SPEED, INITIAL_POWER, sprite, selectedSprite, characterImage, xCoor, yCoor, sx, sy, sw, sh, 35, 60);
		setUpChangesInSprite(sxChange, sxMax, syUp, syDown, syRight, syLeft);
	}

	/*
	 * Returns the name of the enemy
	 */
	public String getName() {
		return "Morty";
	}
	
	/*
	 * Returns a description of the enemy
	 */
	@Override
	public String toString() {
		return "Health = " + super.getHealth() + "\nSpeed = " + super.getSpeed() +"\nPower = " + super.getPower();
	}
}
