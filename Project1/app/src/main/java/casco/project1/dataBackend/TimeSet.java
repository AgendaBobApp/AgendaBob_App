package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class TimeSet implements Serializable{
    protected Dictionary<String, List<String>> times;

    public TimeSet() {
        this.times  = new Hashtable<String, List<String>>();
    }

    public void setWholeDictionary(Dictionary<String, List<String>> d) {
        times = d;
    }

    public Dictionary<String, List<String>> getTimes() {
        return times;
    }

    public void addDay(String day) {
        times.put(day, new ArrayList<String>());
    }

    public void addTimeToDay(String day, String time) {
        times.get(day).add(time);
    }

    public void addDayTimes(String day, List<String> list) {
        times.put(day, list);
    }

    public List<String> getDayTimes(String day) {
        return times.get(day);
    }
}
