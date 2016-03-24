import java.util.ArrayList;

/***
 * A class that stores all data related to a casino playing card.
 * @author sudoninja
 *
 */
public class Card {
	
	public String name;
	public int id;
	public Suit suit;
	public Color color;
	public ArrayList<Integer> pointValue;
	public boolean faceUp;	
	
	/***
	 * Suit was made static so it could be accessed throughout the program
	 * using Card.Suit.EnumVal.
	 */
	public static enum Suit {
		SPADE, CLUB, HEART, DIAMOND 
	}
	
	/***
	 * Color was made static so it could be accessed throughout the
	 * program using Card.Color.EnumVal 
	 */
	public static enum Color {
		RED, BLACK
	}
	
	/***
	 * 
	 * @param name should be a string literal like: "Ace", "Queen", "Jack", "One", etc...
	 * @param id should be a value 1-13 representing a unique ID of the card.
	 * @param points a point value used for the calculation of a winning hand.
	 * @param suit any enumeration of suit.
	 */
	public Card(String name, int id, ArrayList<Integer> points, Suit suit)
	{
		this(name, id, points, suit, false); //invokes the larger constructor
	}
	
	public Card(String name, int id, ArrayList<Integer> points, Suit suit, boolean faceUp)
	{
		this.name = name;
		this.id = id;
		this.pointValue = points;
		this.suit = suit;
		if ( suit == Suit.CLUB || suit == Suit.SPADE )
			this.color = Color.BLACK;
		else
			this.color = Color.RED;	
		this.faceUp = faceUp;
	}
}
