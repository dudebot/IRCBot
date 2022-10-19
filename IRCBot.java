import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import org.nfunk.jep.JEP;
class IRCBot extends Thread
{
	Socket sock;
	ArrayList<String> channels;
	static double version = 0.8;
	
	//EVALUATOR
	JEP j;
	
	BufferedReader in;
	OutputStreamWriter out;
	
	String botName;
	private long[] lastTimes;
	FunctionHandler fh;//keep track
	//Class[] paramtypes;
	
	
	public synchronized void say(String str) throws IOException //let the call stack handle this
	{
		if(str.contains("\n"))
			return;
		out.write(str+'\n');
		out.write('\n');
		System.out.println(str);
		out.flush();
	}
	
	public synchronized void speak(String msg,String channel) throws IOException
	{
		long time=System.currentTimeMillis();
		if(msg.equals(""))//functions that dont require output return ""
			return;
		boolean stfu=time-lastTimes[0]<10000;//if the oldest message is less than 10 seconds away, then stfu
				
		if(!stfu)
		{
			for(int i=0;i<lastTimes.length-1;i++)lastTimes[i]=lastTimes[i+1];
			lastTimes[lastTimes.length-1]=time;
			say("PRIVMSG "+channel+" :"+msg);
		}
	}
	
	HashMap<String,Long> umadTimes;
	private synchronized void umad(String sender,String channel) throws IOException
	{
		Long last=umadTimes.put(sender,System.currentTimeMillis());
		
		if (last==null||(System.currentTimeMillis()-last)>43200000)
			speak("u mad?",channel);
	}
	public BufferedReader getReader(){return in;}
	
	private boolean checkAuth(String name)
	{
		boolean authorized=false;
		for(int i=0;!authorized&&i<auth.size();i++)
			if(name.equals(auth.get(i)))
					authorized=true;
		return authorized;
	}
	private ArrayList<String> auth = new ArrayList<String>();
	private void authorize(String sender) throws IOException
	{
		speak("you are \"authorized\"",sender);
		auth.add(sender);
		System.out.println(auth.size());
	}
	
	private HashMap<String,ArrayList<String>> subnets = new HashMap<String,ArrayList<String>>();
	private void joinsub(String sender,String subnet_name) throws IOException
	{
		speak("you have joined the "+subnet_name+" network",sender);
		ArrayList<String> sublist = subnets.get(subnet_name);
		if(sublist==null)
		{
			sublist=new ArrayList<String>();
			subnets.put(subnet_name,sublist);
		}
		//hopefully referencing like this actually works
		sublist.add(sender);
		
	}
	private void broadcast(String sender, String broadcast) throws IOException
	{
		{
			for(String s:auth)
				if(!s.equals(sender))
					speak(broadcast,s);
		}
	}
	private void broadcast(String sender, String broadcast,String subnet_name)throws IOException
	{
		ArrayList<String> sublist = subnets.get(subnet_name);
		if(sublist==null)
		{
			speak("no subnet name: "+subnet_name,sender);
			return;
		}
		for(String s:sublist)
			if(!s.equals(sender))
				speak(broadcast,s);
	} 
	
	///handles public messages, from any channel
	private void handlePUBL(String message, String sender, String channel) throws IOException
	{
		String msglower=message.toLowerCase();
		j.parseExpression(message);
		if (!j.hasError())
		{
			try{
			Double d=new Double(Double.parseDouble(j.getValueAsObject().toString()));
			speak(d.toString(),channel);
			return;
			}catch(Exception e){}//if it wasnt a double, dont consider the output
			
			
			//this allows imaginary numbers
			//speak(j.getValueAsObject().toString(),channel);
		}
		if((msglower.contains("fuck")&&!msglower.contains("the fuck"))||
			msglower.contains("dammit")||
			msglower.contains("cunt")||
			msglower.contains("shit"))
				umad(sender,channel);
		//if(message.startsWith("!test"))
		//	say("PRIVMSG "+sender+" \u0001VERSION\u0001");
		else if(message.startsWith("!"))//send to reflective function caller
		{
			int index=message.indexOf(' ');
			
			String command=message.substring(1,(index==-1) ? message.length() : index);
			String params=index==-1 ? "" : message.substring(command.length()+2,message.length());
			speak(fh.exec(command,params),channel);
		}
		
		else if(msglower.indexOf(botName.toLowerCase())!=-1)//the message to the channel contains the bot's name
		{	
			speak(fh.exec("reverse",message),channel);
			//speak(sender+", writing",channel,out);
			//System.out.println("name recognized");
		}
	}
	
