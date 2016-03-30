/**
 * 
 */
package Tests;

import CardGames.Card;
import CardGames.Deck;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author sudoninja
 */
public class TestDeck {

	private Deck deck;
	
	@Test
	public void testStandardDeckCount() {
		deck = new Deck();
		assertEquals(52, deck.cards.size());
		deck = null;
	}
	
	@Test
	public void testStandardDeckSuits() {
		deck = new Deck();
		int clubs = 0;
		int spades = 0;
		int hearts = 0;
		int diamonds = 0;
		for ( Card c : deck.cards )
		{
			if (c.suit == Card.Suit.CLUB ) { clubs++; }
			if (c.suit == Card.Suit.SPADE ) { spades++; }
			if (c.suit == Card.Suit.HEART ) { hearts++; }
			if (c.suit == Card.Suit.DIAMOND ) { diamonds++; }
		}
		assertEquals(13, clubs);
		assertEquals(13, hearts);
		assertEquals(13, diamonds);
		assertEquals(13, spades);
		deck = null;
	}
	
	@Test
	public void testStandardDeckColor() {
		deck = new Deck();
		int red = 0;
		int black = 0;
		//Count colors
		for ( Card c : deck.cards )
		{
			if ( c.color == Card.Color.BLACK ) { black++; }
			if ( c.color == Card.Color.RED ) { red++; }
		}
		assertEquals(26, red);
		assertEquals(26, black);
		deck = null;
	}
}
