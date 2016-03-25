
public abstract class CardGame {
	private Table table;
	private Player currentPlayer;

	public CardGame()
	{
		table = new Table();
		currentPlayer = Table.dealer;
	}
	
	/***
	 * TODO: implement the whole work flow of the card game.
	 */
	public abstract void run();
	
	private void printLog()
	{
		for ( LogEntry logEntry : table.log)
		{
			System.out.println( logEntry.date.toString() + " - " + logEntry.log );
		}
	}
}
