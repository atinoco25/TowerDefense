package test;

import org.junit.Test;
import enemy.Enemy;
import enemy.DevilEnemy;
import enemy.SoldierEnemy;
import enemy.TankEnemy;
import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import tower.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class Tests {

	//USED TO INITIALIZE GRAPHIC OBJECTS FOR TESTING
	final JFXPanel fxPanel = new JFXPanel();
	
	//TOWER TESTS
	@Test
	public void testTowerNames() {
		Tower waterTower = new WaterTower(0, 0);
		Tower fireTower = new FireTower(1, 1);
		Tower iceTower = new IceTower(2, 2);
		assertTrue("Water Tower".equals(waterTower.getName()));
		assertTrue("Fire Tower".equals(fireTower.getName()));
		assertTrue("Ice Tower".equals(iceTower.getName()));
	}

	@Test
	public void testSetName() {
		Tower waterTower = new WaterTower(0, 0);
		waterTower.setName("335 Defender");
		assertTrue("335 Defender".equals(waterTower.getName()));
	}
	
	@Test
	public void testSettingGraphicsContext() {
		Tower.setGraphicsContext(new Canvas().getGraphicsContext2D());
	}
	
	@Test
	public void testCosts() {
		Tower waterTower = new WaterTower(0, 0);
		Tower fireTower = new FireTower(1, 1);
		Tower iceTower = new IceTower(2, 2);
		assertEquals(waterTower.getCost(), 30);
		assertEquals(fireTower.getCost(), 60);
		assertEquals(iceTower.getCost(), 90);
	}
	
	@Test
	public void testStrings() {
		Tower waterTower = new WaterTower(0, 0);
		Tower fireTower = new FireTower(1, 1);
		Tower iceTower = new IceTower(2, 2);
		assertTrue("Water Tower x: 0 y: 0\nUpgrade: false".equals(waterTower.toString()));
		assertTrue("Fire Tower x: 1 y: 1\nUpgrade: false".equals(fireTower.toString()));
		assertTrue("Ice Tower x: 2 y: 2\nUpgrade: false".equals(iceTower.toString()));
	}
	
	@Test
	public void testRanges() {
		Tower waterTower = new WaterTower(0, 0);
		Tower fireTower = new FireTower(1, 1);
		Tower iceTower = new IceTower(2, 2);
		assertEquals(3, waterTower.getAttackRange());
		assertEquals(2, fireTower.getAttackRange());
		assertEquals(2, iceTower.getAttackRange());
	}
	
	@Test 
	public void testEnemiesInRange() {
		Tower waterTower = new WaterTower(100, 100);
		ArrayList<Enemy> enemies = new ArrayList<>();
		for(int i = 0; i < 200; i += 10) {
			enemies.add(new DevilEnemy(i, i));
		}
		enemies.add(new SoldierEnemy(100, 0));
		enemies.add(new TankEnemy(100, 200));
		assertEquals(22, waterTower.enemiesInRange(enemies).size());
		
	}
	
	@Test
	public void testWaterTowerUpgrade() {
		Tower waterTower = new WaterTower(100, 100);
		assertEquals(3, waterTower.getAttackRange());
		assertTrue("Water Tower".equals(waterTower.getName()));
		waterTower.upgrade();
		assertEquals(4, waterTower.getAttackRange());
		assertTrue("Upgraded Water Tower".equals(waterTower.getName()));
	}
	
	@Test
	public void testFireTowerUpgrade() {
		Tower fireTower = new FireTower(100, 100);
		assertEquals(5, fireTower.getDamageValue());
		assertTrue("Fire Tower".equals(fireTower.getName()));
		fireTower.upgrade();
		assertEquals(10, fireTower.getDamageValue());
		assertTrue("Upgraded Fire Tower".equals(fireTower.getName()));
	}
	
	@Test
	public void noWaterTowerAttackExceptions() {
		Tower waterTower = new WaterTower(100, 100);
		ArrayList<Enemy> enemies = new ArrayList<>();
		for(int i = 0; i < 200; i += 10) {
			enemies.add(new DevilEnemy(i, i));
		}
		enemies.add(new SoldierEnemy(100, 0));
		enemies.add(new TankEnemy(100, 200));
		waterTower.attack(enemies);
	}
	
	@Test
	public void waterTowerAttackNoEnemies() {
		Tower waterTower = new WaterTower(100, 100);
		ArrayList<Enemy> enemies = new ArrayList<>();
		waterTower.attack(enemies);
	}
	
	@Test
	public void enemyAttack() {
		//soldier attack is 45
		Enemy enemy = new SoldierEnemy(0,0);
		int health = 100;
		
		health -= enemy.attack();
		
		assertTrue(health == 55);
	} 
	
	@Test
	public void receiveDamageEnemy() {
			//soldier health is 80
			Enemy enemy = new SoldierEnemy(0,0);
			int toDamage = 50;
			
			enemy.receiveDamage(toDamage);
			
			assertTrue(enemy.getHealth() == 30);
			assertTrue(enemy.isDead() == false);
			
			//this should kill enemy
			enemy.receiveDamage(toDamage);
			assertTrue(enemy.isDead() == true);
	}
}
