package mainModel;

public class Inventory {
	
	private int gold;
	
	/**
	 * Constructor which initializes the player to start off with 90 gold per game.
	 * 
	 */
	public Inventory() {
		this.gold = 200;
	}
	
	/**
	 * Getter for gold
	 * 
	 * @return current gold amount
	 */
	public int getGold() {
		return this.gold;
	}
	
	/**
	 * Setter for gold
	 * 
	 * @param amount
	 */
	public void setGold(int amount) {
		this.gold = amount;
	}
	
	/**
	 * Determines if a player can purchase a Tower
	 * 
	 * @param cost
	 * @return true if player can afford the Tower
	 */
	public boolean canAfford(int cost) {
		return this.gold >= cost;
	}

	/**
	 * Subtracts the cost from the gold amount
	 * 
	 * @param cost
	 */
	public void subtractGold(int cost) {
		this.gold -= cost;
	}

}
