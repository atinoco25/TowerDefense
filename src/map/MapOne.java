package map;

import java.io.Serializable;
import java.util.ArrayList;

import enemy.Enemy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import map.Map;
import map.Cordinates;
import map.Tile;
import map.TileFly;

public class MapOne extends Map implements Serializable {
	
	//The first map is a square base with a square path around it 
	public MapOne(){
		makeMap1();
		canvas = new Canvas(imageSize*mapSize, imageSize*mapSize);
		gc = canvas.getGraphicsContext2D();
		drawMap();
		Cordinates startCord = new Cordinates(7,0);
		Cordinates endCord = new Cordinates(7,7);
		start = new ArrayList<Cordinates>();
		end = new ArrayList<Cordinates>();
		start.add(startCord);
		end.add(endCord);
	}
	
	//Constructs the first map
	private void makeMap1() {
		map = new Tile[mapSize][mapSize];
		for(int row=0;row<mapSize;row++) {
			for(int col=0;col<mapSize;col++) {
				if((row<1 || row>14)&&(col>((8)) || col < (7))) {
					map[row][col] = new Tile("edge", false);
				}else if((col<1 || col>14)&&(row>((mapSize/2))||row<((mapSize/2)-1))) {
					map[row][col] = new Tile("edge", false);
				}else if((((row<3 && row>0)||(row<15 && row>12))&&(col>0 &&col<15))) {
					map[row][col] = new Tile("path", false);
				}else if((((col<3 && col>0)||(col<15 && col>12))&&(row>0 && row<15))) {
					map[row][col] = new Tile("path", false);
				}else if(row>2 && row<13 && col>2 && col<13) {
					map[row][col] = new Tile("base", true);
				}else if((row<1 || row>mapSize-2)&&(col<=((mapSize/2)) && col >= ((mapSize/2)-1))) {
					map[row][col] = new Tile("path", false);
				}else if((col<1 || col>mapSize-2)&&(row<=((mapSize/2))&&row>=((mapSize/2)-1))) {
					map[row][col] = new Tile("path", false);
				}
			}
		}
	}
	
	public void path(Enemy enem) {
		int enemSpeed = enem.getSpeed();
		if (enem.getBase16XCoor() == 7 && enem.getBase16YCoor() < 2) {
			enem.moveTo(enem.getRealXCoor(), enem.getRealYCoor() + enemSpeed);
		} else if ((enem.getBase16XCoor() <= 7 && enem.getBase16XCoor() >= 2) && enem.getBase16YCoor() == 2) {
			enem.moveTo(enem.getRealXCoor() - enemSpeed, enem.getRealYCoor());
		} else if (enem.getBase16XCoor() == 1 && (enem.getBase16YCoor() >= 2 && enem.getBase16YCoor() < 13)) {
			enem.moveTo(enem.getRealXCoor(), enem.getRealYCoor() + enemSpeed);
		} else if ((enem.getBase16XCoor() >= 1 && enem.getBase16XCoor() < 7) && enem.getBase16YCoor() == 13) {
			enem.moveTo(enem.getRealXCoor() + enemSpeed, enem.getRealYCoor());
		} else if (enem.getBase16XCoor() == 7 && (enem.getBase16YCoor() <= 13 && enem.getBase16YCoor() > 8)) {
			enem.moveTo(enem.getRealXCoor(), enem.getRealYCoor() - enemSpeed);
		} else if (enem.getBase16XCoor() == 7 && enem.getBase16YCoor() == 8) {

			bastHit(enem.getPower());
			enem.setKilledByBase();
			// kill the enemey
			enem.receiveDamage(1000);
		}
	}


}

	

