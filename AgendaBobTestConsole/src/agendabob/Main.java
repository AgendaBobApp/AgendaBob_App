package agendabob;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.lang.reflect.Type;

public class Main {
	private static final String extension = "agendabob";
	private static final String BASE_URL = "http://localhost:8080/agendabob";
	private static final String ENC = "UTF-8";
	public static void main(String[] args) throws Exception {
		System.out.println("Creating New Users");
		String usr1 = createSimulatedGoogleID();
		String usr2 = createSimulatedGoogleID();
		System.out.println("User 1: " +usr1);
		System.out.println("User 2: " +usr2);
		Poll p1 = new Poll(usr1, "First Poll", "Created by First User", null,null);
		String receivedPoll1 = postPoll(p1);
		Poll p2 = new Poll(usr2, "Second Poll", "Created by Second User", null,null);
		String receivedPoll2 = postPoll(p2);
//		Poll p1a = new Poll(usr1, "First Second Poll", "Created by First User", null,null);
//		String receivedPoll1a = postPoll(p1a);
//		Poll p1b = new Poll(usr1, "First Poll", "Created by First User", null,null);
//		String receivedPoll1b = postPoll(p1b);
		
		System.out.println("Key: "+receivedPoll1);
//		System.out.println("Key: "+receivedPoll1a);
//		System.out.println("Key: "+receivedPoll1b);
		System.out.println("Key: "+receivedPoll2);
		receivedPoll1 = receivedPoll1.replaceAll("\\D+","");
//		receivedPoll1a = receivedPoll1a.replaceAll("\\D+","");
//		receivedPoll1b = receivedPoll1b.replaceAll("\\D+","");
//		receivedPoll2 = receivedPoll2.replaceAll("\\D+","");
		System.out.println("Key: "+receivedPoll1);
//		System.out.println("Key: "+receivedPoll1a);
//		System.out.println("Key: "+receivedPoll1b);
		System.out.println("Key: "+receivedPoll2);
		
		System.out.print("Sleeping while database works.");
		for (int i = 0; i < 5; i++){
			Thread.sleep(1000);
			System.out.print(".");
		}
		System.out.println("\nWaiting complete\n");
		System.err.println("Getting Poll from server.");
		
//		Update Poll 
		updatePoll(usr2, receivedPoll1, p2);
		AddUserToPoll(usr2, receivedPoll1);
		updatePoll(usr2, receivedPoll1, p2);
		
//		Add User to Poll
//		AddUserToPoll(usr2, receivedPoll1);
//		System.out.print("Sleeping while database works.");
//		for (int i = 0; i < 2; i++){
//			Thread.sleep(1000);
//			System.out.print(".");
//		}
//		System.out.println("\nWaiting complete\n");
		
//		Remove User from Poll
//		RemoveUserFromPoll(usr1, receivedPoll1);
//		System.out.print("Sleeping while database works.");
//		for (int i = 0; i < 2; i++){
//			Thread.sleep(1000);
//			System.out.print(".");
//		}
//		System.out.println("\nWaiting complete\n");
//		RemoveUserFromPoll(usr2, receivedPoll1);
//		System.out.print("Sleeping while database works.");
//		for (int i = 0; i < 2; i++){
//			Thread.sleep(1000);
//			System.out.print(".");
//		}
//		System.out.println("\nWaiting complete\n");
//		purgePollsIfEmpty();
		
//		Get Poll by ID key
//		Poll gotPoll = receivePoll(receivedPoll1);
//		if (gotPoll != null){
//			System.out.println("Poll: \"" + gotPoll.getTitle() + "\" from User \"" + gotPoll.getCreator() + "\"");
//		}
//		else{
//			System.err.println("Poll is NULL");
//		}
		
//		Get from user
//		List<Poll> pollsFromServer1 = receivePolls(usr1);
//		for (Poll p : pollsFromServer1){
//			System.out.println("Poll: \"" + p.getTitle() + "\" from p.getCreator() \"" + usr1 + "\"");
//		}
//		List<Poll> pollsFromServer2 = receivePolls(usr2);
//		for (Poll p : pollsFromServer2){
//			System.err.println("Poll: \"" + p.getTitle() + "\" from p.getCreator() \"" + usr1 + "\"");
//		}
		
//		Get all Polls
//		List<Poll> pollsFromServer = receiveAllPolls();
//		for (Poll p : pollsFromServer){
//			System.err.println("Poll: \"" + p.getTitle() + "\" from User \"" + p.getCreator() + "\"");
//		}
		
	}
	private static String createSimulatedGoogleID(){
		Random r = new Random();
		String GoogleId = "";
		for(int i = 0; i < 32; i++){
			switch(r.nextInt(16)){
			case 0: GoogleId += "0"; break;
			case 1: GoogleId += "1"; break;
			case 2: GoogleId += "2"; break;
			case 3: GoogleId += "3"; break;
			case 4: GoogleId += "4"; break;
			case 5: GoogleId += "5"; break;
			case 6: GoogleId += "6"; break;
			case 7: GoogleId += "7"; break;
			case 8: GoogleId += "8"; break;
			case 9: GoogleId += "9"; break;
			case 10: GoogleId += "A"; break;
			case 11: GoogleId += "B"; break;
			case 12: GoogleId += "C"; break;
			case 13: GoogleId += "D"; break;
			case 14: GoogleId += "E"; break;
			case 15: GoogleId += "F"; break;
			}
		}
		return GoogleId;
	}
	private static String postPoll(Poll p){
		System.out.println("Creating Poll: " + p.getTitle() + "");
		String key = "";
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "createPoll");
			post.addFormField("username", p.getCreator());
			Gson gson = new Gson();
			String json = gson.toJson( p );
			post.addFormField("poll", json);
			key = post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return key;
	}
	private static Poll receivePoll(String key){
		System.err.println("Getting Poll by Key: " + key + "");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "getPollByKey");
			post.addFormField("key", key);
			json = post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
		if (json != null){
			System.err.println(json);
			Poll poll = new Gson().fromJson(json, Poll.class);
			if(poll.getCreator().equals("")){
				return null;
			}
			return poll;
		}
		return null;
	}
	private static Poll updatePoll(String user, String key, Poll poll){
		System.err.println("Getting Poll by Key: " + key + "");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "updatePollKey");
			post.addFormField("key", key);
			post.addFormField("username", user);
			Gson gson = new Gson();
			String jsonOut = gson.toJson( poll );
			post.addFormField("poll", jsonOut);
			json = post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
