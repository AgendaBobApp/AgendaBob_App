package casco.project1.dataBackend;

import java.util.ArrayList;
import java.util.List;

import casco.project1.R;


/**
 * Created by Stefan on 5/2/2016.
 */
public class TestPopulator {
    int pollID = 0;
    int userID = 0;
    int partID = 0;
    public List<User> users = new ArrayList<User>();
    public List<Participant> participants = new ArrayList<Participant>();
    public List<Poll> polls = new ArrayList<Poll>();
    String[] userNames = {"Austin", "Christian", "Jonathon", "Stefan", "William"};
    String[] pollNames = {"Learn Japanese", "Political Discussion",
            "Gaming Group", "Android Fest", "Twilight Imperium"};
    int[] images = {
            R.drawable.ic_person_red,
            R.drawable.ic_person_orange,
            R.drawable.ic_person_yellow,
            R.drawable.ic_person_green,
            R.drawable.ic_person_blue,
            R.drawable.ic_person_purple,
    };
    public TestPopulator(){
        for(int i = 0; i < userNames.length; i++)
            createUser();
        for(int i = 0; i < userNames.length; i++)
            createUser();
        for(int i = 0; i < pollNames.length; i++)
            createPoll();
    }

    public void createPoll(){
        int shortCode = pollID;
        User creator = users.get(pollID);
        String title = pollNames[pollID % pollNames.length];
        String description = "<Description>";
        Response baseTime = new Response();
        Poll newPoll = new Poll(shortCode,  creator, title, description, null, participants) {
        };
        polls.add(newPoll);
        pollID++;
    }
    public void createUser(){
        users.add(new User(userNames[userID % userNames.length], images[userID % images.length]));
        userID++;
    }
    public void createParticipant(){
        participants.add(new Participant(userNames[partID % userNames.length], images[partID % images.length]));
        partID++;
    }
}
