package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import maps.*;
import tile.Tile;
import tile.TileEnum;

public class MapOneView extends Canvas{
	private final int TILE_NUMBER_HOR = 16;
	private final int TILE_NUMBER_VER = 16;
	private final int TILE_SIZE = 40;
	private final int CANVAS_WIDTH = TILE_NUMBER_HOR * TILE_SIZE;
	private final int CANVAS_HEIGHT = TILE_NUMBER_VER * TILE_SIZE;
	private Map map;

	
	public MapOneView() {
		this.setWidth(CANVAS_WIDTH);
		this.setHeight(CANVAS_HEIGHT);
		map = new MapOne();
		drawMap();
	}


	private void drawMap() {
		for(int i = 0; i< TILE_NUMBER_VER; i++) {
			for(int j=0; j<TILE_NUMBER_HOR; j++) {
				Tile currentTile = map.getMap()[i][j];
				if(currentTile.getType() == TileEnum.base)
					this.getGraphicsContext2D().setFill(Color.WHITE);
				else if(currentTile.getType() == TileEnum.enemyPath)
					this.getGraphicsContext2D().setFill(Color.RED);
				else if(currentTile.getType() == TileEnum.towerSpot)
					this.getGraphicsContext2D().setFill(Color.BLUE);
				else if(currentTile.getType() == TileEnum.start)
					this.getGraphicsContext2D().setFill(Color.BLACK);
				else
					this.getGraphicsContext2D().setFill(Color.GRAY);
				
				this.getGraphicsContext2D().fillRect(TILE_SIZE * j, TILE_SIZE*i, TILE_SIZE, TILE_SIZE);
			}
		}
	}
	
	
}
