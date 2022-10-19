import java.io.BufferedReader;
class RejoinThread extends Thread
{
	private IRCBot bot;
	private String channel;
	public RejoinThread(IRCBot bot,String channel)
	{
		this.bot=bot;
		this.channel=channel;
	}
	public void run()
	{
		System.out.println("KICK detected from "+channel+" rejoining in 30 seconds");
		try{
		Thread.sleep(30000);
		bot.say("JOIN "+channel);
		}catch(Exception e){System.out.println("Could not rejoin channel because of an exception.");}
	}
}