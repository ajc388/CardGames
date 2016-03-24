import java.util.LinkedList;

/***
 * The Deck can hold anywhere from 1 to infinite are amounts of cards.
 * No restrictions are imposed because there are so many variants to
 * common Casino Card games. 
 * @author sudoninja
 */
public class Deck {
	public LinkedList<Card> deck;
	
	/***
	 * The standard deck consists of 52 cards, 13 cards per suit,
	 * from ace to king. There are no jokers in this deck and
	 * Ace is high.
	 */
	public Deck() 
	{
		deck = new LinkedList<Card>();
		
		for ( Card.Suit suit : Card.Suit.values())
		{
			deck.add(new Card("Two", 2, 2, suit));
			deck.add(new Card("Three", 3, 3, suit));
			deck.add(new Card("Four", 4, 4, suit));
			deck.add(new Card("Five", 5, 5, suit));
			deck.add(new Card("Six", 6, 6, suit));
			deck.add(new Card("Seven", 7, 7, suit));
			deck.add(new Card("Eight", 8, 8, suit));
			deck.add(new Card("Nine", 9, 9, suit));
			deck.add(new Card("Ten", 10, 10, suit));
			deck.add(new Card("Jack", 11, 10, suit));
			deck.add(new Card("Queen", 12, 10, suit));
			deck.add(new Card("King", 13, 10, suit));
			deck.add(new Card("Ace", 14, 11, suit));
		}
	}

	/***
	 * 
	 */
	public void shuffle()
	{
		
	}
	
	/***
	 * Draw as many number of cards from the deck.
	 * The cards are then removed from the deck.
	 * @param numOfCards representing how many cards are drawed.
	 * @return a LinkedList of cards.
	 */
	public LinkedList<Card> draw(int numOfCards)
	{
		LinkedList<Card> cards = new LinkedList<Card>();
		for ( int n = 0; n < numOfCards; n++ )
		{
			cards.add(deck.pop());
		}
		return cards;
	}
}
