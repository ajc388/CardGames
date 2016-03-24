import java.util.LinkedList;

/**
 * The hand class just represents the cards a player has.
 * @author sudoninja
 */
public class Hand {
	public LinkedList<Card> cards;
	
	public Hand()
	{
		cards = new LinkedList<Card>();
	}
	public Hand(LinkedList<Card> cards) 
	{
		this.cards = cards;
	}
}
