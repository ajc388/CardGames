package CardGames;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import CardGames.Card.Suit;

public class TexasHoldem extends Game {
	public TexasHoldem()
	{
		table = new Table("Bob", 1000.0, new Deck(), 5.0, 5.0, 6);
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
	public LinkedList<Player> rankPlayerCards() {
		LinkedList<Player> winners = new LinkedList<Player>();
		Player prev = table.players.peek();
		for ( Player p : table.players )
		{
			if ( p.inPlay )
			{
				p.rank = evaluateHand(p);
				if (p.rank > prev.rank)
				{
					winners.add(p);
					winners.remove(prev);
				}
				prev = p;
			} else {
				p.rank = 0;
			}
		}
		return winners;
	}
	
	/***
	 * Evaluates the cards in texas holdem including
	 * the community cards.
	 * @param p
	 * @return
	 */
	private int evaluateHand(Player p)
	{
		p.cards.addAll(Table.dealer.cards);
		int highVal = highCardRank(p.cards);
		int [] cardCounts = new int[15];
		int [] suitCounts = new int[4];
		for ( Card c : p.cards ) 
		{
			if ( c.name.toUpperCase() == "ACE" )
				cardCounts[0]++; //ace can is rank 1 and 14
			cardCounts[c.rank]++; //rank is 2 - 14 by default
			suitCounts[c.suit.ordinal()]++;
		}
		
		if ( isRoyalFlush(cardCounts, suitCounts) )
			return  highVal + 108;
		else if ( isFourOfAKind(cardCounts) )
			return highVal + 94;
		else if ( isFullHouse(cardCounts))
			return highVal + 80;
		else if ( isFlush(suitCounts) )
			return highVal + 70;
		else if ( isStraight(cardCounts) )
			return highVal + 56;
		else if ( isThreeOfAKind(cardCounts) )
			return highVal + 42;
		else if ( isTwoPair(cardCounts) )
			return highVal + 28;
		else if ( isTwoOfAKind(cardCounts) )
			return highVal + 14;
		else 
			return highVal;
		
	}

	private boolean isFlush(int[] suitCounts) {
		for ( int i = 0; i < suitCounts.length; i++)
			if ( suitCounts[i] == 5)
				return true;
		return false;
	}

	private boolean isStraight(int[] cardCounts) {
		int consecutive = 0;
		for (int i = 0; i < cardCounts.length; i++)
		{
			if ( cardCounts[i] > 0)
				consecutive++;
			else
				consecutive = 0;
		}
		return consecutive == 5;
	}

	private boolean isTwoPair(int[] cardCounts) {
		int pairs = 0;
		for ( int i = 0; i < cardCounts.length; i++ )
			if ( cardCounts[i] == 2 )
				pairs++;
		return pairs == 2;
	}

	private boolean isFullHouse(int[] cardCounts) {
		return isThreeOfAKind(cardCounts) && isTwoOfAKind(cardCounts);
	}

	private boolean isTwoOfAKind(int[] cardCounts) {
		for (int i = 0; i < cardCounts.length; i++)
			if ( cardCounts[i] == 2 )
				return true;
		return false;
	}

	private boolean isThreeOfAKind(int[] cardCounts) {
		for (int i = 0; i < cardCounts.length; i++)
			if ( cardCounts[i] == 3 )
				return true;
		return false;
	}

	private boolean isFourOfAKind(int[] cardCounts) {
		for (int i = 0; i < cardCounts.length; i++)
			if ( cardCounts[i] == 4 )
				return true;
		return false;
	}

	private boolean isRoyalFlush(int[] cardCounts, int[] suitCounts) {
		return isStraight(cardCounts) && isFlush(suitCounts);
	}

	private int highCardRank(LinkedList<Card> cards) {
		Card highCard = cards.peek();
		for ( Card c : cards)
			if ( c.rank > highCard.rank )
				highCard = c;
		return highCard.rank;
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
			Table.dealer.deck.cards.pop(); //burn card
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
			Table.dealer.deck.cards.pop(); //burn card
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
			Table.dealer.deck.cards.pop(); //burn card
			Table.dealer.deal(1, Table.dealer, true);
			Table.dealer.showCards("Community Cards (River)\n");
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the river.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
