package CardGames;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import CardGames.Card.Suit;

public class TexasHoldem extends Game {
	private Table table;
	private boolean playAgain;
	
	public TexasHoldem()
	{
		table = new Table("Bob", 1000.0, new Deck(), 5.0);
		playAgain = true;
	}
	
	@Override
	public void run() {
		while ( playAgain )
		{
			setup(); //Setup the game	
			dealHoleCards(); //Deal cards to all players in play
			bettingRound(table.players);
			dealFlop();
			bettingRound(table.players);
			dealTurn();
			bettingRound(table.players);
			dealRiver();
			bettingRound(table.players);
			close();
		}
	}

	@Override
	public LinkedList<Player> evaluateCards() {
		LinkedList<Player> winners = new LinkedList<Player>();
		int [] rank = new int[table.players.size()];
		int idx = 0;
		for ( Player p : table.players)
		{
			if ( p.inPlay )
			{
				rank[idx] = evaluateHand(p);
			}
			else 
			{
				rank[idx] = 0;
			}
			idx++;
		}
		
		return winners;
	}
	
	private int evaluateHand(Player p)
	{
		p.cards.addAll(table.dealer.cards);
		
		Collections.sort(p.cards, new Comparator<Card>() { 
			@Override
	         public int compare(Card c1, Card c2) {
	            if ( c1.rank < c2.rank )
	            	return 1;
	            else
	            	return -1;
	         }
	     });
		
		System.out.println(p.showCards(""));
		/*
		 * 1.) Straight flush - all cards of same suit and straight
		 * 2.) 4 of a kind - 4 cards of a kind
		 * 3.) Full house - 3 cards of a kind and a pair
		 * 4.) Flush - all cards of same suit
		 * 5.) Straight - all consecutive cards
		 * 6.) 3 of a kind
		 * 7.) 2 of a kind
		 * 8.) Highest rank card
		 */
//		boolean flush = isFlush();
		
	}
	
//	private boolean isPair(LinkedList<Card> cards)
//	{
//		for ( Card c : cards )
//		{
//		}
//	}
	
	private boolean isFlush(LinkedList<Card> cards)
	{
		int clubs = 0;
		int spades = 0;
		int hearts = 0;
		int diamonds = 0;
		for (Card c : cards)
		{
			switch ( c.suit ) 
			{
				case CLUB:
					clubs++;
					break;
				case DIAMOND:
					diamonds++;
					break;
				case HEART:
					hearts++;
					break;
				case SPADE:
					spades++;
					break;
			}
		}
		if ( clubs >= 5 || 
		     spades >= 5 ||  
		     hearts >= 5 ||
		     diamonds >= 5 )
			return true;
		else
			return false;
	}
	
	//===============================================
	//			    CARD DEALING PHASES 
	//===============================================
	private void dealHoleCards()
	{
		for ( Player p : table.players )
		{
			if ( p.inPlay )
			{
				try {
					Table.dealer.deal(2, p, false);
					p.showCards(p.name + "Cards\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void dealFlop()
	{
		try {
			Table.dealer.deal(3, Table.dealer, true);
			Table.dealer.showCards("Community Cards (Flop)\n");
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the flop.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dealTurn()
	{
		try {
			Table.dealer.deal(1, Table.dealer, true);
			Table.dealer.showCards("Community Cards (Turn)\n");
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the turn.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dealRiver()
	{
		try {
			Table.dealer.deal(1, Table.dealer, true);
			Table.dealer.showCards("Community Cards (River)\n");
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the river.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
