package Tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CardGames.Card;
import CardGames.Dealer;
import CardGames.Deck;
import CardGames.Game;
import CardGames.GameLog;
import CardGames.LogEntry;

public class TestDealer {

	private Dealer dealer;
	
	@Before
	public void setUp() throws Exception {
		dealer = new Dealer("MasterChief", 9000);
	}

	@After
	public void tearDown() throws Exception {
		dealer = null;
		GameLog.delete();
	}
	
	@Test 
	public void testShuffle() 
	{
		dealer.deck = new Deck();
		Deck unshuffledDeck = new Deck();
		dealer.shuffle();
		
		int similarOrderedCards = 0; 
		
		assertEquals(dealer.deck.cards.size(), unshuffledDeck.cards.size()); //just to make sure we are comparing equal decks
		
		for (int idx = 0; idx < dealer.deck.cards.size(); idx++ )
		{
			Card unshuffledCard = dealer.deck.cards.pop();
			Card shuffledDeckCard = unshuffledDeck.cards.pop();
			if ( shuffledDeckCard.name.equals(unshuffledCard.name)
				&& shuffledDeckCard.suit.equals(unshuffledCard.suit))
				similarOrderedCards++;			
		}
		
		//System.out.println(similarOrderedCards);
		assertTrue(similarOrderedCards < dealer.deck.cards.size()); //atleast one card should be out of order - hopefully.
		assertNotEquals(unshuffledDeck, dealer.deck); //apparently this does element wise comparison in Java... cool!
		dealer.deck = null;
		unshuffledDeck = null;
	}
	
	@Test(expected=Exception.class)
	public void testDeal() throws Exception 
	{
		dealer.deck = new Deck();
		dealer.deal(0, dealer);
		assertEquals(0, dealer.cards.size());
		
		dealer.deal(1, dealer);
		assertEquals(2, dealer.cards.size());
		assertEquals(51, dealer.deck.cards.size());
		
		dealer.deal(5, dealer);
		assertEquals(7, dealer.cards.size());
		assertEquals(46, dealer.cards.size());
		
		//What happens if you draw too many cards... ?
		dealer.deal(47, dealer);
		assertEquals(6, dealer.cards.size());
		
		dealer.deck = null;
	}
}
