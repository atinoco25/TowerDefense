package mainModel;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import enemy.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import map.Cordinates;
import map.Map;
import map.MapOne;
import map.Tile;
import map.mapThree;
import map.mapTwo;
import tower.FireTower;
import tower.IceTower;
import tower.Tower;
import tower.WaterTower;
import mainModel.Inventory;

/**
 * This class contains the necessary methods for the tower defense game
 */
public class MainGame {

	private Map map;
	private Inventory inventory;
	ArrayList<Enemy> enemies;
	HashMap<Cordinates, Tower> towers;
	Vector<Cordinates> towerCords;
	ListIterator<Cordinates> iter;
	private int waves;
	private boolean waveInProgress;
	int enemiesMoving = 1;
	private int enemiesInWave = 10;
	private final int tileSize = 35;
	private int tic = 0;
	private int spacingBetweenEnemies = 10;	

	/**
	 * Constructor will call a method to initialize all instance variables
	 */
	public MainGame() {
		initializeGame();
	}

	/**
	 * Determines if the location selected is an available space to build a
	 * Tower
	 * 
	 * @param x
	 * @param y
	 * @return true if available
	 */
	public boolean canBuild(int x, int y) {
		return map.canBuild(x, y);
	}

	// This will set up a new game
	private void initializeGame() {
		inventory = new Inventory();
		enemies = new ArrayList<Enemy>();
		towers = new HashMap<Cordinates, Tower>();
		towerCords = new Vector<Cordinates>();
		iter = towerCords.listIterator();
		waves = 1;

	}
	
	/**
	 * This method will decided what map to draw given the desired map
	 */
	public void setMap(int mapNum) {
		if(mapNum == 1) {
			map = new MapOne();
		}else if(mapNum == 2) {
			map = new mapTwo();
		}else if(mapNum == 3) {
			map = new mapThree();
		}else {
			map = new MapOne();
		}
		
	}

	/**
	 * Start a new game and tell observers to draw a new game with the string
	 * message startNewGame()
	 */
	public void startNewGame() {
		initializeGame();
		// The state of this model just changed so tell any observer to update
		// themselves
	}

	// Sets up a list to hold the enemies in the wave
	private void fillEnemy() {
		if(map.getClass().equals(MapOne.class)) {
			fillOriginal();
		}
		else if(map.getClass().equals(mapTwo.class)) {
			fillAnimals();
		}
		else {
			fillRickAndMortys();
		}
	}
	private void fillAnimals() {
		if(map.getStartCords().size()==1) {
			int startX = map.getStartCords().get(0).getXCordinates();
			int startY = map.getStartCords().get(0).getYCordinates();
			for (int i = 0; i < enemiesInWave; i++) {
				if(waves==3)
					enemies.add(new NinjaEnemy(startX*tileSize, startY*tileSize));
				else if(waves==2)
					enemies.add(new WizardMortyEnemy(startX*tileSize, startY*tileSize));
				else if(waves==1)
					enemies.add(new PunkMortyEnemy(startX*tileSize, startY*tileSize));
				else if(waves>3) {
					//Win CONDITION
				}
			}
		}else if(map.getStartCords().size()>1) {
			int startX = 0;
			int startY = 0;
			int counter = 0;
			for(int i=0;i<enemiesInWave;i++) {
				if(counter == map.getStartCords().size())
					counter = 0;
				startX = map.getStartCords().get(counter).getXCordinates();
				startY = map.getStartCords().get(counter).getYCordinates();
				if(waves==1) {
					enemies.add(new PunkMortyEnemy(startX*tileSize, startY*tileSize));
				}if(waves==2) {
					enemies.add(new WizardMortyEnemy(startX*tileSize,startY*tileSize));
				}if(waves==3) {
					enemies.add(new NinjaEnemy(startX*tileSize,startY*tileSize));
				}if(waves>3) {
					//WIN CONDITION
				}
				
				counter++;
			}
		}
	}

