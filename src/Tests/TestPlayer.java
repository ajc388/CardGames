package Tests;

import static org.junit.Assert.*;
import javax.activity.InvalidActivityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import CardGames.GameLog;
import CardGames.LogEntry;
import CardGames.Player;
import CardGames.Table;
import CardGames.LogEntry.Type;

public class TestPlayer {
	private Player p;
	
	@Before
	public void setUp() throws Exception {
		p = new Player("p1", 100);
		p.inPlay = true;
		Table.betMinimum = 0.0;
		GameLog.add(LogEntry.Type.BET_ACTION, "Initialized.");
	}

	@After
	public void tearDown() throws Exception {
		p = null;
		Table.pot = 0;
		GameLog.delete();
	}

	//=========================================================
	//				 	   TEST BET METHOD
	//=========================================================
	public void testMinBet() throws Exception
	{
		testBet(Table.betMinimum);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMinBetEdge() throws Exception
	{
		testBet(Table.betMinimum-1);
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
	
	@Test
	public void testPreExistingBet() throws Exception
	{
		p.bet(20);//should be fine
		assertEquals(20, Table.pot, 0.01);
		p.bet(20); //throws error here
		assertEquals(60, Table.pot, 0.01); //matches previous bet and raises
	}
	
	private void testBet(double bet) throws Exception
	{
		assertEquals(0.0, Table.pot, 0.01);
		p.bet(bet);
		assertEquals(bet, Table.pot, 0.01);
			
		LogEntry e = GameLog.pop();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.BET_ACTION, e.logType);
		assertEquals(bet, e.amt, 0.01); //ensure no bet was logged		
	}
	
	//==============================================
	//				OPT IN AND OPT OUT
	//==============================================
	@Test
	public void testOptInCorrectPhase() throws Exception 
	{
		GameLog.add(LogEntry.Type.GAME_START, "Initialized for test.");
		Table.setAnte(5.0);
		p.inPlay = false;
		assertFalse(p.inPlay);
		
		p.optIn();
		assertEquals(5.0, Table.pot, 0.01);
		assertTrue(p.inPlay);
		
		LogEntry e = GameLog.peek();
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
		GameLog.add(LogEntry.Type.GAME_START, "Initialized for test.");
		p.inPlay = true;
		assertTrue(p.inPlay);
		
		p.optOut();
		assertFalse(p.inPlay);
		
		LogEntry e = GameLog.peek();
		assertNotNull(e.date);
		assertEquals(LogEntry.Type.GAME_START, e.logType);
	}
	
	//==============================================
	//				TEST COLLECT
	//==============================================
	@Test(expected = IllegalArgumentException.class)
	public void testRatioTooLow() throws Exception
	{
		testWinRatio(0.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRatioTooMuch() throws Exception
	{
		testWinRatio(1.01);
	}
	
	@Test
	public void testRatioAll() throws Exception
	{
		testWinRatio(1.0);
	}
	
	private void testWinRatio(double ratio) throws Exception
	{
		int initialPot = 100;
		Table.pot = initialPot;
		
		p.collect(ratio);
		assertEquals(initialPot*ratio+100 , p.bank, 0.01);
		assertEquals(initialPot - initialPot*ratio , Table.pot, 0.01);
	}
	
}
