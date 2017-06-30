/*
 * User: Ken
 * Date: 10/10/2010
 * Time: 10:51 PM
 * 
 */
import java.io.*;
public class Logger {
    /**
     * Class method for logging
     * @param message
     * @throws java.io.IOException
     */
	public void log(String message)
	{
	    try {
			//append to file isntead of overwriting it
			PrintWriter out = new PrintWriter(new FileWriter("log.txt", true));
			out.println(message);
			out.close();
		} catch (Exception e) {
		    System.err.println("Error: " + e.getMessage());
	        log("Error: " + e.getMessage());
		    e.printStackTrace();
		}
	}
}