package CardGames;

public class Dealer extends Player {

	public Deck deck;
	
	public Dealer(String name, double bank)
	{
		this(name, bank, new Deck());
	}
	
	public Dealer(String name, double bank, Deck deck)
	{
		super(name, bank);
		this.deck = deck;
	}
	
	/***
	 * Deal a number of cards to a player. This could also be done to the dealer, 
	 * but remember the dealer always handles the deck.
	 * @param numOfCards grab a number of cards from the deck.
	 * @param player the player who should be dealt to.
	 * @throws Exception
	 */
	public void deal(int numOfCards, Player player, boolean faceUp) throws Exception {
		if (numOfCards > deck.cards.size()) 
			throw new Exception("Attempted to draw more cards than in the deck.");
		else 
		{
			for ( int n = 0; n < numOfCards; n++ )
			{
				Card card = deck.cards.pop();
				card.faceUp = faceUp;
				player.cards.add(card);
			}
		}
		GameLog.add(
			LogEntry.Type.DEALER_ACTION,
			"Dealer gives " + player.name + " " + numOfCards + " cards.");
	}
	
	/***
	 * Randomly shuffles the deck.
	 */
	//This is an inefficient O(n^2) shuffling algorithm.
	//Shuffles a number of cards randomly regardless of deck size.
	public void shuffle()
	{
		for(int i=0; i< deck.cards.size(); i++) 
		{
	        int card = (int) (Math.random() * (deck.cards.size()-i));
	        deck.cards.addLast(deck.cards.remove(card));
	    }
		
		GameLog.add(
			LogEntry.Type.DEALER_ACTION,
			"Dealer shuffles the deck.");
	}
	
}
