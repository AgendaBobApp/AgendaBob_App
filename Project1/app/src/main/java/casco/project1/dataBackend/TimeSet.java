package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class TimeSet {
    protected Set<TimeRange> times;

    TimeSet() {
        this.times = new TreeSet<TimeRange>();
    }

    TimeSet(Set<TimeRange> times) {
        // create a response based on provided time ranges
        this.times = new TreeSet<TimeRange>(times); // need to error check
    }

    TimeSet(List<TimeSet> responses) {
        // create a response that is a composite of other responses

        if (responses == null || responses.size() < 1) {
            // throw an exception or something this is not good
            this.times = new TreeSet<TimeRange>();
        }
        else if (responses.size() == 1) {
            this.times = new TreeSet<TimeRange>(responses.get(0).getTimes());
        }
        else {
            // put one response into times to test against
            this.times = new TreeSet<TimeRange>(responses.get(0).getTimes()); // need to error check
            // dont need to test against it again
            responses.remove(0);

            // hold the intersected TimeRanges to reset times with
            Set<TimeRange> reset = new TreeSet<TimeRange>();

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
                reset = new TreeSet<TimeRange>();
            }
        }
    }

    public Set<TimeRange> getTimes() {
        return times;
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeInt(times.size()); // how many elements are in the list
        for (TimeRange tr : times) {
            tr.serialize(dos);
        }
        dos.flush();
    }

    public static TimeSet deserialize(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        Set<TimeRange> times = new TreeSet<TimeRange>();
        for (int i = 0; i < size; i++) {
            times.add(TimeRange.deserialize(dis));
        }
        return new TimeSet(times);
    }
}
