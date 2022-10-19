import java.io.BufferedReader;
import java.io.InputStreamReader;
class CMDListener extends Thread
{
	private IRCBot bot;
	public CMDListener(IRCBot bot)
	{
		this.bot=bot;
	}
	public void run()
	{
		try{
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			try{
			bot.handleCMD(r.readLine());
			}catch(Exception e){System.out.println("failed to read line from command input");}
		}
		}catch(Exception e)
		{System.out.println("could not listen to command line input");}
	}
}