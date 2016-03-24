import java.util.ArrayList;
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
			cards.add(new Card("Two", 2, new ArrayList<Integer>(2), suit));
			cards.add(new Card("Three", 3, new ArrayList<Integer>(3), suit));
			cards.add(new Card("Four", 4, new ArrayList<Integer>(4), suit));
			cards.add(new Card("Five", 5, new ArrayList<Integer>(5), suit));
			cards.add(new Card("Six", 6, new ArrayList<Integer>(6), suit));
			cards.add(new Card("Seven", 7, new ArrayList<Integer>(7), suit));
			cards.add(new Card("Eight", 8, new ArrayList<Integer>(8), suit));
			cards.add(new Card("Nine", 9, new ArrayList<Integer>(9), suit));
			cards.add(new Card("Ten", 10, new ArrayList<Integer>(10), suit));
			cards.add(new Card("Jack", 11, new ArrayList<Integer>(10), suit));
			cards.add(new Card("Queen", 12, new ArrayList<Integer>(10), suit));
			cards.add(new Card("King", 13, new ArrayList<Integer>(10), suit));
			cards.add(new Card("Ace", 14, new ArrayList<Integer>(11), suit));
		}
		
		shuffle();
	}
	
	/***
	 * Randomly shuffles the deck.
	 */
	//This is an O(n^2) shuffling algorithm.
	//It is not efficient, but is simple enough to get the job done,
	//regardless of deck size.
	public void shuffle()
	{
		for(int i=0; i< cards.size(); i++) {
	        int card = (int) (Math.random() * (cards.size()-i));
	        cards.addLast(cards.remove(card));
	    }
	}
	
	/***
	 * Draw a number of cards from the deck as requested.
	 * @return a LinkedList of cards.
	 */
	//This is an O(n) algorithm, where n is numOfCards.
	public LinkedList<Card> draw(int numOfCards)
	{
		LinkedList<Card> hand = new LinkedList<Card>();
		for ( int n = 0; n < numOfCards; n++ )
		{
			hand.add(cards.pop());
		}
		return cards;
	}
}
