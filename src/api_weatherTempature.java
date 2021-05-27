/*
 * Heejae Yoo
 * hxy172830
 * Semester Project 1: 
 * 1. Designing a program to get weather API to tell weather at a certain city or a zipcode to a user
 * 2. Designing a program to get Top songs API to tell user the top songs and artists in a certain country
 * Input is read from the user in a webchat using PircBot
 */

import java.io.*;
import java.net.*;
import com.google.gson.*;

// city name and zip code must be valid inputs

public class api_weatherTempature {

	private URL url;
	private final JsonObject weather;
	
	api_weatherTempature(String city) throws Exception{
		
		//uses WeatherUnlocked as source for API
		String weatherapiURL = "https://api.openweathermap.org/data/2.5/weather?"; 
		String weatherKEY = "47440f47a4c1792110cfaa72e752cd03";
		String weatherURL;

		// https://api.openweathermap.org/data/2.5/weather?q={city name}

		weatherURL = weatherapiURL + "q=" + city + "&units=metric&appid=" + weatherKEY; //the city from MyBot is used as the cityName/zip code

		try {
			url = new URL(weatherURL); // checks to see if the URL works to pull up the api
		}
		catch(MalformedURLException e) {
			System.err.println("URL failed. Exception: " + e.getMessage());
		}
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET"); //gets JSON
		
		BufferedReader buffread = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
		
		String inputLine;
		StringBuffer lines = new StringBuffer();
		
		while((inputLine = buffread.readLine()) != null) { // reads the JSON until end/null
			lines.append(inputLine); //adds everything to the String inputLine
		}

		weather = parseJsonObject(lines.toString()); //parse the string into the JSON object weather with the method
		
	}
	
// returns obj if its valid json string 
	public static JsonObject parseJsonObject(String json) { 
		JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
		try {
			return obj; // returns the object parsed
		}
		catch (Exception e) { //error gives the message in a different website, gets the "message" from that
			String m = obj.get("message").getAsString();
			System.out.println(m);
			return null; // must return something
		}
	}
	//accessor for object
	public JsonObject accessObject() {
		return weather;
	}
	
// accessors for the object tempatures
	public double getTempature(JsonObject object) {
		return object.getAsJsonObject("main").get("temp").getAsDouble(); // gets tempature
	}
	
	public double getLoTempature(JsonObject object) {
		return object.getAsJsonObject("main").get("temp_min").getAsDouble(); // gets low tempature
	}
	
	public double getHiTempature(JsonObject object) {
		return object.getAsJsonObject("main").get("temp_max").getAsDouble(); // gets high tempature
	}

}