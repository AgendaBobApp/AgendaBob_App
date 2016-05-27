package com.example.Stefan.myapplication.backend;

import com.google.appengine.api.datastore.Key;

import java.io.Serializable;

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
    private String name;

    @Persistent
    private String description;

    @Persistent
    private String creator;

    public Key getID() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return name;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setEntityGroup(Key entityGroup) {
        this.entityGroup = entityGroup;
    }
    public Key getEntityGroup() {
        return entityGroup;
    }
}
