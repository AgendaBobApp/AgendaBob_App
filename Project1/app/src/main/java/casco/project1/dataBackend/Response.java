package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import android.util.ArraySet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Date;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class Response extends TimeSet implements Serializable {
    private User creator;
    private Date date;

    public Response () {
        super();
        this.creator = null;
        this.date = null;
    }
    public Response (User creator, Date date) {
        super();
        this.creator = creator;
        this.date = date;
    }

    public Response (List<TimeRange> times, User creator, Date date) {
        // create a response based on provided time ranges
        super(times);
        this.creator = creator;
        this.date = date;
    }

    public User getCreator() { return creator; }

    public Date getDate() { return date; }
}
