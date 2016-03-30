package CardGames;

/***
 * A class that stores all data related to a Casino playing card.
 * @author sudoninja
 */
public class Card {
	
	public String name;
	public Suit suit;
	public Color color;
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
	 * The most basic card constructor.
	 * @param name is the name of the card: "Ace", "Queen", "Jack", "One", etc...
	 * @param suit any enumeration of suit.
	 */
	public Card(String name, Suit suit)
	{
		this(name, suit, false); //invokes the larger constructor
	}
	
	/***
	 * A card constructor that includes faceUp.
	 * @param name is the name of the card: "Ace", "Queen", "Jack", "One", etc...
	 * @param suit is either a club, spade, diamond, or heart.
	 * @param faceUp makes the card visible to other players.
	 */
	public Card(String name, Suit suit, boolean faceUp)
	{
		this.name = name;
		this.suit = suit;
		if ( suit == Suit.CLUB || suit == Suit.SPADE )
			this.color = Color.BLACK;
		else
			this.color = Color.RED;	
		this.faceUp = faceUp;
	}
	
	/***
	 * Will flip the visibility of the card.
	 */
	public void flipCard()
	{
		faceUp = (faceUp ? false: true);
	}
}
