import java.util.Date;

public class LogEntry 
{
	public Date date;
	public Type logType;
	public String log;
	public double amt;
	
	public LogEntry(Type log, String entry)
	{
		this(log, entry, 0);
	}
	
	public LogEntry(Type log, String entry, double amt)
	{
		this.date = new Date();
		this.logType = log;
		this.log = entry;
		this.amt = amt;
	}
	
	public static enum Type {
		PLAYER_ACTION, DEALER_ACTION, GAME_ACTION
	}
}
