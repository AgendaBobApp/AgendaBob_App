package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import android.util.ArraySet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Date;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class Response extends TimeSet{
    private User creator;
    private Date date;

    Response () {
        super();
        this.creator = null;
        this.date = null;
    }

    Response (Set<TimeRange> times, User creator, Date date) {
        // create a response based on provided time ranges
        super();
        this.creator = creator;
        this.date = date;
    }

    public User getCreator() { return creator; }

    public Date getDate() { return date; }

    public void serialize(DataOutputStream dos) throws IOException {
        creator.serialize(dos);
        dos.writeLong(date.getTime()); // date as a long
        dos.writeInt(times.size()); // how many elements are in the list
        for (TimeRange tr : times) {
            tr.serialize(dos);
        }
        dos.flush();
    }

    public static Response deserialize(DataInputStream dis) throws IOException {
        User u = User.deserialize(dis);
        Date d = new Date(dis.readLong());
        int size = dis.readInt();
        Set<TimeRange> s = new TreeSet<TimeRange>();
        for (int i = 0; i < size; i++) {
            s.add(TimeRange.deserialize(dis));
        }
        return new Response(s, u, d);
    }
}