	/**
	 * This method will fill the map with the three original enemies
	 */
	private void fillOriginal() {
		if(map.getStartCords().size()==1) {
			int startX = map.getStartCords().get(0).getXCordinates();
			int startY = map.getStartCords().get(0).getYCordinates();
			for (int i = 0; i < enemiesInWave; i++) {
				if(waves==3)
					enemies.add(new DevilEnemy(startX*tileSize, startY*tileSize));
				else if(waves==2)
					enemies.add(new TankEnemy(startX*tileSize, startY*tileSize));
				else if(waves==1)
					enemies.add(new SoldierEnemy(startX*tileSize, startY*tileSize));
				else if(waves>3) {
					//Win CONDITION
				}
			}
		}else if(map.getStartCords().size()>1) {
			int startX = 0;
			int startY = 0;
			int counter = 0;
			for(int i=0;i<enemiesInWave;i++) {
				if(counter == map.getStartCords().size())
					counter = 0;
				startX = map.getStartCords().get(counter).getXCordinates();
				startY = map.getStartCords().get(counter).getYCordinates();
				if(waves==1) {
					enemies.add(new SoldierEnemy(startX*tileSize, startY*tileSize));
				}if(waves==2) {
					enemies.add(new TankEnemy(startX*tileSize,startY*tileSize));
				}if(waves==3) {
					enemies.add(new DevilEnemy(startX*tileSize,startY*tileSize));
				}if(waves>3) {
					//WIN CONDITION
				}
				
				counter++;
			}
		}
	}

	/**
	 * This method will fill the map with he characters of rick and morty
	 */
	private void fillRickAndMortys() {
		if(map.getStartCords().size()==1) {
			int startX = map.getStartCords().get(0).getXCordinates();
			int startY = map.getStartCords().get(0).getYCordinates();
			for (int i = 0; i < enemiesInWave; i++) {
				if(waves==3)
					enemies.add(new PickleRickEnemy(startX*tileSize, startY*tileSize));
				else if(waves==2)
					enemies.add(new RickEnemy(startX*tileSize, startY*tileSize));
				else if(waves==1)
					enemies.add(new MortyEnemy(startX*tileSize, startY*tileSize));
				else if(waves>3) {
					//Win CONDITION
				}
			}
		}else if(map.getStartCords().size()>1) {
			int startX = 0;
			int startY = 0;
			int counter = 0;
			for(int i=0;i<enemiesInWave;i++) {
				if(counter == map.getStartCords().size())
					counter = 0;
				startX = map.getStartCords().get(counter).getXCordinates();
				startY = map.getStartCords().get(counter).getYCordinates();
				if(waves==1) {
					enemies.add(new MortyEnemy(startX*tileSize, startY*tileSize));
				}if(waves==2) {
					enemies.add(new RickEnemy(startX*tileSize,startY*tileSize));
				}if(waves==3) {
					enemies.add(new PickleRickEnemy(startX*tileSize,startY*tileSize));
				}if(waves>3) {
					//WIN CONDITION
				}
				
				counter++;
			}
		}
	}

	// Returns the toString of the game
	public String toString() {
		return map.toString();
	}

	// Returns the current state of the map
	public Map getGameMap() {
		return this.map;
	}

	// Returns the current inventory
	public Inventory getInventory() {
		return this.inventory;
	}

	// This will start to produce 10 enemies that follows a path
	public void startWave() {

		waveInProgress = true;
		fillEnemy();
		enemiesMoving = 1;
		// System.out.println("Enemies in list");

	}
	
	/**
	 * This method will move all enemies to their next location
	 */
	public void moveEnemies() {
		// while(!enemies.isEmpty()) {
		for (int i = 0; i < enemiesMoving; i++) {
			if(enemies.size() <= 0)
				return;
			
			//if (enemies.get(i).isDead() == false)
				hardcodedPath(enemies.get(i));
		}
		if (enemiesMoving < enemiesInWave && tic == spacingBetweenEnemies) {
			enemiesMoving++;
			tic = 0;
		}
		else {
			tic++;
		}

		// }

	}

	/**
	 * This method determines if there is a current wave in progress, as well as checking if a new wave needs to appear
	 */
	public boolean getWaveInProg() {
		removeDeadEnemies();
		//if there are no more waves
		if(waves >3) {
			return waveInProgress;
		}
		//create the next wave
		else if(waves<3 && enemiesInWave == 0){
			tic =0;
			System.out.println("NEW WAVE");
			waves++;
			enemiesInWave = 10;
			fillEnemy();
			waveInProgress = true;
			return waveInProgress;
		}
		//if a current wave in progress
		else if(waves<=3 && waveInProgress == true) {
			return waveInProgress;
		}
		else {
			if(map.getGameLost() == true) {
				System.out.println("GAME LOST");
			}
			else {
				System.out.println("GAME WON");
			}
			    
			
			waveInProgress = false;
			return waveInProgress;
		}
	}

