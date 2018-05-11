package tile;

public interface Tile {
	public TileType getType();
	public boolean isOccupied();
	public void setOccupied();
	public void setEmpty();
}
