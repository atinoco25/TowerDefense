package maps;

import tile.*;

public interface Map {
	public Tile[][] getMap();
	public void mapToString();
}
