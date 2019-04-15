package tower;

import java.time.LocalTime;
import java.util.ArrayList;
import static java.time.temporal.ChronoUnit.MILLIS;
import enemy.Enemy;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * The Water Tower (when built) will unleash a deluge of water every so often and damage
 * any enemies within the range of the splashdown.
 * 
 *  Upgrade: increase range of splashdown (but keeps the same power)
 *
 * @author Carl Wernicke
 * */
public class WaterTower extends Tower{

	WaterDeluge waterDeluge;
	ArrayList<Enemy> targetEnemies;
	
	public WaterTower(int x, int y) {
		super("file:src/tower/TowerImages/WaterTower.png" , x, y);
		this.name = "Water Tower";
		this.projectileType = "Water";
		this.attackRange = 3;
		this.damageValue = 10;
		this.cost = 30;
		this.upgradeCost = 50;
		this.description = "Range: " + this.getAttackRange() + "\n"
                		 + "Damage: " + this.getDamageValue() + " \n"
                		 + "Type: " + this.projectileType;

	}

	@Override
	public void upgrade() {
		this.upgraded = true;
		//change tower image
		//water tower upgrade will expand the range that the deluge of water splashdown.
		attackRange += 1;
		this.setImage(new Image("file:src/tower/TowerImages/WaterTowerUpgrade.png"));
		this.setName("Upgraded Water Tower");
		this.description = "Range: " + this.getAttackRange() + "\n"
                		 + "Damage: " + this.getDamageValue() + " \n"
                		 + "Type: " + this.projectileType;
	}

	@Override
	public void attack(ArrayList<Enemy> enemyList) {
		//if there are enemies in range
		if (this.enemiesInRange(enemyList).size() > 0 && !this.isAttacking) {
			waterDeluge = new WaterDeluge(this.x, this.y, this.attackRange);
			//enemies for the water Tower will be damaged once the deluge reaches
			//full range
			targetEnemies = enemyList;
			this.isAttacking = true;
		}		
	}

	//see Tower class
	@Override
	public void drawAttack() {
		//don't attack if the game is paused
		if (speedup == 0) 
			return;
		this.waterDeluge.updateCoordsAndDraw();
		if (this.waterDeluge.delugeDone) {
			this.isAttacking = false;
		}
	}
	/**
	 * Inner class for the attacking animation
	 * */
	private class WaterDeluge {
		private int x;
		private int y;
		private double radius;
		private LocalTime startTime;
		private double timeDiff;
		private int range;
		private Image water;
		final static int timeOfAttack = 5000; //5 seconds
		
		public boolean delugeDone;
		private boolean damageDone;
		
		public WaterDeluge(int x, int y, int range) {
			this.x = x + coordOffset;
			this.y = y + coordOffset;
			this.range = range * 35;
			radius = 0;
			delugeDone = false;
			timeDiff = 0;
			startTime = LocalTime.now();
			damageDone = false;
			
			//water animation is a gif which spreads out
			water = new Image("file:src/tower/TowerImages/water.gif", false);
		}
		
		/**
		 * Update the animation based on how long it's been since the attack started
		 * */
		public void updateCoordsAndDraw() {
			timeDiff = MILLIS.between(startTime, LocalTime.now());
			//if the water hasn't reached its maximum range yet
			if (radius < range) {
				radius = (timeDiff/500) * 35 * speedup;
				if (radius >= range) {
					radius = range;
					if (!damageDone) {
						//get all enemies within range of the water right now
						targetEnemies = WaterTower.this.enemiesInRange(targetEnemies);
						for (Enemy enemy: targetEnemies) {
							enemy.receiveDamage(damageValue);
						}
					}
				}
			}
			gc.setGlobalAlpha(0.35);
			gc.drawImage(water, this.x - radius, this.y - radius, radius * 2, radius * 2);
			gc.setGlobalAlpha(1.0);
			
			//check to see if the attack is done
			if (timeDiff > timeOfAttack / speedup) {
				delugeDone = true;
			}
		}
	}
}
