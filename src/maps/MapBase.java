package maps;

import tile.*;

public abstract class MapBase implements Map{
	protected Tile[][] map;
	private final int MAP_WIDTH= 16;
	private final int MAP_HEIGHT = 16;
	
	public MapBase() {
		map = new TileBase[MAP_WIDTH][MAP_HEIGHT];	
		initializeMap();
	}
	
	
	private void initializeMap() {
		for(int i = 0; i<MAP_WIDTH;i++) {
			for(int j=0;j<MAP_HEIGHT;j++) {
				map[i][j] = new TileBase(TileEnum.towerSpot);
			}
		}
	}


	@Override
	public Tile[][] getMap() {
		return map;
	}

	@Override
	public void mapToString() {
		for(int i = 0; i<MAP_WIDTH;i++) {
			for(int j=0;j<MAP_HEIGHT;j++) {
				System.out.print(getMap()[i][j].getType().toString()+"-");
			}
			System.out.println();
		}
	}
}
