import java.util.LinkedList;

/**
 * @author sudoninja
 *
 */
public class Player {
	public String name;
	private double bank;
	protected Hand hand;
	public boolean inPlay;
	
	public Player(String name, double bank)
	{
		this.name = name;
		this.bank = bank;
		this.hand = new Hand();
		this.inPlay = true;
	}
	
	//Gambling Actions
	public void bet(double amount)
	{
		bank -= amount;
		Table.pot += amount;
		Table.log.push(new LogEntry( 
				LogEntry.Type.PLAYER_ACTION, 
				"Player " + name + " bets " + amount + ".",
				amount));
	}
	
	public void raise(double amount)
	{
		LogEntry e = Table.log.peek(); // grab last betting action
		bank -= amount + e.amt;
		Table.pot += amount;
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " matches bet and raises " + amount + ".",
				amount));
	}
	
	public void collect()
	{
		bank += Table.pot;
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " won the pot for a total of " + Table.pot + "."));
		Table.pot = 0;
	}
	
	public void call(double amount)
	{
		bank -= amount;
		Table.pot += amount;
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " matches the bet for " + amount + ".",
				amount));
	}
	
	public void buyIn(double amount)
	{
		inPlay = true;
		bank -= amount;
		Table.pot += amount;
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " buys in for the round " + amount + ".",
				amount));
	}
	
	public void optOut()
	{
		inPlay = false;
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " opts out for the round."));
	}
	
	//Actions on Hand
	public void Draw(int numOfCards)
	{
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " requests " + numOfCards +"."));
		Table.dealer.deal(numOfCards, this);
	}
	
	public void Discard(Card card)
	{
		hand.cards.remove(card);
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " discards a card."));
	}
	
	public void Discard(LinkedList<Card> cards)
	{
		hand.cards.removeAll(cards);
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " discards " + cards.size() + " cards."));
	}
	
	public void Fold()
	{
		hand.cards = null;
		inPlay = false;
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " has folded."));
	}
	
	public void check()
	{
		Table.log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + name + " checks."));
	}
}