	/**
	 * This method will check if the current wave is over
	 */
	public void checkWaveOver() {
		boolean allDead = true;
		for (Enemy enem : enemies) {
			// System.out.println(enem.getHealth());
			if (enem.getBase16XCoor() == map.getEndCords().get(0).getXCordinates() && enem.getBase16YCoor() == map.getEndCords().get(0).getYCordinates()) {
				enem.receiveDamage(1000);
			}
			if (!enem.isDead()) {
				allDead = false;
			} else {

			}
		}
		if (allDead) {
			waveInProgress = false;
		}

	}

	// this function goes through all the towers and makes them attack
	public void towersAttack() {
		iter = towerCords.listIterator();
		if (iter == null) {
			return;
		}
		// System.out.println(iter.hasNext());
		while (iter.hasNext()) {
			Cordinates cord = iter.next();
			Tower curTower = towers.get(cord);
			// System.out.println(curTower.toString());
			if (!curTower.isAttacking()) {
				curTower.attack(enemies);
			}
		}
	}

	// this is a hardcoded path that the enemy will take
	private void hardcodedPath(Enemy enem) {
		map.path(enem);
	}

	// Draws the enemies on the map
	public Canvas drawEnemies(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for (int i = enemies.size() - 1; i >= 0; i--) {
			enemies.get(i).drawCurrentLocation(tileSize, canvas);
		}
		return gc.getCanvas();
	}

	/**
	 * This method will draw all the towers into a given canvas
	 */
	public void drawTowers(Canvas canvas) {

		for (Cordinates location : towerCords) {
			Tower.setGraphicsContext(canvas.getGraphicsContext2D());
			towers.get(location).drawTower();
		}

		// draw attacks afterwards so towers don't overlap the projectiles
		for (Cordinates location : towerCords) {
			if (towers.get(location).isAttacking()
					&& (towers.get(location) instanceof WaterTower || towers.get(location) instanceof IceTower)) {
				towers.get(location).drawAttack();
			}
		}

		// draw fire projectiles over water deluges and ice attacks
		for (Cordinates location : towerCords) {
			if (towers.get(location).isAttacking() && towers.get(location) instanceof FireTower) {
				towers.get(location).drawAttack();
			}
		}
	}

	// Place a Fire Tower on the map at given x,y locations and draw on the
	// canvas provided
	public void createFireTower(Canvas canvas, double startingX, double startingY) {
		// First check if we can place a Tower at the given location
		Cordinates location = new Cordinates((int) startingX, (int) startingY);
		for (Cordinates cord : towerCords) {
			if ((location.getXCordinates() == cord.getXCordinates()
					&& location.getYCordinates() == cord.getYCordinates())) {
				System.out.println("Can't build on another tower");
				return;
			}
		}
		FireTower newTower = new FireTower((int) startingX, (int) startingY);
		FireTower.setGraphicsContext(canvas.getGraphicsContext2D());
		newTower.drawTower();
		towers.put(location, newTower);
		iter.add(location);
		inventory.subtractGold(newTower.getCost());
	}

	// Place a Water Tower on the map at given x,y locations and draw on the
	// canvas provided
	public void createWaterTower(Canvas canvas, double startingX, double startingY) {
		Cordinates location = new Cordinates((int) startingX, (int) startingY);
		for (Cordinates cord : towerCords) {
			if ((location.getXCordinates() == cord.getXCordinates()
					&& location.getYCordinates() == cord.getYCordinates())) {
				System.out.println("Can't build on another tower");
				return;
			}
		}

		WaterTower newTower = new WaterTower((int) startingX, (int) startingY);
		WaterTower.setGraphicsContext(canvas.getGraphicsContext2D());
		newTower.drawTower();
		towers.put(location, newTower);
		iter.add(location);
		inventory.subtractGold(newTower.getCost());
	}

