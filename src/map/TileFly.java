package map;

import javafx.scene.image.Image;
import java.util.HashMap;

/*
 * Author: Ryan McComon
 * Description: The tiles will have a flyweight of all the possible images the tiles will have in the map
 * I'm hoping this would make running the game less taxing when it spawns a lot of enemies on screen
 * 
 * */

public class TileFly {
	
	private final Image mapSprite;
	private final String mapSpriteName;
	private static final HashMap<String, TileFly> tileFlyweight = new HashMap<String, TileFly>();
	
	private TileFly(String name, Image mapPiece) {
		mapSpriteName = name;
		mapSprite = mapPiece;
		
	}
	
	public String getName() {
		return mapSpriteName;
	}
	public Image getImage() {
		return mapSprite;
	}
	
	public static TileFly getFlyweight(String name) {
		if(tileFlyweight.containsKey(name)) {
			return tileFlyweight.get(name);
		}else {
			Image image = new Image("file:img/"+name+".png", false);
			TileFly newFly = new TileFly(name, image);
			tileFlyweight.put(name, newFly);
			return newFly;
		}
	}

}
