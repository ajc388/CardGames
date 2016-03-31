package CardGames;

import java.util.LinkedList;

public class TexasHoldem extends Game {
	private Table table;
	private boolean playAgain;
	
	public TexasHoldem()
	{
		table = new Table("Bob", 1000.0, new Deck(), 5.0);
		playAgain = true;
	}
	
	@Override
	public void run() {
		while ( playAgain )
		{
			setup(); //Setup the whole table				
			dealHoleCards(); //Deal cards to all players in play
			bettingRound(table.players);
			dealFlop();
			bettingRound(table.players);
			dealTurn();
			bettingRound(table.players);
			dealRiver();
			bettingRound(table.players);
			close();
		}
	}

	@Override
	public LinkedList<Player> evaluateCards() {
		return null;
	}
	
	//===============================================
	//			    CARD DEALING PHASES 
	//===============================================
	private void dealHoleCards()
	{
		for ( Player p : table.players )
		{
			if ( p.inPlay )
			{
				try {
					Table.dealer.deal(2, p, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void dealFlop()
	{
		try {
			Table.dealer.deal(3, Table.dealer, true);
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the flop to the community.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dealTurn()
	{
		try {
			Table.dealer.deal(1, Table.dealer, true);
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the turn to the community.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dealRiver()
	{
		try {
			Table.dealer.deal(1, Table.dealer, true);
			GameLog.add(LogEntry.Type.DEALER_ACTION, "The dealer has dealt the river to the community.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
