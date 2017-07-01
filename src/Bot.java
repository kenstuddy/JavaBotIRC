/*
 * User: Ken
 * Date: 10/10/2010
 * Time: 10:35 PM
 * 
 */
import java.io.*;

public class Bot
{
    BufferedWriter bw;
    String channel;
    Logger log = new Logger();
    /**
     * Bot class constructor, set the defaults for the output stream and the channel
     * @param writer
     * @param chan
     * @param own
     */
    Bot(BufferedWriter writer, String chan)
    {   
        bw = writer;
        channel = chan;
    }
    
    /**
     * Class method, this is the main part of the bot, it replies to PING's
     * @throws java.io.IOException
     */
    public void pong() throws IOException
    {
        bw.write("PONG " + channel + "\n");
        bw.flush();
		System.out.println("PONG " + channel);
    }
    
    /**
     * General purpose class method for inputting into the channel at hand
     * @param message
     * @throws java.io.IOException
     */
	//to make this like the C# version, just add String channel, in front of String message to make it multichannel,
	//even though this isn't necessary this can be done if wanted
    public void say(String message) throws IOException
    {
        bw.write("PRIVMSG " + channel + " :" + message + "\n");
		log.log("PRIVMSG " + channel + " :" + message);
        bw.flush();
		System.out.println("PRIVMSG " + channel + " :" + message);
    }
    public void notice(String channel, String message) throws IOException 
	{
		bw.write("NOTICE " + channel + " :" + message + "\n");
		log.log("NOTICE " + channel + " :" + message + "\n");
		bw.flush();
		System.out.println("NOTICE " + channel + " :" + message);
	}
    /**
     * Class method, Joins a specified IRC channel and sets this.channel accordingly
     * @param channel
     * @throws java.io.IOException
     */
    public void join(String chan) throws IOException
    {
		if (!chan.contains("#"))
		{
			chan = "#" + chan;
		}
        bw.write("JOIN " + chan + "\n");
        bw.flush();
        System.out.println("JOIN " + chan);
        log.log("JOIN " + chan);
        //we're doing this so that there's no confusion
        //as to which channel the bot is in
        channel = chan;
    }
    
    /**
     * Leave the curent channel
     * @throws java.io.IOException
     */
    public void part() throws IOException
    {
        bw.write("PART " + channel + "\n");
        bw.flush();
		System.out.println("PART " + channel);
		log.log("PART " + channel);
    }
    
    /**
     * Quit the current IRC session
     * @param reason
     * @throws java.io.IOException
     */
    public void quit() throws IOException
    {
        bw.write("QUIT " + channel + "\n");
        bw.flush();
		System.out.println("QUIT " + channel);
		log.log("QUIT " + channel);
    }
    
    /**
     * Class method used to authenticate the Bot with the IRC server
     * @param nick
     * @param address
     * @throws java.io.IOException
     */
    public void login(String nick, String address) throws IOException
    {
        bw.write("NICK " + nick + "\n");
        bw.write("USER " + nick + " " + address + ": Java Bot\n");
		bw.write("JOIN " + channel + "\n");
        bw.write("PRIVMSG NICKSERV :id mypass\n");
        bw.write("MODE " + Main.nick + " :+B\n");
        bw.flush();
		System.out.println("NICK " + nick);
		log.log("NICK " + nick);
		System.out.println("USER " + nick + " " + address + ": Java Bot");
		log.log("USER " + nick + " " + address + ": Java Bot");
		System.out.println("PRIVMSG NICKSERV : id mypass ");
		log.log("PRIVMSG NICKSERV : id mypass ");
		System.out.println( "JOIN " + channel);
		log.log("JOIN " + channel);
		log.log("MODE " + Main.nick + " :+B\n");
		System.out.println("MODE " + Main.nick + " :+B\n");
    }
    
    /**
     * Class method for yelling at users, might not need to use it after all
     * @param message
     * @throws java.io.IOException
     */
    public void yell(String message) throws IOException
    {
        say(message);
    }
    /**
     * Class method for actions
     * @param message
     * @throws java.io.IOException
     */
    public void action(String message) throws IOException
	{
	
		bw.write("PRIVMSG " + channel + " \u0001ACTION " + message + "\u0001" + "\n");
		bw.flush();
		System.out.println("PRIVMSG " + channel + " \u0001ACTION " + message + "\u0001" + "\n");
		log.log("PRIVMSG " + channel + " \u0001ACTION " + message + "\u0001" + "\n");
	}
    /**
     * Class method for changing mode.
     * @param user
     * @param mode
     * @throws java.io.IOException
     */
	public void mode(String user, String mode) throws IOException
	{
		
		bw.write("MODE " + channel + " " + mode + " " + user + "\n");
		System.out.println("MODE " + channel + " " + mode + " " + user);
		log.log("MODE " + channel + " " + mode + " " + user);
		bw.flush();
	}
    public void wiki() throws IOException
    {
        say("Sorry folks, but that feature isn't working just yet.");
    }
}
