package tower;

import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.LocalTime;
import java.util.ArrayList;

import enemy.Enemy;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * The Ice Tower will occasionally freeze enemies within a certain range for 
 * a certain amount of seconds.
 * 
 * Upgrade: enemies will be frozen for twice as long
 *
 * @author Carl Wernicke
 * */
public class IceTower extends Tower {

	//amount of time the enemies stay frozen for
	int freezeTime = 4;
	FreezeAttack freezeAttack;
	
	public IceTower(int x, int y) {
		super("file:src/tower/TowerImages/IceTower.png", x, y);
		this.name = "Ice Tower";
		this.projectileType = "Ice";
		this.attackRange = 2;
		this.damageValue = 10;
		this.cost = 90;
		this.upgradeCost = 50;
		this.description = "Range: " + this.getAttackRange() + "\n"
                		 + "Damage: " + this.getDamageValue() + " \n"
                		 + "Type: " + this.projectileType;

	}

	//see Tower class
	@Override
	public void upgrade() {
		//double the amount of time enemies are frozen for
		this.upgraded = true;
		freezeTime = 8;
		this.setImage(new Image("file:src/tower/TowerImages/IceTowerUpgrade.png"));
		this.description = "Range: " + this.getAttackRange() + "\n"
						 + "Damage: " + this.getDamageValue() + " \n"
						 + "Type: " + this.projectileType;
	}

	//see Tower class
	@Override
	public void attack(ArrayList<Enemy> enemyList) {
		//if there are enemies in range
		enemyList = this.enemiesInRange(enemyList);
		if (enemyList.size() > 0 && !this.isAttacking) {
			freezeAttack = new FreezeAttack(this.x, this.y, this.attackRange, this.freezeTime);
			//ice attacks damage the enemy immediately
			for (Enemy enemy: enemyList) {
				enemy.receiveDamage(damageValue);
				enemy.freeze(freezeTime*4);
			}
			this.isAttacking = true;
		}	
		
	}

	//see Tower class
	@Override
	public void drawAttack() {
		if (speedup == 0) 
			return;
		this.freezeAttack.updateCoordsAndDraw();
		if (this.freezeAttack.freezeDone) {
			this.isAttacking = false;
		}
	}
	
	/**
	 * freeze animation for the ice tower attack
	 * */
	private class FreezeAttack {
		private int x;
		private int y;
		private LocalTime startTime;
		private double timeDiff;
		private int range;
		private int freezeTime;
		private Image texture;
		
		public boolean freezeDone;
		
		public FreezeAttack(int x, int y, int range, int freezeTime) {
			texture = new Image("file:src/tower/TowerImages/IceTexture.png", false);
			this.x = x + coordOffset;
			this.y = y + coordOffset;
			this.range = range * 35;
			this.freezeTime = freezeTime * 1000;
			freezeDone = false;
			timeDiff = 0;
			startTime = LocalTime.now();
		}
		/**
		 * Update the animation based on how long it's been since the attack started
		 * */
		public void updateCoordsAndDraw() {
			
			timeDiff = MILLIS.between(startTime, LocalTime.now());
			if (timeDiff <= freezeTime/speedup) {
				gc.setGlobalAlpha(0.5);
				gc.drawImage(texture, this.x - range, this.y - range, range * 2, range * 2);
				gc.setGlobalAlpha(1.0);
			}
			
			if (timeDiff > freezeTime * 1.5/speedup) {
				freezeDone = true;
			}
		}
	}
}
