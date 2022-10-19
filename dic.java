import java.io.*;
import java.net.*;
public class dic
{
	public static void main(String[] args) throws IOException
	{
		String cmd=args[0];
		//cmd=cmd.substring(0,cmd.indexOf(" "));//cant have spaces
				
		Socket sock=new Socket();
		System.out.println(2);
		sock.connect(new InetSocketAddress("dict.org",2628));	
		System.out.println(4);
		String data="DEFINE wn "+cmd+"\r\nQUIT\r\n";
		sock.getOutputStream().write(data.getBytes());
		BufferedReader in= new BufferedReader(new InputStreamReader(sock.getInputStream()));

		System.out.println(5);
		in.readLine();
		String line = in.readLine();
		if(line.startsWith("552"))
			return;// "No definitions for "+cmd;
		//well it has to be 150ish anyways
		
		int defCount=Integer.parseInt(line.substring(4,line.indexOf(" ",4)));
		System.out.println(defCount);
		in.readLine();//151 reply
		System.out.println(6);
		in.readLine();//repeat the word again
		boolean done=false;
		String result=in.readLine().substring(6);//5 spaces GONE
		while(!done)
		{
			line=in.readLine();
			if(line.startsWith("          "))//10 spaces (continuation of first definition)
				result+=line.substring(10); //keep one of the spaces, it's supposed to be 11
			else
				done=true;
		}
		System.out.println(result);
	}
}