package tower;

import java.util.ArrayList;
import enemy.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * A Tower that can be selected and built onto the map by the player
 * of a various type. Towers can be upgraded to be stronger in some
 * way. 
 * 
 * After creating the graphics context object in the MainGame. Use:
 * 		 Tower.setGraphicsContext(gc); 
 * to allow all towers to draw to the canvas without having to
 * pass it into every method.
 * 
 * @author Carl Wernicke
 * */

public abstract class Tower {
	
	protected String description;
	protected String name;
	protected Image image;
	protected String projectileType;
	protected int attackRange;
	protected int damageValue;
	protected int x, y;
	protected int cost;
	protected int upgradeCost;
	protected boolean upgraded = false;
	public static GraphicsContext gc;
	public final static int coordOffset = 18;
	protected boolean isAttacking;
	public static int speedup = 1;
	private static int savedPauseSpeed;
	
	
	public Tower(String imagePath, int x, int y) {
		this.setImage(new Image(imagePath));
		this.x = x;
		this.y = y;
	}
	/**
	 * @param the graphics context used by the game to display the map
	 * */
	public static void setGraphicsContext(GraphicsContext newGc) {
		gc = newGc;
	}
	
	/**
	 * Displays the tower's range while it's being drawn as a ghost image
	 * */
    public void displayRange() {
    	Paint gcColor = gc.getFill();
    	//set color as somewhat transparent to show what lies in the range
    	Color rangeShowColor = new Color(0, 0.7, 1, 0.3);
    	gc.setFill(rangeShowColor);
		gc.fillOval(x - (attackRange * 35) + coordOffset, y - (attackRange * 35) + coordOffset, 
				(attackRange * 35) * 2, (attackRange * 35) * 2);
		
		gc.setFill(gcColor);
	}

    //draws the tower
    public void drawTower() {
    	gc.drawImage(this.image, x, y);
    }
    
    /**
     * Method to draw the tower attack animation if it's attacking
     * */
    public abstract void drawAttack();
    
    /**
	 * Method to upgrade the current tower.
	 * Will change different aspects depending on the tower type
	 * */
	public abstract void upgrade();
	
	/**
	 * @param an ArrayList of enemies which are within the tower's range
	 * */
	public abstract void attack(ArrayList<Enemy> enemyList);
	
	/**
	 * Method used to return a list of enemies within the tower's range of attack.
	 * @param list of all enemies on the map right now.
	 * */
	public ArrayList<Enemy> enemiesInRange(ArrayList<Enemy> allEnemies) {
		ArrayList<Enemy> enemiesWithinRange = new ArrayList<>();
		for (Enemy enemy: allEnemies) {
			//if the enemy is within the attackRange
			if (enemy.getRealXCoor() > Math.floor(this.x) - (attackRange*35) &&
				enemy.getRealXCoor() < Math.floor(this.x) + (attackRange*35 ) &&
				enemy.getRealYCoor() > Math.floor(this.y) - (attackRange*35 ) &&
				enemy.getRealYCoor() < Math.floor(this.y) + (attackRange*35 )) {
				enemiesWithinRange.add(enemy);
			}
		}
		return enemiesWithinRange;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public int getAttackRange() {
		return this.attackRange;
	}
	
	public int getDamageValue() {
		return this.damageValue;
	}
	
	public boolean isAttacking() {
		return this.isAttacking;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isNotUpgraded() {
		return !this.upgraded;
	}
	
	public int getUpgradeCost() {
		return this.upgradeCost;
	}
	
	public String toString() {
    	return this.name + " x: " + x + " y: " + y + "\nUpgrade: " + this.upgraded;
    }
	
	public int getCost() {
		return this.cost;
	}
	
	//Methods to change the speed of tower attacks depending
	//on the game's current play speed.
	public static void speedUp() {
		speedup = 2;
	}
	
	public static void slowDown() {
		speedup = 1;
	}
	
	public static void pause() {
		savedPauseSpeed = speedup;
		speedup = 0;
	}
	
	public static void unpause() {
		speedup = savedPauseSpeed;
	}
}
