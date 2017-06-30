/*
 * User: Ken
 * Date: 10/10/2010
 * Time: 10:21 PM
 * 
 */
import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.Date;
import java.util.Random;

public class Main implements Runnable
{   
	static String owner = "ken";
	static String server = "irc.swiftirc.net"; 
    static String nick = "JavabotLOL";
    static String address = "wat";
    static String channel = "#test";
    static int port = 6667;
	Logger log = new Logger();
	public Socket irc;
	public BufferedWriter bw;
	public BufferedReader br;
	public Bot JavaBot;
	public Main() {
	}
    /**
     * constructor for the program
     * @param args
     * @throws java.io.IOException
     */
	public Main(String args[]) {
	
        try {

            //server = "irc.swiftirc.net";
			//nick = "javabotlol";
			//address = "wat";
			//channel = "#test";
			
			//server = args[0];
			//nick = args[1];
			//address = "wat";
			//channel = args[2];
			
            //our socket we're connected with
            irc = new Socket(server, port);
            //out output stream
			bw = new BufferedWriter(new OutputStreamWriter(irc.getOutputStream()));
            //our input stream
            br = new BufferedReader(new InputStreamReader(irc.getInputStream()));
            
            //create a new instance of the JavaBot
            JavaBot = new Bot(bw, channel);

            //authenticate the JavaBot with the server
            JavaBot.login(nick, address);
            
			Thread t = new Thread(this);
			t.start();
			
			//Simple test I wrote in C# to test if multithreading works, it does.
			//We can't use bw.write() to test the multithreading, because it is constantly
			//being used by the thread t and thus, the default nonthread of this constructor
			//has to wait until bw.write() is free and isn't being used/called, whereas println
			//can have as many instances of it as you want, anywhere in the program.
			//while (true)
			//System.out.println("test");
			
        } catch (UnknownHostException e) {
            System.err.println("No such host");
            log.log("No such host");
        } catch (IOException e) {
            System.err.println("There was an error connecting to the host. Is the IRCD server running on this host? If you are running on localhost/127.0.0.1, then make sure the IRCD server is running before you start the bot.");
            log.log("There was an error connecting to the host. Is the IRCD server running on this host? If you are running on localhost/127.0.0.1, then make sure the IRCD server is running before you start the bot.");
        } 
	}
    /**
     * main method of the program
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    { 
		//Pass main the paramater args, we can't use the default construtor because the 
		//Parser class needs the default construtor because parser calls it when
		//it extends main (so we don't need to create a new instance of main class and mess
		//with that when we don't have to), and if we use only one constructor, parser will call 
		//the default construtor, which will create a new irc socket, then call the run method
		//to create a new parser instance and do it all over again. To avoid this, we simply
		//create a paramater constructor main and leave the default one for parser to call blank.
		new Main(args);
		
    }
	public void run() {
		String currLine;
		try {
			//C# note:
			//The following line of code doesn't work in C# because all strings are references in C#, 
			//and you can't use != null for strings in C#, you have to use !stringname.equals(null)
			while((currLine = br.readLine()) != null)
			{
				Parser par = new Parser();
				//The JavaBot object needs to be passed as an argument because we HAVE to use the same bufferedwriter for the Bot class,
				//and also it can't be static or it won't work for some reason.
				par.parseText(currLine, JavaBot);
			}
		} catch (UnknownHostException e) {
            System.err.println("No such host");
            log.log("No such host");
        } catch (IOException e) {
            System.err.println("There was an error connecting to the host. Is the IRCD server running on this host? If you are running on localhost/127.0.0.1, then make sure the IRCD server is running before you start the bot.");
            log.log("There was an error connecting to the host. Is the IRCD server running on this host? If you are running on localhost/127.0.0.1, then make sure the IRCD server is running before you start the bot.");
        } 
	}
}
