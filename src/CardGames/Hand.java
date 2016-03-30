package CardGames;
import java.util.LinkedList;

/**
 * An abstraction for the player's hand of cards.
 * @author sudoninja
 */
public class Hand {
	public LinkedList<Card> cards;
	
	public Hand() { cards = new LinkedList<Card>(); }
}
