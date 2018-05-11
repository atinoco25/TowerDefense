package tile;

public interface Tile {
	public TileEnum getType();
	public void setType(TileEnum type);
	public boolean isOccupied();
	public void setOccupied();
	public void setEmpty();
}
