package map;

import java.util.ArrayList;

import enemy.Enemy;
import javafx.scene.canvas.Canvas;

public class mapThree extends Map {
	
	public mapThree() {
		makeMap3();
		canvas = new Canvas(imageSize*mapSize, imageSize*mapSize);
		gc = canvas.getGraphicsContext2D();
		drawMap();
		Cordinates startCord = new Cordinates(0,7);
		Cordinates startCord2 = new Cordinates(0,8);
		Cordinates endCord = new Cordinates(7,7);
		Cordinates endCord2 = new Cordinates(7,8);
		start = new ArrayList<Cordinates>();
		end = new ArrayList<Cordinates>();
		start.add(startCord);
		start.add(startCord2);
		end.add(endCord);
		end.add(endCord2);
	}

	private void makeMap3() {
		map = new Tile[mapSize][mapSize];
		for(int row=0;row<mapSize;row++) {
			for(int col=0;col<mapSize;col++) {
				map[row][col] = new Tile("base", true);
				if((row<1)||(col>14)||(row>14)||(col<1)) {
					map[row][col]= new Tile("edge", false);
				}
				if(((row == 1||row==14) && (col<4 || col>12)) || ((row ==2||row==13) &&(col<3 ||col>13)) || ((row ==3||row==12) &&(col<2))) {
					map[row][col]= new Tile("edge", false);
				}
				if(((row>6&&row<9)&&(col<4||(col>7&&col<14))) 
						|| ((row==6||row==9)&&((col>1&&col<5)||(col>11&&col<14)))
						|| ((row==5||row==10)&&((col>1&&col<7)||(col>10&&col<14)))
						|| ((row==4||row==11)&&(col>3&&col<14))
						|| ((row==3||row==12)&&(col>5&&col<12))) {
					map[row][col]= new Tile("path", false);
				}
				if((row==7||row==8)&&(col==6||col==7)) {
					map[row][col]= new Tile("sbse", false);
				}
				
			}
		
		}
	}

	@Override
	public void path(Enemy enem) {
		// TODO Auto-generated method stub
		int row = enem.getBase16YCoor();
		int col = enem.getBase16XCoor();
		int x = enem.getRealXCoor();
		int y = enem.getRealYCoor();
		int speed = enem.getSpeed();
		if((row==7||row==8)&&(col<3)) {
			enem.moveTo(x+speed, y);
		}
		else if(row>=8 && row<10 && col==3) {
			enem.moveTo(x, y+speed);
		}
		else if(row<=7 && row>4 && col==3) {
			enem.moveTo(x, y-speed);
		}
		else if((row==4||row==10)&&col<6) {
			enem.moveTo(x+speed, y);
		}
		else if(col==6 && row>9 && row<11) {
			enem.moveTo(x, y+speed);
		}
		else if(col==6 && row>3 && row<6 ) {
			enem.moveTo(x, y-speed);
		}
		else if((row==3||row==11)&&(col<12)) {
			enem.moveTo(x+speed, y);
		}
		else if(row>=3 && row<7 && col==12) {
			enem.moveTo(x, y+speed);
		}
		else if(row<12 && row>7 && col==12) {
			enem.moveTo(x, y-speed);
		}
		else if((row==7||row==8)&&(col>7 && col<13)) {
			enem.moveTo(x-speed, y);
		}
		else if((row==end.get(0).getYCordinates()||row==end.get(1).getYCordinates())
				||(col==end.get(0).getXCordinates()||col==end.get(1).getXCordinates())) {
			bastHit(enem.getPower());
			enem.setKilledByBase();
			// kill the enemy
			enem.receiveDamage(1000);
		}
		
	}

}
