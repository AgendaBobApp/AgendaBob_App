package com.example.Stefan.myapplication.backend;

/**
 * Created by Stefan on 5/24/2016.
 */
public class Constants {
    // constants related to poll and pollGroup
    public static final String PollID = "key";
    public static final String PollGroup = "group";
    public static final String PollModDate = "date";
    public static final String UserNameKey = "username";
    public static final String ActiveUsers = "userset";
    public static final String Poll = "poll";
    public static final String AllPollsGroup = "allpolls";

    // constants related to user and userGroup
    public static final String AllUsersGroup = "allusers";

    // operations poll and pollGroup
    public static final String CreatePollKey = "createPoll";
    public static final String GetAllPollsKey = "getAllPollsKey";
    public static final String GetPollByKey = "getPollByKey";
    public static final String UpdatePollByKey = "updatePollKey";
    public static final String GetPollByDate = "getPollByDateKey";
    public static final String GetPollByUserKey = "getPollByUser";
    public static final String PurgePollsKey = "purgePolls";

    // operations user and userGroup
    public static final String CreateUserKey = "createUser";
    public static final String addUserKey = "addUser";
    public static final String removeUserKey = "removeUser";
    public static final String PurgeUsersKey = "purgeUsers";
}
