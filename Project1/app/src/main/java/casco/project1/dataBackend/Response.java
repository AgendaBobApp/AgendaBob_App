package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import android.util.ArraySet;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class Response {
    private Set<TimeRange> times;

    Response () {
        this.times = new TreeSet<TimeRange>();
    }

    Response (Set<TimeRange> times) {
        // create a response based on provided time ranges
        this.times = new TreeSet<TimeRange>(times);
    }

    Response (List<Response> responses) {
        // create a response that is a composite of other responses
        this.times = new TreeSet<TimeRange>();
        for (Response r: responses) {
            this.times.addAll(r.getTimes()); // probaby wrong thing but you get idea
        }
    }

    public Set<TimeRange> getTimes() {
        return times;
    }
}
