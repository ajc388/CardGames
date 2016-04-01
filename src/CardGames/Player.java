package CardGames;
import java.util.LinkedList;

import javax.activity.InvalidActivityException;

import CardGames.LogEntry.Type;

/**
 * @author sudoninja
 */
public class Player {
	public String name;
	public double bank;
	public LinkedList<Card> cards;
	public boolean inPlay;
	
	public Player(String name, double bank)
	{
		this.name = name;
		this.bank = bank;
		this.cards = new LinkedList<Card>();
		this.inPlay = false;
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
		LogEntry e = GameLog.peek(); // grab last betting action
		if (!inPlay)
			throw new InvalidActivityException("Player is not in play.");
		else if ( e == null )
			throw new NullPointerException();
		else if ( e.logType == Type.BET_ACTION )
			throw new InvalidActivityException("Player cannot use the bet method if there is an existing bet.");
		else if (amount <= 0)
			throw new IllegalArgumentException("Player cannot bet less than or equal to no money.");
		else if ( amount > bank )
			throw new IllegalArgumentException("Player does not have enough money to make the bet.");
		else 
		{
			bank -= amount;
			Table.pot += amount;
			GameLog.add(
					LogEntry.Type.BET_ACTION, 
					"Player " + name + " bets " + amount + ".",
					amount);
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
		LogEntry e = GameLog.peek(); // grab last betting action
		if (!inPlay)
			throw new InvalidActivityException("Player is not in play.");
		else if ( e == null )
			throw new NullPointerException();
		else if ( e.logType != LogEntry.Type.BET_ACTION )
			throw new InvalidActivityException("Player can only raise after a bet action.");
		else if (amount <= 0)
			throw new IllegalArgumentException("Player cannot raise less than or equal to no money.");
		else if ( (amount+e.amt) > bank )
			throw new IllegalArgumentException("Player does not have enough money to match and raise.");
		else 
		{
			bank -= amount;
			Table.pot += amount + e.amt;
			GameLog.add(
					LogEntry.Type.BET_ACTION,
					"Player " + name + " matches bet and raises " + amount + ".",
					amount + e.amt);
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
		LogEntry log = GameLog.peek(); // grab last betting action
		if (!inPlay)
			throw new InvalidActivityException("Player is not in play.");
		else if ( log == null )
			throw new NullPointerException();
		else if ( log.logType != LogEntry.Type.BET_ACTION )
			throw new InvalidActivityException("Player can only call after a bet action.");
		else 
		{
			bank -= log.amt;
			Table.pot += log.amt;
			GameLog.add(
					LogEntry.Type.BET_ACTION,
					"Player " + name + " matches the bet for " + log.amt + ".",
					log.amt);
		}
	}
	
	/**
	 * A played can decide not to add anything to the pot using
	 * the check action.
	 * @throws Exception throws exception if there is a bet on the table.
	 */
	public void check() throws Exception
	{
		LogEntry e = GameLog.peek();
		if (!inPlay)
			throw new InvalidActivityException("Player is not in play.");
		else if ( e.amt <= 0 && e.logType == LogEntry.Type.BET_ACTION)
			throw new InvalidActivityException("A player cannot check if there is a bet on the table.");
		else 
		{
			GameLog.add(
					LogEntry.Type.BET_ACTION,
					"Player " + name + " checks.");
		}
	}
	
	/***
	 * Player pays the required ante for the round.
	 * This amount is set by the table.
	 * @throws Exception 
	 */
	public void optIn() throws Exception
	{
		LogEntry e = GameLog.peek();
		if ( e.logType != LogEntry.Type.GAME_START )
			throw new InvalidActivityException("Player cannot buy in unless it's the start of a new round.");
		else 
		{
			inPlay = true;
			bank -= Table.getAnte(); //this could be negative... need to fix that in table
			Table.pot += Table.getAnte();
			GameLog.add(
					LogEntry.Type.GAME_START,
					"Player " + name + " buys in for the round " + Table.getAnte() + ".",
					Table.getAnte());
		}
	}
	
	/***
	 * A player can choose not to pay the ante 
	 * and opt out of the round.
	 * @throws Exception
	 */
	public void optOut() throws Exception
	{
		LogEntry e = GameLog.peek();
		if ( e.logType != LogEntry.Type.GAME_START )
			throw new InvalidActivityException("Player cannot opt out of th eround unless it's the start of a new round.");
		else
		{
			inPlay = false;
			GameLog.add(
					LogEntry.Type.GAME_START,
					"Player " + name + " opts out for the round.");
		}
	}
	
	/***
	 * This player has collected a percentage of the pot.
	 * If the player won everything enter 1.0.
	 * @param winRatio a number greater than 0.0 and less than 1.0
	 * @throws Exception 
	 */
	public void collect(double winRatio) throws Exception
	{
		if (!inPlay)
			throw new InvalidActivityException("Player must be inPlay.");
		if ( winRatio <= 0.0 || winRatio > 1.0 )
			throw new IllegalArgumentException("The win ratio must be between 0 and 1.");
		else
		{
			bank += Table.pot*winRatio;
			Table.pot -= Table.pot*winRatio;
			GameLog.add(
					LogEntry.Type.PLAYER_ACTION,
					"Player " + name + " won the pot for a total of " + Table.pot*winRatio + ".");
		}
	}
	
	
	//=============================================================
	//                       Player Card Actions
	//=============================================================
	/***
	 * A player can prompt the dealer to give him a number of cards.
	 * @param numOfCards to be drawn from deck by dealer.
	 * @throws Exception 
	 */
	public void RequestDraw(int numOfCards) throws Exception
	{
		if (!inPlay)
			throw new InvalidActivityException("Player is not in play.");
		
		Table.dealer.deal(numOfCards, this, false); 
		GameLog.add(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " requests " + numOfCards +".");
	}
	
	/***
	 * Discard a specific card from your hand.
	 * @param card to be discarded.
	 */
	public void Discard(Card card) throws Exception
	{
		if (!inPlay)
			throw new InvalidActivityException("Player is not in play.");
		cards.remove(card);
		GameLog.add(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " discards a card.");
	}
	
	/***
	 * Player quits this round and forfeits all bets.
	 * Make sure the player is taken out of the turn rotation
	 * or is consistently skipped for this game.
	 */
	public void Fold()
	{
		cards = null;
		inPlay = false;
		GameLog.add(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " has folded.");
	}

	/**
	 * Prints the whole set of cards held by the player.
	 */
	public String showCards(String str)
	{
		for (Card c : cards) 
			str += c.toString() + "\n";
		return str;
	}
}
