package CardGames;
import java.util.LinkedList;

import javax.activity.InvalidActivityException;

/**
 * @author sudoninja
 */
public class Player {
	public String name;
	private double bank;
	public Hand hand;
	public boolean inPlay;
	
	public Player(String name, double bank)
	{
		this.name = name;
		this.bank = bank;
		this.hand = new Hand();
		this.inPlay = true;
	}
	
	//===========================================================
	//					   GAMBLING ACTIONS
	//===========================================================
	/***
	 * A player can place a bet on the table.
	 * @param amount
	 * @throws Exception
	 */
	public void bet(double amount) throws Exception
	{
		LogEntry e = Game.log.peek(); // grab last betting action
		if ( e == null )
			throw new NullPointerException();
		else if ( e.amt > 0 )
			throw new InvalidActivityException("Player cannot use the bet method if there is an existing bet.");
		else if (amount <= 0)
			throw new IllegalArgumentException("Player cannot bet less than or equal to no money.");
		else if ( amount > bank )
			throw new IllegalArgumentException("Player does not have enough money to make the bet.");
		else 
		{
			bank -= amount;
			Table.pot += amount;
			Game.log.push(new LogEntry( 
					LogEntry.Type.BET_ACTION, 
					"Player " + name + " bets " + amount + ".",
					amount));
		}
	}
	
	/***
	 * Player matches the money on the table and raises 
	 * an additional amount of money.
	 * @param amount
	 * @throws Exception
	 */
	public void raise(double amount) throws Exception
	{
		LogEntry e = Game.log.peek(); // grab last betting action
		if ( e == null )
			throw new NullPointerException();
		else if ( e.amt == 0 && e.logType == LogEntry.Type.BET_ACTION )
			throw new InvalidActivityException("Player cannot use the raise method unless there is an existing bet.");
		else if (amount <= 0)
			throw new IllegalArgumentException("Player cannot raise less than or equal to no money.");
		else if ( (amount+e.amt) > bank )
			throw new IllegalArgumentException("Player does not have enough money to match and raise.");
		else 
		{
			bank -= amount;
			Table.pot += amount + e.amt;
			Game.log.push(new LogEntry(
					LogEntry.Type.BET_ACTION,
					"Player " + name + " matches bet and raises " + amount + ".",
					amount + e.amt));
		}
	}
	
	/***
	 * A player may match the existing bet on the table.
	 * This can only be done if there was a prior bet for
	 * a positive amount of money.
	 * @param amount
	 * @throws Exception
	 */
	public void call() throws Exception
	{
		LogEntry log = Game.log.peek(); // grab last betting action
		if ( log == null )
			throw new NullPointerException();
		else if ( log.amt == 0 && log.logType == LogEntry.Type.BET_ACTION )
			throw new InvalidActivityException("Player cannot use the call method unless there is an existing bet.");
		else 
		{
			bank -= log.amt;
			Table.pot += log.amt;
			Game.log.push(new LogEntry(
					LogEntry.Type.BET_ACTION,
					"Player " + name + " matches the bet for " + log.amt + ".",
					log.amt));
		}
	}
	
	/***
	 * The required ante to play for the round.
	 * This amount is set by the table.
	 * @param amount of buyIn
	 */
	public void buyIn()
	{
		inPlay = true;
		bank -= Table.ante;
		Table.pot += Table.ante;
		Game.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " buys in for the round " + Table.ante + ".",
				Table.ante));
	}
	
	public void optOut()
	{
		inPlay = false;
		Game.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " opts out for the round."));
	}
	
	/***
	 * This player has collected a percentage of the pot.
	 * If the player won everything enter 1.0.
	 * @param winRatio a number less than 1.0 but greater than 0.0
	 * @throws Exception 
	 */
	public void collect(double winRatio) throws Exception
	{
		if ( winRatio < 0.0 || winRatio > 1.0 )
			throw new Exception("The win ratio must be between 0 and 1.0");
		else
		{
			bank += Table.pot*winRatio;
			Game.log.push(new LogEntry(
					LogEntry.Type.PLAYER_ACTION,
					"Player " + name + " won the pot for a total of " + Table.pot*winRatio + "."));
			Table.pot -= Table.pot/winRatio;
		}
	}
	
	/**
	 * @throws Exception throws exception if there is a bet on the table.
	 */
	public void check() throws Exception
	{
		LogEntry e = Game.log.peek();
		if ( e.amt > 0 )
			throw new Exception("Invalid player action. A player cannot check if there is a bet on the table.");
		else 
		{
			Game.log.push(new LogEntry(
					LogEntry.Type.PLAYER_ACTION,
					"Player " + name + " checks."));
		}
	}
	
	//=============================================================
	//                       Player Actions
	//=============================================================
	/***
	 * A player can prompt the dealer to give him a number of cards.
	 * @param numOfCards to be drawn from dealer.
	 * @throws Exception 
	 */
	public void RequestDraw(int numOfCards) throws Exception
	{
		Game.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " requests " + numOfCards +"."));
		Table.dealer.deal(numOfCards, this); 
	}
	
	/***
	 * Discard a specific card from your hand.
	 * @param card to be discarded.
	 */
	public void Discard(Card card)
	{
		hand.cards.remove(card);
		Game.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " discards a card."));
	}
	
	/***
	 * Discard a whole group of cards all at once.
	 * @param cards list of cards to be discarded.
	 */
	public void Discard(LinkedList<Card> cards)
	{
		hand.cards.removeAll(cards);
		Game.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " discards " + cards.size() + " cards."));
	}
	
	/***
	 * Player quits this round and forfeits all bets.
	 * Make sure the player is taken out of the turn rotate
	 * for this game or is consistently skipped.
	 */
	public void Fold()
	{
		hand.cards = null;
		inPlay = false;
		Game.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " has folded."));
	}
}