//		if (json != null){
//			System.err.println(json);
//			Poll poll = new Gson().fromJson(json, Poll.class);
//			if(poll.getCreator().equals("")){
//				return null;
//			}
//			return poll;
//		}
		return null;
	}
	private static Poll AddUserToPoll(String user, String key){
		System.err.println("Getting Poll by Key: " + key + "");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "addUser");
			post.addFormField("key", key);
			post.addFormField("username", user);
			json = post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
//		if (json != null){
//			System.err.println(json);
//			Poll poll = new Gson().fromJson(json, Poll.class);
//			if(poll.getCreator().equals("")){
//				return null;
//			}
//			return poll;
//		}
		return null;
	}
	private static Poll RemoveUserFromPoll(String user, String key){
		System.err.println("Getting Poll by Key: " + key + "");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "removeUser");
			post.addFormField("key", key);
			post.addFormField("username", user);
			json = post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
//		if (json != null){
//			System.err.println(json);
//			Poll poll = new Gson().fromJson(json, Poll.class);
//			if(poll.getCreator().equals("")){
//				return null;
//			}
//			return poll;
//		}
		return null;
	}
	
	private static List<Poll> receivePolls(String GoogleID){
//		System.out.println("Getting Poll by User ID: " + GoogleID + "");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "getPollByUser");
			post.addFormField("username", GoogleID);
			json = post.finish();
//			System.out.println(json);
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
		if (json != null){
//			System.out.println(json);
			Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
			List<String> SerializedPollList = new Gson().fromJson(json, listType);
			List<Poll> pollList = new ArrayList<Poll>();
			for (String jsPoll : SerializedPollList){
				Poll p = new Gson().fromJson(jsPoll, Poll.class);
				pollList.add(p);
			}
			return pollList;
		}
		return null;
	}
	private static List<Poll> receiveAllPolls(){
		System.out.println("Getting All Polls");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "getAllPollsKey");
			json = post.finish();
//			System.out.println(json);
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
		if (json != null){
//			System.out.println(json);
			Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
			List<String> SerializedPollList = new Gson().fromJson(json, listType);
			List<Poll> pollList = new ArrayList<Poll>();
			for (String jsPoll : SerializedPollList){
//				System.out.println(jsPoll);
				Poll p = new Gson().fromJson(jsPoll, Poll.class);
				pollList.add(p);
			}
			return pollList;
		}
		return null;
	}
	private static String purgePollsIfEmpty(){
		System.out.println("Purging All Empty Polls");
		String json;
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "purgePolls");
			json = post.finish();
//			System.out.println(json);
		} catch (Exception ex) {
			ex.printStackTrace();
			json = null;
		}
		if (json != null){
			System.out.println(json);
			return null;
		}
		return null;
	}
	private static void purge() throws Exception {
		System.err.println("Purging...");
		HttpPost post = new HttpPost(BASE_URL, ENC);
		post.addFormField("op", "purge");
		post.finish();
		System.err.println("Waiting for purge to complete...");

		// wait until the purge is completed
		waitUntilNavail(0);
		System.err.println("Purge complete...");
	}

	private static void waitUntilNavail(int waitUntilThisMany) throws Exception {
		while (true) {
			HttpGet get = new HttpGet(BASE_URL, ENC);
			get.addFormField("op", "navail");
			String json = get.finish();
			JsonObject rv = new JsonParser().parse(json).getAsJsonObject();
			int size = rv.getAsJsonPrimitive("navail").getAsInt();
			if (size >= waitUntilThisMany)
				break;
			Thread.sleep(1000);
		}
	}
	

	

	private static void createStudy(String name, int size) throws Exception {
		System.err.println("Initiating study " + name);
		HttpPost post = new HttpPost(BASE_URL, ENC);
		post.addFormField("op", "initstudy");
		post.addFormField("name", name);
		post.addFormField("size", Integer.toString(size));
		String result = post.finish();
		if (result.contains("error"))
			throw new IllegalStateException("Server reported error " + result);

		System.err.println("Requested init for study " + name + ", waiting for writes...");
		while (getStudySize(name) == 0) {
			Thread.sleep(1000);
		}
	}

	private static int getStudySize(String name) throws Exception {
		HttpGet get = new HttpGet(BASE_URL, ENC);
		get.addFormField("op", "studyinfo");
		get.addFormField("name", name);
		String json = get.finish();
		JsonObject rv = new JsonParser().parse(json).getAsJsonObject();
		if (!rv.has("error"))
			return rv.getAsJsonArray("participants").size();
		else
			return 0;
	}
}