	///this is a private message sent to the bot
	private void handlePRIV(String message, String sender) throws IOException
	{
		/*	list of available commands
			AUTH pass
			JOINSUB <subnet_name>
			BC <broadcast>
			BCS <subnet_name> <broadcast>
			VERSION
		*/
		String[] split = message.split(" ");
		
		//if(split.length==2&&split[0]=="AUTH"&&split[1]==data.get("BOTNETPASS"))
		boolean authed = checkAuth(sender);
		if(!authed && split.length==2 && split[0].equals("AUTH") && split[1].equals("fixme"))
			authorize(sender);
			
		else if(authed && split.length==2 && split[0].equals("JOINSUB"))
			joinsub(sender,split[1]);
			
		else if(authed && split.length==2 && split[0].equals("BC"))
			broadcast(sender,split[1]);			
			
		else if(authed && split.length==3&&split[0].equals("BCS"))//subnet broadcast
			broadcast(sender,split[2],split[1]);
			
		else if(message.equals("\u0001VERSION\u0001"))
			say("NOTICE "+sender+ " :\u0001VERSION xchat 2.8.6-2 Windows Vista [Intel/1.60GHz]\u0001");
		else
			speak(fh.exec("zalgo","HE COMES"),sender);
			
	}

	///these are complete lines sent directly to the console
	public void handleCMD(String cmd)//used by CMDListener
	{
		String lower=cmd.toLowerCase();
		
		if(lower.startsWith("reload"))
		{
			try{
				fh.reload();
			}catch(Exception e){e.printStackTrace();}
		}
		else if(lower.startsWith("reset"))
		{
			try{
				fh.reset();
			}catch(Exception e){e.printStackTrace();}
		}
		
		else
		{
			try{
				if(lower.startsWith("nick "))
				{
					Scanner sc=new Scanner(cmd);
					sc.next();
					botName=sc.next();	
				}
				/*if(lower.startsWith("join "))
				{
					Scanner sc=new Scanner(cmd);
					sc.next();
					botName=sc.next();	
				}*/
				out.write(cmd);
				out.write('\n');
				out.flush();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public boolean connected()
	{
		return sock.isConnected();
	}
	
	///constructor
	public IRCBot(String hostName,String botName, String channels)throws ClassNotFoundException 
	{
		lastTimes = new long[3];//heh, last message was in the 70's
		
		j = new JEP();
		j.addStandardConstants();
		j.addStandardFunctions();
		j.addComplex();// idunolol
		
		umadTimes = new HashMap<String,Long>();
		this.botName="";
		sock=null;
		this.channels=new ArrayList<String>();
		
		in=null;
		out=null;
		try{//parse arguements, any failures are the users
			this.botName=botName;
			Scanner sc= new Scanner(channels);
			while(sc.hasNext())
				this.channels.add(sc.next());
			System.out.println(channels);
			
			sock=new Socket(InetAddress.getByName(hostName),6667);
		}catch(UnknownHostException e)
		{
			System.out.println("Unknown host");
		}catch(Exception e)//out of range or socket cant connect
		{
			
			e.printStackTrace();
			return;
		}
		
		
		String line="";
		
		fh=new FunctionHandler(true);//no reason to fail gracefully here, if this fails, everything fails
		//paramtypes={Class.forName("java.lang.String")};
		
		
		//connect the socket and sort out the silly connection junk
		
		try{
			in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out=new OutputStreamWriter(sock.getOutputStream(), "ISO-8859-5");
			//idk wtf is happening
			
			//connected and good stuff, now to get everything settled with the server
			
			
			//out.write(new String("USER "+botName+" * 6 "+botName+"\nNICK "+botName+"\n").getBytes());
			say("USER "+botName+" * 6 "+botName);
			say("NICK "+botName);
			
			boolean registered=false;
			while(in.ready()||!registered)//sometimes arloria likes to ping after you nick, and doesnt allow you to join until you do it
			{
				line=in.readLine();
				System.out.println(line);
				if(line.startsWith("PING"))
				{
					say("PONG "+line.substring(6));//respond
					//System.out.println(new String(line.substring(6)+"\n").getBytes());//show what i just did
				}
				if(line.indexOf("MODE")!=-1)
					registered=true;
				
			}
			
			for(int i=0;i<this.channels.size();i++)
				say("JOIN "+this.channels.get(i));
		}catch(IOException e)
		{
			System.out.println("IO error");
			e.printStackTrace();
			return;
		}
	}

	///take the raw output from the IRC server and dispatch it
	public void handleServerInput(String line)
	{
		try
		{	
			//use an error handler separate from connection (so it can recover)
			//PONG 
			//line=in.readLine();
			if(line==null)//buffered reader returns null if it's at the end of the stream (disconnected)
			{	
				System.out.println("exiting");
				System.exit(1);
			}
			System.out.println(line);
			int spaceIndex=line.indexOf(' ');
			if(line.startsWith("PING"))
				say("PONG"+line.substring(4));
			
			if (line.contains("\u0001"))//*********FIXME
				return;
			
			//System.out.println(line.substring(spaceIndex+1,spaceIndex+8));
			
			if(spaceIndex==-1);//sanity check
			
			//PRIVMSG is not a "private" message, just a header
			else if(line.length()<=spaceIndex+8);
			else if(line.substring(spaceIndex+1,spaceIndex+8).equals("PRIVMSG"))
			{
				String sender=line.substring(1,line.indexOf('!'));
				String message=line.substring(line.indexOf(':',1)+1,line.length());
				String channel=line.substring(spaceIndex+9,line.indexOf(':',1)-1); //needed for multi-channel responces
																				   //however for the case of private messages, this is the bot's name
				
				
				//if(sender.toLowerCase().contains("octo")||sender.contains("bot"))
				//	return;
				//use settings
				
				
				//dispatch handlers for public or private messages
				if(channel.equals(botName))
					handlePRIV(message, sender);
					
				else if(channel.startsWith("#")&&!message.startsWith("\u0001"))
					handlePUBL(message, sender, channel);	
					
				else if(Math.random()<0.001)
					speak(fh.exec("reverse",message),channel);
				
					
					
				//do whatever
				//text event handling
				//markov generator
				
				//System.out.println("<"+sender+"> "+message);
				
			}
			
			//KICK
			else if(line.substring(spaceIndex+1,spaceIndex+5).equals("KICK"))
			{
				//String sender=line.substring(1,line.indexOf('!'));
				int spaceIndex2=line.indexOf(' ',spaceIndex+1);
				int spaceIndex3=line.indexOf(' ',spaceIndex2+1);
				int spaceIndex4=line.indexOf(' ',spaceIndex3+1);
				//System.out.println(spaceIndex+" "+spaceIndex2+" "+spaceIndex3);
				String kickee=line.substring(spaceIndex3+1,spaceIndex4);
				String channel=line.substring(spaceIndex2+1,spaceIndex3);
				if(kickee.equals(botName))
				{
					new RejoinThread(this,channel).start();
				}
					
			}
		}catch(SocketException e)
		{
			e.printStackTrace();
			//fooey, we lost connection, might as well close and let a batch file reload us
			return;
		}catch(IOException e)
		{
			//probably means we disconnected, we should probably close
			System.out.println("Unknown IO error");
			e.printStackTrace();
			return;
		}/*catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();//thats kind of interesting
		}catch(java.lang.reflect.InvocationTargetException e)
		{
			e.printStackTrace();//thats kind of interesting too
		}*/catch(NullPointerException e)
		{
			//try{speak("oh noes! a NullPointer",channel,out);}catch(IOException arrr){return;}//if exception occurs on exception handling, then fuck it
			e.printStackTrace();
		}
		
	}


		
	public static void main(String args[])throws ClassNotFoundException
	{
		IRCBot bot=null;
		try{
			//create sock separately from IRCBot, preferably ServerListener
			bot = new IRCBot(args[0],args[1],args[2]);
		
			CMDListener cmd= new CMDListener(bot);
			cmd.start();
			
			ServerListener serv= new ServerListener(bot);
			serv.start();
		}catch(IndexOutOfBoundsException e)		{System.out.println("Usage: IRCBot [url.to.server] [botName] \"#ch1 #ch2 #ch3\"");}

		
	}
}

