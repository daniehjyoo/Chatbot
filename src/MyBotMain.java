/*
 * Heejae Yoo
 * hxy172830
 * Semester Project 1: 
 * 1. Designing a program to get weather API to tell weather at a certain city or a zipcode to a user
 * 2. Designing a program to get Top songs API to tell user the top songs and artists in a certain country
 * Input is read from the user in a webchat using PircBot
 */
import org.jibble.pircbot.*;

public class MyBotMain {
    
    public static void main(String[] args) throws Exception {
        
        // Now start our bot up.
        MyBot bot = new MyBot();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.freenode.net");

        // Join the #pircbot channel.
        bot.joinChannel("#heejaeapibot");
        bot.setMessageDelay(1500);
        
        bot.sendMessage("#heejaeapibot", "Hello, I am here to tell you about the weather in a city/zipcode or top songs in a country!");
    }

}