package com.example.Stefan.myapplication.backend;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Created by Stefan on 5/24/2016.
 */
@PersistenceCapable
public class UserGroup {
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
        this.key = new KeyFactory.Builder(UserGroup.class.getSimpleName(), groupName).getKey();
    }

    public static UserGroup load(String groupName, PersistenceManager pm) {
        return pm.getObjectById(UserGroup.class, groupName);
    }

    public static UserGroup create(String groupName, PersistenceManager pm) {
        UserGroup group = new UserGroup();
        group.setGroupName(groupName);
        pm.makePersistent(group);
        return group;
    }

}
