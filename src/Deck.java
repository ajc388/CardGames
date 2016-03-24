import java.util.ArrayList;
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
			deck.add(new Card("Two", 2, new ArrayList<Integer>(2), suit));
			deck.add(new Card("Three", 3, new ArrayList<Integer>(3), suit));
			deck.add(new Card("Four", 4, new ArrayList<Integer>(4), suit));
			deck.add(new Card("Five", 5, new ArrayList<Integer>(5), suit));
			deck.add(new Card("Six", 6, new ArrayList<Integer>(6), suit));
			deck.add(new Card("Seven", 7, new ArrayList<Integer>(7), suit));
			deck.add(new Card("Eight", 8, new ArrayList<Integer>(8), suit));
			deck.add(new Card("Nine", 9, new ArrayList<Integer>(9), suit));
			deck.add(new Card("Ten", 10, new ArrayList<Integer>(10), suit));
			deck.add(new Card("Jack", 11, new ArrayList<Integer>(10), suit));
			deck.add(new Card("Queen", 12, new ArrayList<Integer>(10), suit));
			deck.add(new Card("King", 13, new ArrayList<Integer>(10), suit));
			deck.add(new Card("Ace", 14, new ArrayList<Integer>(11), suit));
		}
		
		shuffle();
	}

	/***
	 * Randomly shuffles the deck.
	 */
	//This is an O(n^2) shuffling algorithm.
	//It is not efficient, but is simple enough to get the job done.
	public void shuffle()
	{
		for(int i=0; i<deck.size(); i++) {
	        int card = (int) (Math.random() * (deck.size()-i));
	        deck.addLast(deck.remove(card));
	    }
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
