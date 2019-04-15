package enemy;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * @author Alexis Tinoco
 * This class in the base of all the enemies
 */
public abstract class Enemy{
	private int health;
	private final int INITIAL_HEALTH;
	private int speed;
	private int power;
	private Image sprite, selectedSprite, characterImg; //these images contains the images of the enemy including sprite and enemy face
	private boolean isDead; //true if dead, false if not
	private int currentLevel; //keeps track of the enemy level
	private int currentX, currentY; //coordinates
	private int futureX, futureY; //helpful to determine direction the enemy should walk into
  private int sx, sy, sw, sh; //sy and sy = sprite's x and y coor to start, sw and sh = sprite's img size
  private int sxChange, sxMax, syUp, syDown, syRight, syLeft, sxOriginal; //for help identifying what image in sprite should be drawn
  private int imageHeight, imageWidth; //size of img drawn in graphics context
  private boolean freeze; //true if enemy is currently freeze, false if not
  private int freezeLoops; //this variable determines how many frames will the enemy be freezed
  private int currentHit, hitLoops; //determines if enemy was hit recently
  private final int loopsToShowDamageHits = 6; //how many frames the damage will appear on screen
  private boolean isSelected; //true if enemy is currently selected by user
  private boolean killedByBase; //determines if enemy was killed by user or base
	
  /*
   * Constructor of the class, it basically initializes all of the instance variables
   */
	public Enemy(int newHealth, int newSpeed, int newPower, Image spriteImg, Image newSelectedSprite, Image newCharacterImg, int xStart, int yStart, 
			int newSx, int newSy, int newSw, int newSh, int imgWidth, int imgHeight) {
		INITIAL_HEALTH = newHealth;
		selectedSprite = newSelectedSprite;
		health = newHealth;
		speed = newSpeed;
		power = newPower;
		sprite = spriteImg;
		characterImg = newCharacterImg;
		isDead = false;
		currentLevel = 1;
		currentX= futureX= xStart;
		currentY= futureY= yStart;
		sx= newSx;
  	sy= newSy;
  	sw= newSw;
  	sh= newSh;
  	freeze= false;
  	freezeLoops = 0;
  	imageHeight = imgHeight;
  	imageWidth = imgWidth;
  	sxMax = 10000;
  	hitLoops = 0;
  	isSelected = false;
  	killedByBase = false;
	}
	
	/*
	 * This method will allow the enemy to receive damage, 
	 *   and mark it as dead if it has 0 health left
	 *   it will also remove enemy from canvas
	 */
	public void receiveDamage(int damage) {
		health -= damage;
		
		if(isDead == true)
			return;
		
		else if (health<=0) {
			isDead = true;
			System.out.println("Enemy Killed");
			return;
		}
		else {
			currentHit = damage;
			hitLoops = loopsToShowDamageHits;
		}
	}
	
	/*
	 * The following are getters methods
	 */
	public boolean isDead() {
		return isDead;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getEnemyLevel() {
		return currentLevel;
	}
	
	public int getPower() {
		return power;
	}
	
	public Image getImg() {
		return sprite;
	}
	
	public boolean wasKilledByBase() {
		return killedByBase;
	}
	
	/*
	 * This method will return the real X coordinates
	 */
	public int getRealXCoor() {
		return currentX;
	}
	
	/*
	 * This method will return the real Y coordinates
	 */
	public int getRealYCoor() {
		return currentY;
	}
	
	public Image getCharacterImg() {
		return characterImg;
	}
	
	/*
	 * This method will return the current X coordinates of enemy divided by 35
	 * Example, if current coordinate is 75, then the current tile is 75/35 which is the tile 2
	 */
	public int getBase16XCoor() {
		return (int) Math.floor(currentX/35);
	}
	
	/*
	 * This method will return the current Y coordinates of enemy divided by 35
	 * Example, if current coordinate is 75, then the current tile is 75/35 which is the tile 2
	 */
	public int getBase16YCoor() {
		return (int) Math.floor(currentY/35);
	}
	
	public int getHealth() {
		return health;
	}
	
	/*
	 * This method will freeze the enemy for a given amount of frames
	 */
	public void freeze(int loopsToFreeze) {
		freezeLoops = loopsToFreeze;
		freeze = true;
	}
	
	/*
	 * The following are setters methods
	 */
	protected void setHealth(int newHealth) {
		health = newHealth;
	}
	
	protected void setSpeed(int newSpeed) {
		speed = newSpeed;
	}
	
	protected void setPower(int newPower) {
		power = newPower;
	}
	
	protected void increaseLevel() {
		currentLevel++;
	}
	
	public void setKilledByBase() {
		killedByBase = true;
	}
	
	public void setSelected() {
		isSelected = true;
	}
	
	public void unSelect() {
		isSelected = false;
	}
	
	/*
	 * This method returns attack power, and starts the attack aninmation
	 */
	public int attack() {
		return power;
	}
	
	/*
	 *This method will move the enemy to the given coordinates
	 */
	public void moveTo(int newX, int newY) {
		if(isDead == false && freeze == false) {
			futureX = newX;
			futureY = newY;
		}
		
		if(freeze == true) {
			freezeLoops--;
			if(freezeLoops == 0) {
				freeze = false;
			}
		}
	}
	
	/*
	 * This method will draw the enemy into the canvas that the enemy has
	 */
	public void drawCurrentLocation(int tileSize, Canvas canvas) {
		if(isDead == true)
			return;
		
		//only run if the enemy moved
		if(currentX != futureX || currentY != futureY) {
			//determine the direction that the enemy should walk
			if(currentX < futureX)
				sy = syRight;
			if(currentX > futureX)
				sy = syLeft;
			if(currentY < futureY)
				sy = syDown;
			if(currentY > futureY)
				sy = syUp;
			
			//determine if the sprite needs to start from beginning
			sx += sxChange;
			if(sx > sxMax) {
				sx = sxOriginal; 
			}		
		}
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//determine which sprite to draw. If enemy is currently selected, draw the sprite for selected enemy
		if(isSelected == false)
			gc.drawImage(sprite, sx, sy, sw, sh, futureX, futureY-35, imageWidth, imageHeight);
		else {
			gc.drawImage(selectedSprite, sx, sy, sw, sh, futureX, futureY-35, imageWidth, imageHeight);
		}
		
		//The following will draw the enemy health bar
		gc.setFill(Color.RED);
		double percentage = ((double)health/INITIAL_HEALTH);
		gc.fillRect(futureX, futureY+25, percentage*35, 10);
		gc.setFill(Color.BLACK);
		gc.strokeRect(futureX, futureY+25, 35, 10);
				
		//The following will draw the enemy hit damage icon
		if(hitLoops > 0) {
			hitLoops--;
			Image damageImg = new Image("file:img/damageStar.png");

			gc.drawImage(damageImg, futureX+30, futureY+15, 30, 30);
			gc.fillText(currentHit+"", futureX+35, futureY+35);
		}
		else {
			currentHit = 0;
		}
		
		//move the enemy in the coordinates
		currentX = futureX;
		currentY = futureY;
	}
	
	/*
	 * This method will take more variables to determine more sizes in the sprite
	 */
	public void setUpChangesInSprite(int sxChange, int sxMax, int syUp, int syDown, int syRight, int syLeft) {
		this.sxChange = sxChange;
		this.sxMax = sxMax;
		this.syDown = syDown;
		this.syUp = syUp;
		this.syLeft = syLeft;
		this.syRight = syRight;
		sxOriginal = sx;
	}
	
	
	public abstract String toString();
	public abstract String getName();
}