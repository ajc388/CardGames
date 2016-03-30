package CardGames;
import java.util.LinkedList;

/***
 * The Deck can hold anywhere from 1 to infinite are amounts of cards.
 * No restrictions are imposed because there are so many variants to
 * common Casino Card games. 
 * @author sudoninja
 */
public class Deck {
	public LinkedList<Card> cards;
	
	/***
	 * The standard deck consists of 52 cards, 13 cards per suit,
	 * from ace to king. There are no jokers in this deck and
	 * Ace is high.
	 */
	public Deck() 
	{
		cards = new LinkedList<Card>();
		
		for ( Card.Suit suit : Card.Suit.values())
		{
			cards.add(new Card("Two", suit));
			cards.add(new Card("Three", suit));
			cards.add(new Card("Four", suit));
			cards.add(new Card("Five", suit));
			cards.add(new Card("Six", suit));
			cards.add(new Card("Seven", suit));
			cards.add(new Card("Eight", suit));
			cards.add(new Card("Nine", suit));
			cards.add(new Card("Ten", suit));
			cards.add(new Card("Jack", suit));
			cards.add(new Card("Queen", suit));
			cards.add(new Card("King", suit));
			cards.add(new Card("Ace", suit));
		}
	}
	
	/* TODO: You may want to implement different constructors
	 * to build unique decks. Otherwise, just hand this constructor
	 * the deck.
	 */
	public Deck(LinkedList<Card> cards)
	{
		this.cards = cards;
	}
}
