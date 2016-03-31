package Tests;

import static org.junit.Assert.*;
import java.util.LinkedList;
import javax.activity.InvalidActivityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import CardGames.Game;
import CardGames.LogEntry;
import CardGames.Player;
import CardGames.Table;

public class TestPlayer {
	private Player p;
	private Table table;
	
	@Before
	public void setUp() throws Exception {
		p = new Player("p1", 100);
		Game.log = new LinkedList<LogEntry>();
		table = new Table();
	}

	@After
	public void tearDown() throws Exception {
		p = null;
		table = null;
		Game.log = null;
		Table.pot = 0;
	}

	//=========================================================
	//				 	   TEST BET METHOD
	//=========================================================
	@Test(expected = IllegalArgumentException.class)
	public void testZeroBet() throws Exception
	{
		testBet(0.0);
	}
	
	@Test
	public void testStandardBet() throws Exception
	{
		testBet(20.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeBet() throws Exception
	{
		testBet(-1.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExcessBet() throws Exception
	{
		testBet(101.0);
	}
	
	@Test(expected = InvalidActivityException.class)
	public void testPreExistingBet() throws Exception
	{
		p.bet(20);//should be fine
		assertEquals(20, Table.pot, 0.01);
		p.bet(20); //throws error here
	}
	
	private void testBet(double bet) throws Exception
	{
		assertEquals(0.0, Table.pot, 0.01);
		p.bet(bet);
		assertEquals(bet, Table.pot, 0.01);
			
		LogEntry e = Game.log.pop();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.BET_ACTION, e.logType);
		assertEquals(bet, e.amt, 0.01); //ensure no bet was logged		
	}
	
	//===================================================
	//				 TEST RAISE METHOD
	//===================================================
	@Test(expected = IllegalArgumentException.class)
	public void testZeroRaise() throws Exception
	{
		testRaise(0.0);
	}
	
	@Test
	public void testStandardRaise() throws Exception
	{
		testRaise(20.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeRaise() throws Exception
	{
		testRaise(-1.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExcessRaise() throws Exception
	{
		testRaise(101.0);
	}
	
	@Test(expected = InvalidActivityException.class)
	public void testRaiseNoBetAction() throws Exception
	{
		p.raise(20);
	}
	
	private void testRaise(double raise) throws Exception
	{
		p.bet(20); //adds a bet
		assertEquals(20.0, table.pot, 0.01);
		
		p.raise(raise);
		assertEquals(raise+40.0, table.pot, 0.01);
			
		LogEntry e = Game.log.pop();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.BET_ACTION, e.logType);
		assertEquals(raise+20.0, e.amt, 0.01); //ensure no bet was logged
	}
	
	//===================================================
	//		          TEST CALL METHOD
	//===================================================
	@Test
	public void testCallOnBet() throws Exception
	{
		p.bet(20);
		assertEquals(20.0 , table.pot, 0.0);
		p.call();
		assertEquals(40.0, table.pot, 0.0);
		
		LogEntry e = Game.log.peek();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.BET_ACTION, e.logType);
		assertEquals(20.0, e.amt, 0.01);
	}
	
	@Test
	public void testCallOnRaise() throws Exception
	{
		p.bet(20);
		assertEquals(20.0 , table.pot, 0.01);
		p.raise(20);
		assertEquals(60.0, table.pot, 0.01);
		p.call();
		assertEquals(100.0, table.pot, 0.01);
		
		LogEntry e = Game.log.peek();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.BET_ACTION, e.logType);
		assertEquals(40.0, e.amt, 0.01);
	}
	
	@Test(expected = InvalidActivityException.class)
	public void testCallNoAction() throws Exception
	{
		p.call();
	}
	
	//==============================================
	//				TEST CHECK METHOD
	//==============================================
	@Test(expected = InvalidActivityException.class)
	public void testCheckNoBet() throws Exception
	{
		Game.log.push(new LogEntry(LogEntry.Type.BET_ACTION, "Test"));
		p.check();
	}
	
	@Test
	public void testCheck() throws Exception
	{
		p.bet(10);
		assertEquals(10, table.pot, 0.01);
		p.check();
		assertEquals(10, table.pot, 0.01);
		
		LogEntry e = Game.log.peek();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.BET_ACTION, e.logType);
		assertEquals(0.0, e.amt, 0.01);
	}
	
	//==============================================
	//				OPT IN AND OPT OUT
	//==============================================
	@Test
	public void testOptInCorrectPhase() throws Exception 
	{
		Game.log.push(new LogEntry(LogEntry.Type.GAME_START, "Initialized for test."));
		table.setAnte(5.0);
		p.inPlay = false;
		assertFalse(p.inPlay);
		
		p.optIn();
		assertEquals(5.0, table.pot, 0.01);
		assertTrue(p.inPlay);
		
		LogEntry e = Game.log.peek();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.GAME_START, e.logType);
		assertEquals(5, e.amt, 0.01);
	}
	
	@Test(expected = InvalidActivityException.class)
	public void testOptInNotGameStart() throws Exception
	{
		p.optIn();
	}
	
	public void testOptOut() throws Exception
	{
		Game.log.push(new LogEntry(LogEntry.Type.GAME_START, "Initialized for test."));
		p.inPlay = true;
		assertTrue(p.inPlay);
		
		p.optOut();
		assertFalse(p.inPlay);
		
		LogEntry e = Game.log.peek();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.GAME_START, e.logType);
	}
}
