package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CardGames.Player;

public class TestPlayer {

	Player p;
	
	@Before
	public void setUp() throws Exception {
		p = new Player("p1", 9001);
	}

	@After
	public void tearDown() throws Exception {
		p = null;
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
