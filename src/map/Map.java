package map;

import java.util.ArrayList;

import enemy.Enemy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Map  {
	final int mapSize = 16;
	Tile[][] map;
	ArrayList<Cordinates> start;
	ArrayList<Cordinates> end;
	int imageSize = 35;
	int baseHealth = 15;
	boolean gameLost = false;
	Canvas canvas;
	GraphicsContext gc;
	
	public Map() {
		
	}
	
	//Returns the to string of the map but
	//does not show enemies or towers placed
	public String toString() {
		String finalStr = "";
		for(int row=0;row<mapSize;row++) {
			for(int col=0;col<mapSize;col++) {
				if(map[row][col] != null) {
					finalStr += map[row][col].getName();
				}else {
					finalStr += "NULL";
				}
				finalStr += " ";
				
			}
			finalStr += "\n";
		}
		return finalStr;
	}
	
	abstract public void path(Enemy enem);
	
	//Draws the map but not the enemies or towers
	public void drawMap() {
		// TODO Auto-generated method stub
		int xPos = 0;
		int yPos = 0;
		for(int row=0;row<mapSize;row++) {
			for(int col=0;col<mapSize;col++) {
				gc.drawImage(TileFly.getFlyweight(map[row][col].getName()).getImage(), xPos, yPos);
				xPos += imageSize;
				
			}
			xPos = 0;
			yPos += imageSize;
		}
		
	}
	
	//checks to see if the tile is buildable
	public boolean canBuild(int x, int y) {
		// TODO Auto-generated method stub
		if(x>=0 && x<16 && y>=0 && y<16) {
			return map[x][y].getBuildable();
		}
		return false;
	}
	
	//hits the base decrements the health by one 
	public void bastHit(int power) {
		baseHealth -= power;
		if(baseHealth <= 0) {
			baseHealth = 0;
			gameLost = true;
		}
		
	}
	
	
	/*
	 * Getter methods
	 */
	public Tile[][] getMap() {
		return this.map;
	}
	
	public int getImageSize() {
		return this.imageSize;
	}
	
	public ArrayList<Cordinates> getStartCords() {
		
		return start;
	}
	
	public ArrayList<Cordinates> getEndCords() {
		
		return end;
	}
	
	public Canvas getCanvas() {
		
		return canvas;
	}
	
	public boolean getGameLost() {
		return gameLost;
	}
	
	public int getBaseHealth() {
		return baseHealth;
	}
	
	/*
	 * Setter methods
	 */
	public void setStartCords(ArrayList<Cordinates> startList) {
		start = startList;
		
	}
	
	public void setEndCords(ArrayList<Cordinates> endList) {
		end = endList;
		
	}
}
	
	
	
	
	