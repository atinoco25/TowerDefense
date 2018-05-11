package tile;

/*
 * This class will keep the information of a single tile
 * Information could be if there is currently a tower placed in there, or if the 
 * 	current tile is only for enemies or base. The tile could also allow nothing to be there.
 */

public class TileBase implements Tile {
	private TileEnum tileType; //The type of this tile object
	private boolean occupied; //useful for tower tiles
	
	//constructor
	public TileBase(TileEnum type) {
		tileType= type;
		occupied = false;
	}
	
	/*
	 * This method will return the type of the tile
	 */
	@Override
	public TileEnum getType() {
		return tileType;
	}

	/*
	 * This method will set the tile occupied, but only if this is a tower tile
	 *  otherwise it does not make sense to mark it occupied
	 */
	@Override
	public void setOccupied() {
		if (tileType == TileEnum.towerSpot)
			occupied = true;
	}

	/*
	 * Set the tile empty, so no tower is in here. Useful if a tower dies
	 */
	@Override
	public void setEmpty() {
		occupied = false;
	}

	/*
	 * Returns true if there is a tower in this tile
	 */
	@Override
	public boolean isOccupied() {
		return occupied;
	}

	@Override
	public void setType(TileEnum newType) {
		tileType = newType;
	}


}
