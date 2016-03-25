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
		Game.log.push(new LogEntry(
				LogEntry.Type.DEALER_ACTION,
				"Dealer gives " + player.name + " " + numOfCards + " cards."));
	}
	
	public void shuffle()
	{
		deck.shuffle();
		Game.log.push(new LogEntry(
				LogEntry.Type.DEALER_ACTION,
				"Dealer shuffles the deck."));
	}
}
