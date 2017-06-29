/*
 * User: Ken
 * Date: 10/10/2010
 * Time: 10:48 PM
 * 
 */
import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.Date;
import java.util.Random;
public class Parser extends Main 
{
	Logger log = new Logger();
	public void parseText(String currLine, Bot JavaBot)
	{
		try {
			System.out.println(currLine);
			log.log(currLine);
			//token[4] is the first argument that comes after the command name
			//e.g. if someone types !join #test, token[3] would be !join, token[4] would be #test.
            //but since token[3] will always be the same for the same command (e.g. !join), regex should be used instead for it
			//whereas token[4] can be different any time for the same exact command so all commands should use token[4]
			//without using regex.
			String[] token = currLine.split( " " );
            //compile a regex to check and see if the person calling commands is the owner
			//ownership can just be used the the myuser variable, but ownership works fine like this ATM, it can be changed fairly easily to the second wy
			//in the future, if needbe.
			//This code and the logic behind it (for the !op and !deop etc) should work in C# with a bit of tweaking, 
			//like for example instead of onwership.find() it should be if (nickn == "ken"), etc.
            Pattern checkOwner = Pattern.compile( "^:"+owner, Pattern.CASE_INSENSITIVE );
            Matcher ownership = checkOwner.matcher( currLine );
            String myuser = "";
			String p4 = "";
			if (token[0].startsWith(":") && token[0].indexOf("!") > 0 && token[0].indexOf("@") > 0 && token.length > 2) {
				String usern = token[0].substring(1);
				myuser = usern.substring(0, usern.indexOf("!"));
			}
			if (token.length > 4) {
				p4 = token[4];
			}
            //constantly check for PING's, if the bot sees one, it replies with a PONG
            Pattern pingRegex = Pattern.compile( "^PING", Pattern.CASE_INSENSITIVE );
            Matcher ping = pingRegex.matcher( currLine );
            if( ping.find() )
            {
                JavaBot.pong();
            }
                
			//check if a user is trying to pull a fast one on us (i.e. they are PMing/noticing the bot and not using commands in the channel)
			//the c# version of this bot handles this different by handling it per command in the commands if statement, but this way works too by having a block of commands inside
			//of a main if statement that have to be said in a channel, and then there's your ping type of commands that do not have to be and should not be
			//said in a channel and should run regardless of whether the bot is in a channel or anything like that so it doesn't ping out.
            //C#'s comment: "Check the bot has a command sent from a channel and not from a user for every nonping command, check each command for owner seperately if needed."
            if (currLine.contains("PRIVMSG " + JavaBot.channel + " :"))
			{
			
				if (currLine.contains("!op") && ownership.find() && !(currLine.contains("!deop")))
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"+o");
					}
					else {
						JavaBot.mode(p4,"+o");
					}
				}
				if (currLine.contains("!deop") && ownership.find())
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"-o");
					}
					else {
						JavaBot.mode(p4,"-o");
					}
				}
				if (currLine.contains("!halfop") && ownership.find() && !(currLine.contains("!dehalfop")))
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"+h");
					}
					else {
						JavaBot.mode(p4,"+h");
					}
				}
				if (currLine.contains("!dehalfop") && ownership.find())
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"-h");
					}
					else {
						JavaBot.mode(p4,"-h");
					}
				}
				if (currLine.contains("!voice") && ownership.find() && !(currLine.contains("!devoice")))
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"+v");
					}
					else {
						JavaBot.mode(p4,"+v");
					}
				}
				if (currLine.contains("!devoice") && ownership.find())
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"-v");
					}
					else {
						JavaBot.mode(p4,"-v");
					}
				}
				if (currLine.contains("!protect") && ownership.find() && !(currLine.contains("!deprotect")))
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"+a");
					}
					else {
						JavaBot.mode(p4,"+a");
					}
				}
				if (currLine.contains("!deprotect") && ownership.find())
				{
					if (p4 == null || p4 == "") {
						JavaBot.mode(myuser,"-a");
					}
					else {
						JavaBot.mode(p4,"-a");
					}
				}
				if(currLine.contains("!exit"))
				{
					if (ownership.find())
					{
						JavaBot.quit();
					}
					else {
						JavaBot.notice(myuser,"You aren't allowed to do that.");
					}
					
				}
					

				if(currLine.contains("!act") && ownership.find())
				{
					String message = "";
					for (int i = 4; i < token.length; i++) {
						message += token[i] + " ";
					}
					JavaBot.action(message);
				}
				//parts one room and joins another, gives a nice little going away speech as well
				if( currLine.contains("!join") && ownership.find() && token.length > 4)
				{
					if (!token[4].contains("#"))
					{
						token[4] = "#" + token[4];
					}
					JavaBot.say( "I'm going to go over to " + token[4] + " and see what they're up to over there. Cya." );
					JavaBot.part();
					channel = token[4];
					JavaBot.join( channel );
					JavaBot.say( "Hey guys!" );
				}
					
				//we should be polite every now and then, this introduces the bot
				if( currLine.contains("!sayhi") && ownership.find() )
				{
					JavaBot.say( "Hi, I'm JavaBot!" );
				}
				if (currLine.contains("!sup"))
				{
                    JavaBot.say("sup " + myuser);
				}
				if (currLine.contains("!say") && ownership.find() && (!currLine.contains("!sayhi")))
				{
					if (token.length < 5)
					{
						JavaBot.say("You must type something for me to say!");
					}
					else
					{
						String message = "";
						for (int x = 4;x<token.length;x++)
						{
							message += token[x] + " ";
						}
						JavaBot.say(message);
					}
					
				}
				//more or less a PoC, just returns the current date/time to the IRC channel
				if( currLine.contains("!time"))
				{
					Date d = new Date();
					String message = "The date is " + d ;
					JavaBot.say( message );
				}
		    }
			else
			{
                System.out.println("Someone tried hacking me I think.");
				//this should not happen
			}
		}
		catch (IOException e)
		{
			System.err.println( "There was an error connecting to the host. Is the IRCD server running on this host? If you are running on localhost/127.0.0.1, then make sure the IRCD server is running before you start the bot." );
			log.log( "There was an error connecting to the host. Is the IRCD server running on this host? If you are running on localhost/127.0.0.1, then make sure the IRCD server is running before you start the bot." );
		}	
	}
}