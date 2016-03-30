package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CardGames.Card;
import CardGames.Card.Suit;

public class TestCard {

	Card c;
	
	@Before
	public void setUp() throws Exception {
		c = new Card("Ace", Suit.CLUB);
	}

	@After
	public void tearDown() throws Exception {
		c = null;
	}

	@Test
	public void testFlip() {
		assertFalse(c.faceUp);
		c.flipCard();
		assertTrue(c.faceUp);
	}

}
