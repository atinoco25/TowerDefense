package tower;

import enemy.Enemy;
import java.time.LocalTime;
import java.util.ArrayList;
import static java.time.temporal.ChronoUnit.MILLIS;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * The Fire Tower (when built) will throw out balls of fire to attack enemies.
 * a single ball will hit an enemy and then dissipate. 
 * 
 *  Upgrade: increase the strength of fire balls that get shot out
 *
 * @author Carl Wernicke
 * */

public class FireTower extends Tower {
	
	private FireProjectile fireProjectile;
	private Enemy targetEnemy;
	
	public FireTower(int x, int y) {
		super("file:src/tower/TowerImages/FireTower.png", x, y);
		this.name = "Fire Tower";
		this.projectileType = "Fire";
		this.attackRange = 2;
		this.damageValue = 5;
		this.cost = 60;
		this.upgradeCost = 50;
		this.description = "Range: " + this.getAttackRange() + "\n"
                		 + "Damage: " + this.getDamageValue() + " \n"
                		 + "Type: " + this.projectileType;

	}
	
	@Override
	public void upgrade() {
		//double the damage value of a fireball
		this.upgraded = true;
		this.damageValue = 10;
		this.setImage(new Image("file:src/tower/TowerImages/FireTowerUpgrade.png"));
		this.setName("Upgraded Fire Tower");
		this.description = "Range: " + this.getAttackRange() + "\n"
                		 + "Damage: " + this.getDamageValue() + " \n"
                		 + "Type: " + this.projectileType;
		
	}

	@Override
	public void attack(ArrayList<Enemy> enemyList) {
		//idea is to create an animation where the fire attack is shot at
		//the first enemy in the list then disappears once it makes contact
		//with and damages the enemy. 
		enemyList = this.enemiesInRange(enemyList);
		//if there are enemies in range
		if (enemyList.size() > 0) {
			
			for (int i = 0; i < enemyList.size(); i++) {
				targetEnemy = enemyList.get(i);
				if (targetEnemy.isDead()) {
					if (i == enemyList.size() - 1) 
						return;
					continue;
				}
				
			}
			int enemyX = targetEnemy.getRealXCoor();
			int enemyY = targetEnemy.getRealYCoor();
			fireProjectile = new FireProjectile(enemyX, enemyY, this.x, this.y);
			this.isAttacking = true;
			
			
		}
		
	}
	
	/**
	 * Method to detect collision between an enemy and a projectile.
	 * @param The projectile's coordinates and the enemy's coordinates.
	 * */
	private boolean projectileHitEnemy(double projX, double projY, int enemyX, int enemyY) {	
		return projX < enemyX + 15 && 
			   projX > enemyX - 15 &&
			   projY < enemyY + 15 &&
			   projY > enemyY - 15;		
	}

	//see Tower class
	@Override
	public void drawAttack() {
		if (speedup == 0) 
			return;
		this.fireProjectile.updateCoordsAndDraw();
		if (this.fireProjectile.hitEnemy) {
			this.isAttacking = false;
			this.targetEnemy.receiveDamage(this.damageValue);
		}
		
		
	}
	/**
	 * The fireball attack animation used by this tower
	 * */
	private class FireProjectile {
		private int enemyX;
		private int enemyY;
		private double x;
		private double y;
		private int startX;
		private int startY;
		private Image image;
		private LocalTime startTime;
		private double timeDiff;
		final static int speed = 50;
		
		private double opposite;
		private double adjacent;
		private double hypotenuse;
		
		public boolean hitEnemy;
		public boolean missedEnemy;
		
		public FireProjectile(int enemyX, int enemyY, int x, int y) {
			this.enemyX = enemyX;
			this.enemyY = enemyY;
			this.x = x + coordOffset;
			this.y = y + coordOffset;
			this.startX = x + coordOffset;
			this.startY = y + coordOffset;
			image = new Image("file:src/tower/TowerImages/FireProjectile.png", false);
			startTime = LocalTime.now();
			timeDiff = 0;
			hitEnemy = false;
			missedEnemy = false;
			opposite = enemyY - startY;
			adjacent = enemyX - startX;
			hypotenuse = Math.sqrt(opposite * opposite + adjacent * adjacent);
		}
		
		/**
		 * Update the animation based on how long it's been since the attack started
		 * */
		public void updateCoordsAndDraw() {
			timeDiff = MILLIS.between(startTime, LocalTime.now());
			this.x = startX + speed * (timeDiff/500) * (adjacent/hypotenuse) * speedup;
			this.y = startY + speed * (timeDiff/500) * (opposite/hypotenuse) * speedup;
			gc.drawImage(image, x, y);
			
			//if the fireball hits or misses the enemy, stop the attack
			if (Math.abs(this.x - startX) > adjacent + 15 ||
			    Math.abs(this.y - startY) > opposite + 15) {
				missedEnemy = true;
			}
			
			if (projectileHitEnemy(x, y, enemyX, enemyY)) {
				hitEnemy = true;
			}
		}
	}
	
}
