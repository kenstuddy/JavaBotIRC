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
     * Class method, this is used for sending messages
     * @throws java.io.IOException
     */
    public void sendMessage(String message) throws IOException
    {
        bw.write(message);
        log.log(message);
        System.out.println(message);
        bw.flush();
    }
    
    /**
     * Class method, this is the main part of the bot, it replies to PING's
     * @throws java.io.IOException
     */
    public void pong() throws IOException
    {
        sendMessage("PONG " + channel + "\n");
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
        sendMessage("PRIVMSG " + channel + " :" + message + "\n");
    }
    public void notice(String channel, String message) throws IOException
    {
        sendMessage("NOTICE " + channel + " :" + message + "\n");
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
        sendMessage("JOIN " + chan + "\n");
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
        sendMessage("PART " + channel + "\n");
    }

    /**
     * Quit the current IRC session
     * @param reason
     * @throws java.io.IOException
     */
    public void quit() throws IOException
    {
        sendMessage("QUIT " + channel + "\n");
    }

    /**
     * Class method used to authenticate the Bot with the IRC server
     * @param nick
     * @param address
     * @throws java.io.IOException
     */
    public void login(String nick, String address) throws IOException
    {
        sendMessage("NICK " + nick + "\n");
        sendMessage("USER " + nick + " " + address + ": Java Bot\n");
        sendMessage("JOIN " + channel + "\n");
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

        sendMessage("PRIVMSG " + channel + " \u0001ACTION " + message + "\u0001" + "\n");
    }
    /**
     * Class method for changing mode.
     * @param user
     * @param mode
     * @throws java.io.IOException
     */
    public void mode(String user, String mode) throws IOException
    {

        sendMessage("MODE " + channel + " " + mode + " " + user + "\n");
    }
    public void wiki() throws IOException
    {
        say("Sorry folks, but that feature isn't working just yet.");
    }
}
