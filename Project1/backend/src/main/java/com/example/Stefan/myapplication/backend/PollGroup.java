package com.example.Stefan.myapplication.backend;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by Stefan on 5/24/2016.
 */
@PersistenceCapable
public class PollGroup {
    @PrimaryKey
    @Persistent
    private Key key;

    public Key getKey() {
        return key;
    }

    public String getGroupName() {
        return key.getName();
    }

    public void setGroupName(String groupName) {
        this.key = new KeyFactory.Builder(PollGroup.class.getSimpleName(), groupName).getKey();
    }

    public static PollGroup load(String groupName, PersistenceManager pm) {
        return pm.getObjectById(PollGroup.class, groupName);
    }

    public static PollGroup create(String groupName, PersistenceManager pm) {
        PollGroup group = new PollGroup();
        group.setGroupName(groupName);
        pm.makePersistent(group);
        return group;
    }

}