	// Place an Ice Tower on the map at given x,y locations and draw on the
	// canvas provided
	public void createIceTower(Canvas canvas, double startingX, double startingY) {
		Cordinates location = new Cordinates((int) startingX, (int) startingY);
		for (Cordinates cord : towerCords) {
			if ((location.getXCordinates() == cord.getXCordinates()
					&& location.getYCordinates() == cord.getYCordinates())) {
				System.out.println("Can't build on another tower");
				return;
			}
		}
		IceTower newTower = new IceTower((int) startingX, (int) startingY);
		IceTower.setGraphicsContext(canvas.getGraphicsContext2D());
		newTower.drawTower();
		towers.put(location, newTower);
		iter.add(location);
		inventory.subtractGold(newTower.getCost());
	}

	public void drawGhostFireTower(Canvas canvas, double startingX, double startingY) {
		// First check if we can place a Tower at the given location
		
		Cordinates location = new Cordinates((int) startingX, (int) startingY);
		for (Cordinates cord : towerCords) {
			if ((location.getXCordinates() == cord.getXCordinates()
					&& location.getYCordinates() == cord.getYCordinates())) {
				return;
			}
		}
		FireTower newTower = new FireTower((int) startingX, (int) startingY);
		FireTower.setGraphicsContext(canvas.getGraphicsContext2D());
		newTower.drawTower();
		newTower.displayRange();
	}
	
	public void drawGhostWaterTower(Canvas canvas, double startingX, double startingY) {
		// First check if we can place a Tower at the given location
		
		Cordinates location = new Cordinates((int) startingX, (int) startingY);
		for (Cordinates cord : towerCords) {
			if ((location.getXCordinates() == cord.getXCordinates()
					&& location.getYCordinates() == cord.getYCordinates())) {
				return;
			}
		}
		WaterTower newTower = new WaterTower((int) startingX, (int) startingY);
		WaterTower.setGraphicsContext(canvas.getGraphicsContext2D());
		newTower.drawTower();
		newTower.displayRange();
	}
	
	public void drawGhostIceTower(Canvas canvas, double startingX, double startingY) {
		// First check if we can place a Tower at the given location
		
		Cordinates location = new Cordinates((int) startingX, (int) startingY);
		for (Cordinates cord : towerCords) {
			if ((location.getXCordinates() == cord.getXCordinates()
					&& location.getYCordinates() == cord.getYCordinates())) {
				return;
			}
		}
		IceTower newTower = new IceTower((int) startingX, (int) startingY);
		IceTower.setGraphicsContext(canvas.getGraphicsContext2D());
		newTower.drawTower();
		newTower.displayRange();
	}

	public Tower getTowerSelection(int x, int y) {
		for (Cordinates cord : towerCords) {
			if (x == cord.getXCordinates() && y == cord.getYCordinates()) {
				return towers.get(cord);
			}
		}
		return null;
	}

	/*
	 * This method will remove all enemies who are dead from our enemy list
	 */
	public int removeDeadEnemies() {
		int i=enemiesMoving-1;
		int enemiesKilledByUser = 0;
		while(i>=0) {
			if(enemies.get(i).isDead() == true) {
				enemiesMoving--;
				enemiesInWave--;
				
				if(enemies.get(i).wasKilledByBase() == false)
					enemiesKilledByUser++;
				
				enemies.remove(i);
			}
			
			
			i--;
		}
		
		return enemiesKilledByUser;
	}

	/*
	 * This method will return a nearby enemy from the coordinates given
	 */
	public Enemy returnSelectedEnemy(double eventX, double eventY) {
		for(int i = 0; i <= enemies.size()-1;i++) {
			Enemy currentEnemy = enemies.get(i);
			if(currentEnemy.getRealXCoor()+tileSize > eventX && currentEnemy.getRealXCoor() <= eventX &&
				currentEnemy.getRealYCoor()+tileSize > eventY && currentEnemy.getRealYCoor()-tileSize < eventY) {
				 	unselectEnemies();
					currentEnemy.setSelected();
					return currentEnemy;
			}
		}
		
		return null;
	}
	
	/*
	 * This method will unselect all enemies, so that their normal sprite is drawn
	 */
	public void unselectEnemies() {
		for(int i =0; i < enemies.size() ;i++) {
			enemies.get(i).unSelect();
		}
	}
	
}
