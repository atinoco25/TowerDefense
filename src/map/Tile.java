package map;

public class Tile {
	private String name;
	private boolean buildable;
	
	public Tile(String name, boolean build) {
		this.name = name;
		buildable = build;
	}
	
	public String getName() {
		return name;
	}
	public boolean getBuildable() {
		return buildable;
	}

}
