package CardGames;
import java.util.LinkedList;

/***
 * Extend this game class for any Casino Card game of your choice.
 * @author sudoninja (aka ajc388)
 */
public abstract class Game {
	private Table table;
	public static LinkedList<LogEntry> log; //should be turned into a file

	/* TODO: Make sure you construct the table in the correct way.
	 * The table will automatically build the dealer. It will not
	 * assign players. */
	public Game()
	{
		table = new Table();
	}
	
	/* TODO: implement the whole work flow of the card game. */
	public abstract void run();
	
	/* TODO:
	 * This phase should encapsulate the setup of the game.
	 * During the first round the players should join
	 * the table and pay an ante (if applicable) 
	 * the dealer should then deal an appropriate amount of cards 
	 * to the players hands. */
	public abstract void setup(LinkedList<Player> players);
	
	/* TODO:
	 * Iterate through all players that are inPlay and 
	 * determine what their betting and drawing actions are. */
	public abstract void round();
	
	/* TODO:
	 * Evaluate all players hands that are still inPlay
	 * and determine who the winner is or if the pot should be split. */
	public abstract void closeRound();
	
	/* TODO: 
	 * Implement how the game evaluates points or ranks hands.
	 * This depends entirely on the game. */
	public abstract void evaluateHand();
	
	/***
	 * Print the whole log from the most recent to the oldest entry.
	 */
	private void printLog()
	{
		for ( LogEntry logEntry : log)
			System.out.println( logEntry.date.toString() + " - " + logEntry.log );
	}
	
	/***
	 * Print the most recent action to the console.
	 */
	private void printRecentLog()
	{
		LogEntry recent = log.peek();
		System.out.println( recent.date.toString() + " - " + recent.log);
	}
}
