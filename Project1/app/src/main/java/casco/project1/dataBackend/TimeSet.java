package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class TimeSet implements Serializable{
    protected List<TimeRange> times;

    public TimeSet() {
        this.times = new ArrayList<TimeRange>();
    }

    public TimeSet(List<TimeRange> times) {
        // create a response based on provided time ranges
        this.times = new ArrayList<TimeRange>(times); // need to error check
    }
/*
    public TimeSet(List<TimeSet> responses) {
        // create a response that is a composite of other responses

        if (responses == null || responses.size() < 1) {
            // throw an exception or something this is not good
            this.times = new ArrayList<TimeRange>();
        }
        else if (responses.size() == 1) {
            this.times = new ArrayList<TimeRange>(responses.get(0).getTimes());
        }
        else {
            // put one response into times to test against
            this.times = new ArrayList<TimeRange>(responses.get(0).getTimes()); // need to error check
            // dont need to test against it again
            responses.remove(0);

            // hold the intersected TimeRanges to reset times with
            List<TimeRange> reset = new ArrayList<TimeRange>();

            // get a response
            for (TimeSet r : responses) {
                // get all the time ranges for that response
                for (TimeRange tr : r.getTimes()) {
                    // find the intersections that we want to keep
                    for (TimeRange time : times) {
                        TimeRange add = time.interects(tr);
                        if (add != null) {
                            reset.add(add);
                        }
                    }
                }
                this.times = reset;
                reset = new ArrayList<TimeRange>();
            }
        }
    }
*/
    public List<TimeRange> getTimes() {
        return times;
    }
}
