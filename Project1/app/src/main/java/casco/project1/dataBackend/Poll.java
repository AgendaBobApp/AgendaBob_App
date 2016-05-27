package casco.project1.dataBackend;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Baron on 4/30/2016.
 */
public class Poll implements Serializable{
    private int shortCode; // id for the poll
    private String longCode; // GAE id of poll
    private User creator; // user that created the poll
    private String title; // name of the poll
    private String description; // description of the poll
    private TimeSet baseTime; // times that participants can select from
    private List<Response> responses;

    public Poll() {
        this.shortCode = 12345;
        this.creator = new User("FooBar");
        this.title = "A Really Bad Title";
        this.description = "An instance of Poll that really should not be here!";
        this.baseTime = new TimeSet();
        this.responses = new ArrayList<Response>();
    }

    public Poll(int shortCode, User creator, String title, String description, TimeSet baseTime, List<Response> responses) {

        this.shortCode = shortCode;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.baseTime = baseTime;
        this.responses = responses;
    }
    public void setShortCode(int sc){shortCode = sc;}
    public void setLongCode(String lc) {longCode = lc;}
    public void setCreator(User usr){creator = usr;}
    public void setTitle(String t){title = t;}
    public void setDescription(String desc){description = desc;}
    public void setBaseTime(TimeSet ts){baseTime = ts;}
    public void setResponses(List<Response> responses){responses = responses;}

    public int getShortCode() {
        return shortCode;
    }

    public String getLongCode() { return longCode; }

    public User getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TimeSet getBaseTime() {
        return baseTime;
    }

    // This really should be changed to give only one participant? maybe... IDK...
    public List<Response> getResponses() {
        return responses;
    }
/*
    // return the combined response of all participants
    public TimeSet combinedResponse()  {
        List<TimeSet> list = new ArrayList<TimeSet>();
        list.add(baseTime);
        for (Response r : responses) {
            list.add(r);
        }

        TimeSet response = new TimeSet(list);
        return response;
    }
*/
    // get all users that have participated
    public List<User> allParticipants() {
        List<User> list = new LinkedList<User>();
        for (Response r : responses) {
            list.add(r.getCreator());
        }
        return list;
    }
/*
    // get a response for a particular user
    // the user's response is combined with the bastTime
    // may need to be edited
    public TimeSet participantResponse(User user) {
        List<TimeSet> list = new ArrayList<TimeSet>();
        list.add(baseTime);
        for (Response r : responses) {
            if (r.getCreator().getName().equals(user.getName())){
                list.add(r);
            }
        }
        return new TimeSet(list);
    }
    */
}
