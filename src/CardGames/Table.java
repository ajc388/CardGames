package CardGames;
import java.util.LinkedList;

/***
 * The table is the central part of the Casino Card game.
 * Some variables may be set here as a configuration for 
 * the whole game.
 * @author sudoninja
 *
 */
public class Table {
	public static Dealer dealer;
	public LinkedList<Player> players; //should include the dealer
	public static double pot;
	private static double ante;
	
	public Table()
	{
		this("Dealer", 90001, new Deck(), 0);
	}

	public Table(String name, double bank, Deck deck, double ante)
	{
		dealer = new Dealer(name, bank, deck);
		players = new LinkedList<Player>();
		Table.pot = 0;
		setAnte(ante);
		GameLog.add(
			LogEntry.Type.GAME_ACTION, 
			"A new table has been formed by dealer " + dealer.name + ".");
	}
	
	public void newDealer(String name, double bank, Deck deck)
	{
		players.remove(dealer);
		dealer = new Dealer(name, bank, deck);
		players.add(dealer);
		dealer.shuffle();
		GameLog.add(
			LogEntry.Type.GAME_ACTION,
			"A new dealer " + name + " is running the table.");
	}
	
	public void playerJoinsTable(Player p)
	{
		players.add(p);
		GameLog.add(
				LogEntry.Type.GAME_ACTION,
				"A new player " + p.name + " joins the table.");
	}
	
	public void playerLeavesTable(Player p)
	{
		players.remove(p);
		GameLog.add(
				LogEntry.Type.GAME_ACTION,
				"Player " + p.name + " has left the table.");
	}
	
	public static void setAnte(double newAnte)
	{
		if ( newAnte < 0 )
			throw new IllegalArgumentException("Ante must be a positive or zero.");
		ante = newAnte;
	} 
	
	public static double getAnte()
	{
		return ante;
	}
}
