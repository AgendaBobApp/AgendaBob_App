package com.example.Stefan.myapplication.backend;

import com.google.appengine.api.datastore.Key;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created by Stefan on 5/24/2016.
 */
@PersistenceCapable
public class Poll implements Serializable{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent
    @Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
    private Key entityGroup;

    @Persistent
    private Date modifiedDate;

    @Persistent(mappedBy = "employee", dependentElement = "true")
    private Set<String> activeUsers;

    @Persistent
    private String serialPoll;

    public Key getID() {
        return id;
    }

    public void setEntityGroup(Key entityGroup) {
        this.entityGroup = entityGroup;
    }
    public Key getEntityGroup() {
        return entityGroup;
    }

    public Date updateDate() {
        modifiedDate = new Date();
        return modifiedDate;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public boolean addUser(String user) {
        if (activeUsers == null) {
            activeUsers = new HashSet<String>();
        }
        return activeUsers.add(user);
    }
    public boolean removeUser(String user) {
        return activeUsers.remove(user);
    }
    public Set<String> getActiveUsers() {
        return activeUsers;
    }

    public void setSerialPoll(String poll) {
        serialPoll = poll;
    }
    public String getSerialPoll() {
        return serialPoll;
    }
}
