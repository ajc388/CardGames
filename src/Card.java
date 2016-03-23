/***
 * A class that stores all data related to a casino playing card.
 * @author sudoninja
 *
 */
public class Card {
	
	public String name;
	public int id;
	public Suit suit;
	public int pointValue;
	public boolean faceUp;
	
	/***
	 * Suit was made static so it could be accessed throughout the program
	 * using Suit.ENUMVALUE.
	 *
	 */
	public static enum Suit {
		SPADE, CLUB, HEART, DIAMOND 
	}
	
	/***
	 * 
	 * @param name should be a string literal like: "Ace", "Queen", "Jack", "One", etc...
	 * @param id should be a value 1-13 representing a unique ID of the card.
	 * @param value a point value used for the calculation of a winning hand.
	 * @param suit any enumeration of suit.
	 */
	public Card(String name, int id, int value, Suit suit)
	{
		this.name = name;
		this.id = id;
		this.pointValue = value;
		this.suit = suit;
	}
}
