package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CardGames.GameLog;
import CardGames.LogEntry;
import CardGames.Player;
import CardGames.Table;

public class TestPlayerTable {
	private Table table;
	private Player p1 = new Player("p1", 600);
	private Player p2 = new Player("p2", 600);
	private Player p3 = new Player("p3", 600);
	
	@Before
	public void setUp() throws Exception {
		table = new Table();
		Table.betMinimum = 0.0;
		Table.pot = 0.0;
		
		table.playerJoinsTable(p1);
		table.playerJoinsTable(p2);
		table.playerJoinsTable(p3);
		
		p1.inPlay = true;
		p2.inPlay = true;
		p3.inPlay = true;
		
		GameLog.add(LogEntry.Type.BET_ACTION, "Betting round started.");
	}

	@After
	public void tearDown() throws Exception {
		table.playerLeavesTable(p1);
		table.playerLeavesTable(p2);
		table.playerLeavesTable(p3);
		table.players = null;
		GameLog.delete();
		Table.pot = 0.0;
	}

	@Test
	public void testBetChain() throws Exception {
		assertEquals(0.0, Table.pot, 0.01);
		p1.bet(5.0);
		assertEquals(5.0, Table.pot, 0.01);
		p2.bet(10.0);
		assertEquals(20.0, Table.pot, 0.01);
		p3.bet(5.0);
		assertEquals(45.0, Table.pot, 0.01);	
	}
	
	@Test
	public void testBetCallChain() throws Exception {
		assertEquals(0.0, Table.pot, 0.01);
		p1.bet(5.0);
		assertEquals(5.0, Table.pot, 0.01);
		p2.call();
		assertEquals(5.0, p2.recentBet, 0.01);
		assertEquals(10.0, Table.pot, 0.01);
		p3.bet(5.0);
		assertEquals(20.0, Table.pot, 0.01);
	}

	@Test
	public void testCallChain() throws Exception {
		assertEquals(0.0, Table.pot, 0.01);
		p1.call();
		assertEquals(0.0, Table.pot, 0.01);
		p2.call();
		assertEquals(0.0, Table.pot, 0.01);
		p3.call();
		assertEquals(0.0, Table.pot, 0.01);
	}
	
	@Test
	public void testFullBettingRound() throws Exception {
		assertEquals(0.0, Table.pot, 0.01);
		p1.call();
		assertEquals(0.0, Table.pot, 0.01);
		p2.bet(5.0);
		assertEquals(5.0, Table.pot, 0.01);
		p3.call();
		assertEquals(10.0, Table.pot, 0.01);
		
		assertEquals(0.0, p1.recentBet, 0.01);
		p1.call();
		assertEquals(5.0, p1.recentBet, 0.01); 
		assertEquals(15.0, Table.pot, 0.01);

		assertEquals(5.0, p2.recentBet, 0.01);
		p2.call();
		assertEquals(0.0, p2.recentBet, 0.01); 
		assertEquals(15.0, Table.pot, 0.01);
		
		assertEquals(5.0, p3.recentBet, 0.01);
		p3.call();
		assertEquals(0.0, p3.recentBet, 0.01); 
		assertEquals(15.0, Table.pot, 0.01);
	}
	
	
}
