package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Holds information about a response to a given poll
 * Also has some handy functions for response data analysis
 *      Such as create a Response that is a composite of a set of responses
 */
public class TimeSet implements Serializable{
    protected Dictionary<String, Set<String>> times;

    public TimeSet() {
        this.times  = new Hashtable<String, Set<String>>();
    }

    public void setWholeDictionary(Dictionary<String, Set<String>> d) {
        times = d;
    }

    public Dictionary<String, Set<String>> getTimes() {
        return times;
    }

    public void addDay(String day) {
        times.put(day, new TreeSet<String>());
    }

    public void addTimeToDay(String day, String time) {
        times.get(day).add(time);
    }

    // cant really be used with other addtimes
    // this will reset the value for given key!!!!!!!!!!!!!
    public void addDayTimes(String day, List<String> list) {
        times.put(day, new TreeSet<String>(list));
    }

    // cant really be used with other addtimes
    // this will reset the value for given key!!!!!!!!!!!!!
    public void addDayTimes(String day, Set<String> set) {
        times.put(day, set);
    }

    public List<String> getDayTimesList(String day) {
        return new ArrayList<String>(times.get(day));
    }

    public Set<String> getDayTimesSet(String day) {
        return times.get(day);
    }

    public List<String> getDayList() {
        List<String> days = new ArrayList<>();
        for (String day: Constants.WeekDays) {
            if (times.get(day) != null) {
                days.add(day);
            }
        }

        return days;
    }
}
