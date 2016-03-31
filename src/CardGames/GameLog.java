package CardGames;

import java.util.LinkedList;

public class GameLog {
	private static LinkedList<LogEntry> log = new LinkedList<LogEntry>();
	
	/**
	 * Add a log entry.
	 * @param logType
	 * @param entry
	 */
	public static void add(LogEntry.Type logType, String entry) 
	{
		log.push(new LogEntry(logType, entry));
	}
	
	/**
	 * Add a log entry.
	 * @param logType
	 * @param entry
	 * @param amt
	 */
	public static void add(LogEntry.Type logType, String entry, double amt) 
	{
		log.push(new LogEntry(logType, entry, amt));
	}
	
	public static LogEntry peek()
	{
		return log.peek();
	}
	
	public static LogEntry pop()
	{
		return log.pop();
	}
	
	public static void delete()
	{
		log = new LinkedList<LogEntry>();
	}
	
	/***
	 * Write gamelog to file.
	 * Not intended to save the state of the game...
	 * TODO: implement
	 */
	public static void save()
	{
		
	}
	
	/***
	 * Print the whole log from the most recent to the oldest entry.
	 */
	public static void printLog()
	{
		for ( LogEntry logEntry : log)
			System.out.println( logEntry.date.toString() + " - " + logEntry.log );
	}
	
	/***
	 * Print the most recent action to the console.
	 */
	public static void printRecentLog()
	{
		LogEntry recent = log.peek();
		System.out.println( recent.date.toString() + " - " + recent.log);
	}
}
