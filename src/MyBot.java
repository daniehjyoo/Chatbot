/*
 * Heejae Yoo
 * hxy172830
 * Semester Project 1: 
 * 1. Designing a program to get weather API to tell weather at a certain city or a zipcode to a user
 * 2. Designing a program to get Top songs API to tell user the top songs and artists in a certain country
 * Input is read from the user in a webchat using PircBot
 */

import java.util.regex.*;
import org.jibble.pircbot.*;
import com.google.gson.*;

public class MyBot extends PircBot {
    private String city, country;
    private api_weatherTempature api_weather;
    private api_topTracksGeo api_TTG;

   
	public MyBot() {
        this.setName("HeejaeBot");
    }
    
    //method that reads message on the chat
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        
        if (message.equalsIgnoreCase("Hello") || message.equalsIgnoreCase("Hi")) {
        	sendMessage(channel, "Hello " + sender + "! You can ask me a question about the weather or top 5 tracks in a country!");
        }
        
        
        message = message.trim(); //cuts off excessive spaces of the last word
        //Get exact string of country, city, or zipcode
        Pattern weather = Pattern.compile("What is the weather in (.*)", Pattern.CASE_INSENSITIVE);
        Pattern songsCountry = Pattern.compile("What are the top songs in (.*)", Pattern.CASE_INSENSITIVE);
        
        Matcher matchWeather = weather.matcher(message);
        Matcher matchSongs = songsCountry.matcher(message);

     
        if (matchWeather.find()) {
        	city = matchWeather.group(1);
        	try {
        		api_weather = new api_weatherTempature(city);
        		sendMessage(channel, sender + ", the weather in " + city + " is " + api_weather.getTempature(api_weather.accessObject()) + " Celcius"
        				+ ", with a high of " + api_weather.getHiTempature(api_weather.accessObject()) + " Ceclius and a low of " + 
        				api_weather.getLoTempature(api_weather.accessObject())+ " Celcius.");
        	}
        	catch(Exception e){
        		sendMessage(channel, sender + ", sorry that is not the proper input. Try 'What is the weather in city/zipcode");
        		e.printStackTrace();
        	}
        }
        else if (matchSongs.find()) {
        	country = matchSongs.group(1);
        	try {
        		api_TTG = new api_topTracksGeo(country);
        		JsonObject[] songsArr = api_TTG.accessArray();
        		//JsonObject[] artistsArr = api_TTG.accessArray();
        		sendMessage(channel, sender + ", the top 5 songs in " + country + " are:");
        		for (int i = 0; i < 5; i++) {
        			sendMessage(channel, "Song: "  + songsArr[i].get("name").getAsString());
        			sendMessage(channel, "By Artist: " + songsArr[i].get("artist").getAsJsonObject().get("name").getAsString());
        			sendMessage(channel, "Listened by: " + songsArr[i].get("listeners").getAsString() + " users.");
        			if (i<4)
        				sendMessage(channel, " ");
        		}
        	}
        	catch(Exception e) {
        		sendMessage(channel, sender + ", sorry that is not a proper input. 'Try What are the top songs in 'country'");
        		e.printStackTrace();
        	}
        }
      //  else
        //	sendMessage(channel, sender + "No match. Try the format 'What is the weather in' or 'What are the top tracks in '.");

    }
}
