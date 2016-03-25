import java.util.LinkedList;

public class Table {
	public static Dealer dealer;
	public LinkedList<Player> players;
	public static double pot;
	public static LinkedList<LogEntry> log; //should be turned into a file
	
	public Table()
	{
		this("Dealer", 90001, new Deck());
	}
	
	public Table(Deck deck)
	{
		this("Dealer", 90001, deck);
	}
	
	public Table(String name, double bank, Deck deck)
	{
		dealer = new Dealer(name, bank, deck);
		this.players.add(dealer);
		pot = 0;
		log = new LinkedList<LogEntry>();
		log.push(new LogEntry(
				LogEntry.Type.GAME_ACTION, 
				"A new table has been formed with dealer " + dealer.name + "."));
	}
	
	public void newDealer(String name, double bank, Deck deck)
	{
		dealer = new Dealer(name, bank, deck);
		log.push(new LogEntry(
				LogEntry.Type.DEALER_ACTION,
				"A new dealer " + name + " is running the table."));
	}
	
	public void playerJoinsTable(Player p)
	{
		players.add(p);
		log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"A new player " + p.name + " joins the table."));
	}
	
	public void playerLeavesTable(Player p)
	{
		players.remove(p);
		log.push(new LogEntry(
				LogEntry.Type.PLAYER_ACTION,
				"Player " + p.name + " has left the table."));
	}
}
