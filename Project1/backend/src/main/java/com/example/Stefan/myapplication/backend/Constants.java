package com.example.Stefan.myapplication.backend;

/**
 * Created by Stefan on 5/24/2016.
 */
public class Constants {

    public static final String CreateUserKey = "createUser";
    public static final String CreatePollKey = "createPoll";
    public static final String PurgeUsersKey = "purgeUsers";
    public static final String PurgePollsKey = "purgePolls";
    public static final String UserNameKey = "username";
    public static final String PollNameKey = "pollname";
    public static final String PollDescKey = "polldesc";
    public static final String PollCreatorKey = "pollcreator";

    public static final String AllUsersGroup = "allusers";
    public static final String AllPollsGroup = "allpolls";
//    public static String Key = "";
    public static Long ID = new Long(1);
    public static Long getNewID(){
        Long myID = new Long(ID);
        ID++;
        return myID;
    }
}
