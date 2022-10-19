import java.io.BufferedReader;
class ServerListener extends Thread
{
	private IRCBot bot;
	public ServerListener(IRCBot bot)
	{
		this.bot=bot;
	}
	public void run()
	{
		BufferedReader r = bot.getReader();
		while(true)
		{	try{
			bot.handleServerInput(r.readLine());
			}catch(Exception e)
			{
				e.printStackTrace();
				//check if still active
				if(!bot.connected())
					System.exit(-1);
			}
		}
	
	}
}