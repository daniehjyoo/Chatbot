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

public class api_topTracksGeo {

	private URL url;
	private static JsonObject [] ArrTopTracks;
	
	api_topTracksGeo(String country) throws Exception{		
		String apiURL = "http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=";
		String apiKEY = "e4a3b7c4be5a6520fa62fc041848c73f";
		String lastFmURL = apiURL + country + "&limit=5&api_key=" + apiKEY + "&format=json"; //String country is the input from MyBot
		
		try {
			url = new URL(lastFmURL); // checks to see if the URL works to pull up the api
		}
		catch (MalformedURLException e) {
			System.err.println("URL failed. Exception thrown: " + e.getMessage());
		}
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET"); // gets the JSON from the site
		
		BufferedReader buffread = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String inputLines;
		StringBuffer lines = new StringBuffer();
		
		while((inputLines = buffread.readLine()) != null) { //reads the JSON and adds it to the String inputLines until reaches the end/null
			lines.append(inputLines);
		}

		ArrTopTracks = parseJsonFunction(lines.toString()); // parses the string into the JSONObject with method
	}

	private static JsonObject [] parseJsonFunction(String data) {
		JsonObject object = new JsonParser().parse(data).getAsJsonObject();
		try {
			JsonArray TrackArrays = (JsonArray) new JsonParser().parse(data).getAsJsonObject().get("tracks").getAsJsonObject().get("track");	
			JsonObject [] topTracks = new JsonObject[5];
			
			for (int i =0; i < 5; i++) {
				topTracks[i] = new JsonParser().parse(TrackArrays.get(i)+"").getAsJsonObject(); //gets the items into the object array
			}
			return topTracks; // returns the object
		}
		catch(Exception e) { //error gives the message in a different website, gets the "message" from that 
			String error = object.get("message").getAsString();
			System.out.println(error);
			return null; //must return something 
		}
	}
// accessor for object
	public JsonObject[] accessArray() {
		return ArrTopTracks;
	}
}
