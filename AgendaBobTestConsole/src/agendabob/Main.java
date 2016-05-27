package agendabob;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
	private static final String extension = "agendabob";
	private static final String BASE_URL = "http://localhost:8080/agendabob";
	private static final String ENC = "UTF-8";
	public static void main(String[] args) throws Exception {
		System.err.println("Creating New Users");
		createUser("Stefan");
		createUser("Austin");
		createUser("John");
		createUser("William");
		createUser("Christian");
		
		
		createPoll("Pokemon TCG tourney", "Stefan", "Nothing special");
		createPoll("Anime Group", "Austin", "Nada");
		createPoll("Gaming Group", "William", "Eh?");
		createPoll("Android Coding", "John", "code.....");
		createPoll("New Poll1", "Stefan", "New stuff!");
		createPoll("New Poll2", "Austin", "New stuff!");
		createPoll("New Poll3", "William", "New stuff!");
		createPoll("New Poll14", "John", "New stuff!");
	}
	private static void createUser(String name) throws Exception {
		System.err.println("Creating User: " + name + "");
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "createUser");
			post.addFormField("username", name);
			post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.err.println("Created User "+name+ " on Server");
	}
	private static void createPoll(String name, String creator, String desc) throws Exception {
		System.err.println("Creating Poll: " + name + "");
		try {
			HttpPost post = new HttpPost(BASE_URL, ENC);
			post.addFormField("op", "createPoll");
			post.addFormField("pollname", name);
			post.addFormField("pollcreator", creator);
			post.addFormField("polldesc", desc);
			post.finish();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.err.println("Created Poll "+name+ " on Server");
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
