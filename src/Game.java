import java.util.LinkedList;

public abstract class Game {
	private Table table;
	private Player currentPlayer;
	public static LinkedList<LogEntry> log; //should be turned into a file
	
	public Game()
	{
		table = new Table();
		log = new LinkedList<LogEntry>();
		currentPlayer = Table.dealer;
	}
	
	/***
	 * TODO: implement the whole work flow of the card game.
	 */
	public abstract void run();
	
	/***
	 * TODO: implement how the game evaluates points and hands.
	 * This depends entirely on the game.
	 */
	public abstract void evaluateHand();
	
	private void printLog()
	{
		for ( LogEntry logEntry : log)
			System.out.println( logEntry.date.toString() + " - " + logEntry.log );
	}
}
