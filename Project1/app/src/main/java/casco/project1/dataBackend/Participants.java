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
public class Participants {
    private String name;
    private Date date;      // date when created
    private Response response;

    Participants(String name, Date date, Response response) {
        this.name = name;
        this.date = date;
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Response getResponse() {
        return response;
    }
}
