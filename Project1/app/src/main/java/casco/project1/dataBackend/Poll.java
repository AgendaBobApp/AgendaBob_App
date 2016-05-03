package casco.project1.dataBackend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Baron on 4/30/2016.
 */
public class Poll {
    private int shortCode; // id for the poll
    private User creator; // user that created the poll
    private String title; // name of the poll
    private String description; // description of the poll
    private Response baseTime; // times that participants can select from
    private List<Participants> participants;

    Poll() {
        this.shortCode = 12345;
        this.creator = new User("FooBar");
        this.title = "A Really Bad Title";
        this.description = "An instance of Poll that really should not be here!";
        this.baseTime = new Response();
        this.participants = new ArrayList<Participants>();
    }

    Poll(int shortCode, User creator, String title, String description, Response baseTime, List<Participants> participants) {
        this.shortCode = shortCode;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.baseTime = baseTime;
        this.participants = participants;
    }

    public int getShortCode() {
        return shortCode;
    }

    public User getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Response getBaseTime() {
        return baseTime;
    }

    // This really should be changed to give only one participant? maybe... IDK...
    public List<Participants> getParticipants() {
        return participants;
    }

    // return the combined response of all participants
    public Response combinedResponse()  {
        List<Response> list = new LinkedList<Response>();
        list.add(baseTime);
        for (Participants p : participants) {
            list.add(p.getResponse());
        }

        Response response = new Response(list);
        return response;
    }

    // get all users that have participated
    public List<User> allParticipants() {
        List<User> list = new LinkedList<User>();
        for (Participants p : participants) {
            list.add(p.getUser());
        }
        return list;
    }

    // get a response for a particular user
    // the user's response is combined with the bastTime
    // may need to be edited
    public Response participantResponse(User user) {
        List<Response> list = new ArrayList<Response>();
        list.add(baseTime);
        for (Participants p : participants) {
            if (p.getUser().getName().equals(user.getName())){
                list.add(p.getResponse());
            }
        }
        return new Response(list);
    }
}
