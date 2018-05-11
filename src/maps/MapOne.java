package maps;

import tile.*;

/*
 * This class will set up a map
 */
public class MapOne extends MapBase{
	public MapOne() {
		super();
		
		setUpMap();
	}

	/*
	 * The methods to assign each spot in the map will later be improved
	 * For now I am just worried to make it work
	 */
	private void setUpMap() {
		setUpStartTiles();
		setUpEnemyPath();
		setUpBase();
		setUpUnableToBuildAroundStart();
	}

	private void setUpUnableToBuildAroundStart() {
		map[5][0] = new TileBase(TileEnum.nothing);
		map[5][1] = new TileBase(TileEnum.nothing);
		map[5][2] = new TileBase(TileEnum.nothing);
		map[6][0] = new TileBase(TileEnum.nothing);
		map[6][1] = new TileBase(TileEnum.nothing);
		map[6][2] = new TileBase(TileEnum.nothing);
		map[8][0] = new TileBase(TileEnum.nothing);
		map[8][1] = new TileBase(TileEnum.nothing);
		map[8][2] = new TileBase(TileEnum.nothing);
		map[9][0] = new TileBase(TileEnum.nothing);
		map[9][1] = new TileBase(TileEnum.nothing);
		map[9][2] = new TileBase(TileEnum.nothing);


	}

	private void setUpBase() {
		map[7][7] = new TileBase(TileEnum.base);
	}

	private void setUpEnemyPath() {
		map[7][1] = new TileBase(TileEnum.enemyPath);
		map[7][2] = new TileBase(TileEnum.enemyPath);
		map[7][3] = new TileBase(TileEnum.enemyPath);
		map[6][3] = new TileBase(TileEnum.enemyPath);
		map[5][3] = new TileBase(TileEnum.enemyPath);
		map[4][3] = new TileBase(TileEnum.enemyPath);
		//map[7][1] = new TileBase(TileEnum.enemyPath);
		map[3][3] = new TileBase(TileEnum.enemyPath);
		map[3][4] = new TileBase(TileEnum.enemyPath);
		map[3][5] = new TileBase(TileEnum.enemyPath);
		map[3][6] = new TileBase(TileEnum.enemyPath);
		map[3][7] = new TileBase(TileEnum.enemyPath);
		map[3][8] = new TileBase(TileEnum.enemyPath);
		map[3][9] = new TileBase(TileEnum.enemyPath);
		map[3][10] = new TileBase(TileEnum.enemyPath);
		map[3][11] = new TileBase(TileEnum.enemyPath);
		map[4][11] = new TileBase(TileEnum.enemyPath);
		map[5][11] = new TileBase(TileEnum.enemyPath);
		map[6][11] = new TileBase(TileEnum.enemyPath);
		map[7][11] = new TileBase(TileEnum.enemyPath);
		map[8][11] = new TileBase(TileEnum.enemyPath);
		map[9][11] = new TileBase(TileEnum.enemyPath);
		map[10][11] = new TileBase(TileEnum.enemyPath);
		map[11][11] = new TileBase(TileEnum.enemyPath);
		map[12][11] = new TileBase(TileEnum.enemyPath);
		map[12][10] = new TileBase(TileEnum.enemyPath);
		map[12][9] = new TileBase(TileEnum.enemyPath);
		map[12][8] = new TileBase(TileEnum.enemyPath);
		map[12][7] = new TileBase(TileEnum.enemyPath);
		map[12][6] = new TileBase(TileEnum.enemyPath);
		map[12][5] = new TileBase(TileEnum.enemyPath);
		map[12][4] = new TileBase(TileEnum.enemyPath);
		map[12][3] = new TileBase(TileEnum.enemyPath);
		map[11][3] = new TileBase(TileEnum.enemyPath);
		map[10][3] = new TileBase(TileEnum.enemyPath);
		map[10][4] = new TileBase(TileEnum.enemyPath);
		map[10][5] = new TileBase(TileEnum.enemyPath);
		map[10][6] = new TileBase(TileEnum.enemyPath);
		map[10][7] = new TileBase(TileEnum.enemyPath);
		map[9][7] = new TileBase(TileEnum.enemyPath);
		map[8][7] = new TileBase(TileEnum.enemyPath);


	}

	private void setUpStartTiles() {
		map[7][0] = new TileBase(TileEnum.start);
	}

}
