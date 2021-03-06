package casco.project1.dataBackend;

/**
 * Created by Baron on 4/30/2016.
 */

import android.util.TimeUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * has start stop times for a given date
 * also has comparators for
 */
public class TimeRange implements Serializable{
    private Date startTime;
    private Date endTime;

    public TimeRange() {
        this.startTime = new Date();
        this.endTime = new Date();
    }

    public TimeRange(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public TimeRange interects(TimeRange tr) {
        if (startTime.before(tr.startTime)) {
            if (endTime.after(tr.startTime)) {
                if (endTime.before(tr.endTime)) {
                    return new TimeRange(tr.startTime, endTime);
                }
                else {
                    return new TimeRange(tr.startTime, tr.endTime);
                }
            }
        }
        else {
            if (startTime.before(tr.endTime)) {
                if (endTime.before(tr.endTime)) {
                    return new TimeRange(startTime, endTime);
                }
                else {
                    return new TimeRange(startTime, tr.endTime);
                }
            }
        }
        return null; //should this throw an exception instead?
    }
}
