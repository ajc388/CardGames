package CardGames;
import java.util.LinkedList;
import java.util.Scanner;

/***
 * Extend this game class for any Casino Card game of your choice.
 * @author sudoninja (aka ajc388)
 */
public abstract class Game {
	public Scanner sc;
	public boolean anotherGame;
	Table table;
	
	public Game()
	{
		table = new Table();
		anotherGame = true;
		sc = new Scanner(System.in);
	}
	
	/* TODO: implement the whole work flow of the card game. */
	public abstract void run();
	
	/* TODO:
	 * This phase should encapsulate the setup of the game.
	 * During the first round the players should join
	 * the table and pay an ante (if applicable) 
	 * the dealer should then deal an appropriate amount of cards 
	 * to the players hands. If you have a unique deck you will
	 * need to overload this. */
	public void setup() {
		for ( Player p : table.players )
			queryRemovePlayers(p);
		
		queryNewPlayers();
		
		//Some players should opt in or out
		for ( Player p : table.players)
			queryOptInOut(p);
		
		//Dealer shuffles new deck
		Table.dealer.deck = new Deck();
		Table.dealer.shuffle();	
	}

	/* Evaluate all players hands that are still inPlay
	 * and determine who the winners are.
	 * Pays out the pot to the winners. */
	public void close() {
		GameLog.add(LogEntry.Type.GAME_ACTION, "Closing out the game.");
		LinkedList<Player> winners = evaluateCards();
		for (Player p : winners) 
		{
			try {
				p.collect(1.0/winners.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		queryNewGame();
	}
	
	/* TODO: 
	 * Implement how the game evaluates points or ranks hands.
	 * You may have multiple winners, add them to the list.
	 * This implementation is entirely dependent by the game. */
	public abstract LinkedList<Player> evaluateCards();
	
	/***
	 * Ask all players for a bet.
	 * @param players a given round of players
	 */
	protected void bettingRound(LinkedList<Player> players)
	{
		GameLog.add(LogEntry.Type.BET_ACTION, "Start of a betting round");
		for ( Player p : players )
			if ( p.inPlay )
				queryBetAction(p);
	}
	
	
	protected void queryOptInOut(Player p) {
		System.out.println("Player " + p.name + " is in for this round? (IN , OUT)");
		String response = sc.nextLine().trim().toUpperCase();
		try {
			if ( response.equals("IN") )
				p.optIn();
			else if ( response.equals("OUT"))
				p.optOut();
			else
			{
				System.out.println("Player is in.");
				queryOptInOut(p);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void queryBetAction(Player p) 
	{
		try {
			System.out.println("What is your betting action? (CHECK, BET, RAISE, CALL, FOLD");
			String response = sc.nextLine().trim().toUpperCase();
			if ( response.equals("CHECK") ) {
				p.check();
			} else if ( response.equals("BET")) {
				System.out.println("How much do you want to bet?");
				p.bet(sc.nextDouble());
			} else if ( response.equals("RAISE") ) {
				System.out.println("How much do you want to bet?");
				p.raise(sc.nextDouble());
			} else if ( response.equals("CALL") ) {
				p.call();
			} else if ( response.equals("FOLD") ) {
				p.Fold();
			} else {
				queryBetAction(p);
			}
			GameLog.printRecentLog();		
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			queryBetAction(p); //ask again
		}
	}
	
	protected void queryNewGame() 
	{
		System.out.println("Are you ready to play another game? (YES or NO)");
		String response = sc.nextLine().trim().toUpperCase();
		if ( response.equals("YES") ) 
			anotherGame = true;
		else if ( response.equals("NO") )
			anotherGame = false;
		else
		{
			System.out.println("Incorrect response. (YES or NO)");
			queryNewGame();
		}
	}
	

	protected void queryNewPlayers()
	{
		System.out.println("Do you want to create new players? (YES or NO)");
		String response = sc.nextLine().trim().toUpperCase();
		if (response.equals("YES")) {
			System.out.println("Please enter a new player name.");
			String name = sc.nextLine();
			System.out.println("Please enter amount of money in casino bank. ");
			double bank = sc.nextDouble();
			table.playerJoinsTable(new Player(name, bank));
			queryNewPlayers();
		} else if ( response.equals("NO")) {
			return;
		} else {
			System.out.println("Please enter YES or NO.");
			queryNewPlayers();
		}
	}
	
	protected void queryRemovePlayers(Player p)
	{
		System.out.println("Remove " + p.name + " (YES or NO)?");
		String response = sc.nextLine().trim().toUpperCase();
		if ( response.equals("YES") )
			table.playerLeavesTable(p);
		else if ( response.equals("NO") ) 
			return;
		else
		{
			System.out.println("Please enter (YES or NO)");
			queryRemovePlayers(p);
		}
	}
}
