package CardGames;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/***
 * Extend this game class for any Casino Card game of your choice.
 * @author sudoninja (aka ajc388)
 */
public abstract class Game {
	private Scanner sc;
	public Table table;
	protected boolean playAgain;
	private boolean betOnTable;
	
	public Game()
	{
		table = new Table();
		playAgain = true;
		sc = new Scanner(System.in);
	}
	
	/* TODO: implement the work flow of the card game. */
	public abstract void run();
	

	/**TODO: 
	 * Implement how the game evaluates points for a player.
	 * This implementation is entirely dependent on the game. */
	public abstract LinkedList<Player> rankPlayerCards();	
	
	/** This phase should encapsulate the setup of the game.
	 * During the first round the players should join
	 * the table and pay an ante (if applicable).
	 * The deck will then be constructed and shuffled.
	 * If you have a unique deck or the dealer is a player 
	 * you will need to overload this. */
	public void setup() {
		GameLog.add(LogEntry.Type.GAME_START, "Starting a new round.");
		
		//Query the console to remove players from the list.
		for ( Player p : table.players )
			queryRemovePlayers(p);
		
		//Query
		queryNewPlayers();
		
		//Query the console to opt in or out for the round.
		for ( Player p : table.players)
			queryOptInOut(p);
		
		//Dealer shuffles new deck
		Table.dealer.deck = new Deck();
		Table.dealer.shuffle();
	}

	/** 
	 * Evaluate all players hands that are still in play
	 * to determine who the winners are. Pays out the pot to the winners. */
	public void close() {
		GameLog.add(LogEntry.Type.GAME_ACTION, "Closing out the game.");
		GameLog.printRecentLog();
		
		LinkedList<Player> winners = rankPlayerCards();
		for (Player p : winners) 
		{
			try {
				p.collect(1.0/winners.size());
				GameLog.printRecentLog();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		queryNewGame();
	}
	
	/**
	 * Ask all players for a bet.
	 * @param players is a list of all players in the game. */
	protected void bettingRound(LinkedList<Player> players)
	{
		GameLog.add(LogEntry.Type.BET_ACTION, "Start of a betting round", 0.0);
		
		for ( Player p : players )
			if ( p.inPlay )
				queryBetAction(p);
		
		if ( betOnTable ) 
		{
			for ( Player p : players )
				if ( p.inPlay )
					queryFinalBet(p);
			betOnTable = false;
		} 
	}
	
	//=============================================================
	//               CONSOLE USER INTERFACE
	//=============================================================
	/**
	 * Ask a player if he is in or out for the round.
	 * @param p is a player in the players list. */
	protected void queryOptInOut(Player p) {
		System.out.println("Player " + p.name + " is in for this round? (IN , OUT)");
		String response = sc.nextLine().trim().toUpperCase();
		try {
			if ( response.equals("IN") )
			{
				p.optIn();
				GameLog.printRecentLog();
			} else if ( response.equals("OUT")) {
				p.optOut();
				GameLog.printRecentLog();
			} else
				queryOptInOut(p);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			queryOptInOut(p);
		}
	}
	
	/**
	 * Ask a player what type of bet action he wants
	 * and how much he want to add to the pot.
	 * @param p */
	protected void queryBetAction(Player p) 
	{
		try {
			System.out.println("Player: " + p.name);
			System.out.println("Money in bank: " + p.bank);
			System.out.println("Pot: " + Table.pot);
			System.out.println("Your last bet: " + p.recentBet);
			System.out.println("Current bet on table: " +  GameLog.peek().amt);
			System.out.println(Table.dealer.showCards("Cards on table: "));
			System.out.println(p.showCards("Cards in hole: "));
			System.out.println("What is your betting action? (BET, CALL, FOLD)");
			
			String response = sc.nextLine().trim().toUpperCase();
			if ( response.equals("BET")) {
				betOnTable = true;
				p.bet(queryBetAmount(response)); 
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
	
	private void queryFinalBet(Player p) 
	{
		System.out.println("Player: " + p.name);
		System.out.println("Money in bank: " + p.bank);
		System.out.println("Pot: " + Table.pot);
		System.out.println("Your last bet: " + p.recentBet);
		System.out.println("Current bet on table: " +  GameLog.peek().amt);
		System.out.println("Would you like to (CALL or FOLD)?");
		try {
			String response = sc.nextLine().trim().toUpperCase();
			if ( response.equals("CALL") ) {
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
	
	/**
	 * Ask the console if they want to play another round
	 * of the game. */
	protected void queryNewGame() 
	{
		System.out.println("Are you ready to play another game? (YES or NO)");
		String response = sc.nextLine().trim().toUpperCase();
		if ( response.equals("YES") ) 
			playAgain = true;
		else if ( response.equals("NO") )
			playAgain = false;
		else
		{
			System.out.println("Incorrect response. (YES or NO)");
			queryNewGame();
		}
	}
	
	/**
	 * Ask the console if new players should enter the table. */
	protected void queryNewPlayers()
	{
		System.out.println("Do you want to create new players? (YES or NO)");
		String response = sc.nextLine().trim().toUpperCase();
		if (response.equals("YES")) {
			System.out.println("Please enter a new player name.");
			String name = sc.nextLine();
			double bank = queryBankAmount();
			try {
				table.playerJoinsTable(new Player(name, bank));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			queryNewPlayers();
		} else if ( response.equals("NO")) {
			return;
		} else {
			queryNewPlayers();
		}
	}
	
	private double queryBankAmount()
	{
		try {
			System.out.println("Please enter amount of money in casino bank. (XXX.XX)");
			String strBank = sc.nextLine();
			return Double.parseDouble(strBank);
		} catch ( Exception e ) 
		{
			return queryBankAmount();
		}
	}
	
	private double queryBetAmount(String response)
	{
		try {
			System.out.println("Enter the amount of money to " + response + " (XXX.XX)");
			String strBet = sc.nextLine();
			return Double.parseDouble(strBet);
		} catch ( Exception e ) 
		{
			return queryBankAmount();
		}
	}
	/**
	 * Query the console to remove players from the table.
	 * @param p is the specific player being queried.
	 */
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
			queryRemovePlayers(p);
		}
	}
}
