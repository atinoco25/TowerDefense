package map;

import java.io.Serializable;
import java.util.ArrayList;

import enemy.Enemy;
import javafx.scene.canvas.Canvas;

public class mapTwo extends Map implements Serializable {
	public mapTwo() {
		makeMap2();
		canvas = new Canvas(imageSize*mapSize, imageSize*mapSize);
		gc = canvas.getGraphicsContext2D();
		drawMap();
		Cordinates startCord = new Cordinates(0,7);
		Cordinates endCord = new Cordinates(8,6);
		start = new ArrayList<Cordinates>();
		end = new ArrayList<Cordinates>();
		start.add(startCord);
		end.add(endCord);
	}

	private void makeMap2() {
		map = new Tile[mapSize][mapSize];
		for(int row=0;row<mapSize;row++) {
			for(int col=0;col<mapSize;col++) {
				map[row][col] = new Tile("base", true);
				if((row>8)&&(col<4)) {
					map[row][col]= new Tile("edge", false);
				}
				if((row>14 || row<1)||(col<1 || col>14)) {
					map[row][col]= new Tile("edge", false);
				}
				if((col>1 &&col<14) && (row == 2 || row ==3)) {
					map[row][col] = new Tile("path", false);
				}
				if((row ==7 || row ==8) && col<4) {
					map[row][col] = new Tile("path", false);
				}
				if((col == 2 || col == 3)&&(row>1 && row<9)) {
					map[row][col] = new Tile("path", false);
				}
				if((col == 13 || col == 12)&&(row>1 && row<14)) {
					map[row][col] = new Tile("path", false);
				}
				if((col == 6 || col ==7)&&(row>5 && row<14)) {
					map[row][col] = new Tile("path", false);
				}
				if((row == 13 || row ==12)&&(col > 5 && col<14)) {
					map[row][col] = new Tile("path", false);
				}
				if((row == 6 || row ==7)&&(col == 8||col ==9)) {
					map[row][col] = new Tile("sbse", false);
				}
			}
		}
		
	}
	@Override
	public void path(Enemy enem) {
		int enemSpeed = enem.getSpeed();
		if (enem.getBase16XCoor() <3 && enem.getBase16YCoor() == 7) {
			enem.moveTo(enem.getRealXCoor() + enemSpeed,enem.getRealYCoor());//->
			
		} else if ((enem.getBase16YCoor() < 9 && enem.getBase16YCoor() > 2) && enem.getBase16XCoor() == 3) {
			enem.moveTo(enem.getRealXCoor() , enem.getRealYCoor() - enemSpeed);// ^
																			   // |
		} else if (enem.getBase16YCoor() == 2 && (enem.getBase16XCoor() >= 2 && enem.getBase16XCoor() < 12)) {
			enem.moveTo(enem.getRealXCoor() + enemSpeed , enem.getRealYCoor() );//->
			
		} else if ((enem.getBase16YCoor() >= 1 && enem.getBase16YCoor() < 12) && enem.getBase16XCoor() == 12) {
			enem.moveTo(enem.getRealXCoor() , enem.getRealYCoor()+ enemSpeed);// |
																			  // V
			
		} else if (enem.getBase16YCoor() == 12 && (enem.getBase16XCoor() <= 13 && enem.getBase16XCoor() > 6)) {
			enem.moveTo(enem.getRealXCoor()- enemSpeed, enem.getRealYCoor() );//<-
		
		}else if (enem.getBase16XCoor() == 6 && (enem.getBase16YCoor() <= 13 && enem.getBase16YCoor() > 6 )) {
			enem.moveTo(enem.getRealXCoor() , enem.getRealYCoor() - enemSpeed);
		
		}else if(enem.getBase16YCoor() == 6 && enem.getBase16XCoor() > 5 && enem.getBase16XCoor() <8) {
			enem.moveTo(enem.getRealXCoor() + enemSpeed , enem.getRealYCoor() );
			
		} else if (enem.getBase16XCoor() == this.end.get(0).getXCordinates() && enem.getBase16YCoor() == end.get(0).getYCordinates()) {

			bastHit(enem.getPower());
			enem.setKilledByBase();
			// kill the enemy
			enem.receiveDamage(1000);
			enem.moveTo(0, 0);
		}
	}
}
