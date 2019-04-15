package map;

public class Cordinates{
	private int[] cord = {0,0} ;
	
	public Cordinates(int x,int y) {
		
		cord[0] = x;
		cord[1] = y;
	}
	
	public int getXCordinates(){
		return cord[0];
	}
	
	public int getYCordinates() {
		return cord[1];
	}
}