import java.util.LinkedList;

public class Dealer extends Player {

	private Deck deck;
	
	public Dealer(String name, double bank)
	{
		this(name, bank, new Deck());
	}
	
	public Dealer(String name, double bank, Deck deck)
	{
		super(name, bank);
		this.deck = deck;
	}
	
	public void deal(int numOfCards, Player player) {
		LinkedList<Card> cards = deck.draw(numOfCards);
		player.hand.cards.addAll(cards);
		Table.log += "Dealer gives " + player.name + " " + numOfCards + " cards.\n";
	}
	
	public void shuffle()
	{
		deck.shuffle();
		Table.log += "Dealer shuffles the deck.\n";
	}
}
