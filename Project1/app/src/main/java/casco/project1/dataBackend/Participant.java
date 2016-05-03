package casco.project1.dataBackend;

import java.util.Date;

/**
 * Created by Baron on 4/30/2016.
 */
/*
 * Holds information about a participant in the poll.
 * Such things are
 *      Participant's name(id)
 *      date participated
 *      participant's response
 */
public class Participant extends User {
    private String name;
    private Date date;      // date when created
    private Response response;

    Participant(String name, int imageIndex) {
        super(name, imageIndex);
        this.date = null;
        this.response = null;
    }

    Participant(String name, Date date, Response response) {
        super(name);
        this.date = date;
        this.response = response;
    }

    Participant(String name, int imageIndex, Date date, Response response) {
        super(name, imageIndex);
    }

    public Response getResponse() {
        return response;
    }

    public String getUser() {
        return name;
    }
}
/*public class Participants {
    private User name;
    private Date date;      // date when created
    private Response response;

    Participants(User name, Date date, Response response) {
        this.name = name;
        this.date = date;
        this.response = response;
    }

    public User getUser() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Response getResponse() {
        return response;
    }
}*/
