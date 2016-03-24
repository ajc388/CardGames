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
		Table.log += "Player " + name + " bets " + amount + ".\n";
	}
	
	public void raise(double amount)
	{
		bank -= amount;
		Table.pot += amount;
		Table.log += "Player " + name + " sees bet and raises " + amount + ".\n";
	}
	
	public void collect()
	{
		bank += Table.pot;
		Table.log += "Player " + name + " won the pot for a total of " + Table.pot + ".\n";
		Table.pot = 0;
	}
	
	public void call(double amount)
	{
		bank -= amount;
		Table.pot += amount;
		Table.log += "Player " + name + " matches the bet for " + amount + ".\n";
	}
	
	public void buyIn(double amount)
	{
		inPlay = true;
		bank -= amount;
		Table.pot += amount;
		Table.log += "Player " + name + " buys in for the round " + amount + ".\n";
	}
	
	public void optOut()
	{
		inPlay = false;
		Table.log += "Player " + name + " opts out for the round.\n";
	}
	
	//Actions on Hand
	public void Draw(int numOfCards)
	{
		Table.log += "Player " + name + " requests " + numOfCards +".\n";
		Table.dealer.deal(numOfCards, this);
	}
	
	public void Discard(Card card)
	{
		hand.cards.remove(card);
		Table.log += "Player " + name + " discards a card.\n";
	}
	
	public void Discard(LinkedList<Card> cards)
	{
		hand.cards.removeAll(cards);
		Table.log += "Player " + name + " discards " + cards.size() + " cards.\n";
	}
	
	public void Fold()
	{
		hand.cards = null;
		inPlay = false;
		Table.log += "Player " + name + " has folded.\n";
	}
	
	public void check()
	{
		Table.log += "Player " + name + " checks.\n";
	}
}
