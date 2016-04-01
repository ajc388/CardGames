package CardGames;
import java.util.Comparator;
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
			cards.add(new Card("Two", suit, 2));
			cards.add(new Card("Three", suit, 3));
			cards.add(new Card("Four", suit, 4));
			cards.add(new Card("Five", suit, 5));
			cards.add(new Card("Six", suit, 6));
			cards.add(new Card("Seven", suit, 7));
			cards.add(new Card("Eight", suit, 8));
			cards.add(new Card("Nine", suit,9 ));
			cards.add(new Card("Ten", suit, 10));
			cards.add(new Card("Jack", suit, 11));
			cards.add(new Card("Queen", suit, 12));
			cards.add(new Card("King", suit, 13));
			cards.add(new Card("Ace", suit, 14));
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
