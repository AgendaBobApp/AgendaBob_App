package casco.project1.dataBackend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import casco.project1.R;

/**
 * Created by Stefan on 5/3/2016.
 */
public class Constants {
    // public static final
    public static final String PollFilePath = "";
    public static final String PollBundleKey = "POLL";
    public static final String PollNameBundleKey = "POLL NAME";
    public static final String PollDescBundleKey = "POLL DESC";
    public static final String PollDaysBundleKey = "POLL DAYS";
    public static final String PollTimesBundleKey = "POLL TIMES";
    public static final String PollStartTimeBundleKey = "POLL START TIME";
    public static final String PollEndTimeBundleKey = "POLL END TIME";
    public static final String UserBundleKey = "USER";
    public static final String BASE_URL = "http://localhost:8080/agendabob";
    public static final String ENC = "UTF-8";
    public static final String[] WeekDays = {
            "Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
    };
    public static final int DefaultStartTimesHalfHour = 16;
    public static final int DefaultEndTimesHalfHour = 40;
    public static final String[] TimesHalfHour = {
            "12:00am", "12:30am", "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am",
            "4:00am", "4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am", "7:30am",
            "8:00am", "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am", "11:30am",
            "12:00pm", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm",
            "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm", "7:00pm", "7:30pm",
            "8:00pm", "8:30pm", "9:00pm", "9:30pm", "10:00pm", "10:30pm", "11:00pm", "11:30pm"
    };
    public static final int ClearBackstackFlags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP;
    public static User loadUser(Bundle bundle){
        User currentUser = new User();
        if (bundle != null) {
            currentUser = (User) bundle.getSerializable(Constants.UserBundleKey);
            if(currentUser == null) {
                Log.i("STEFAN", "Bundle was not null, but user was.");
                return new User();
            }
            else
                Log.i("STEFAN", "User passed from other activity");
        }
        else
        {
            currentUser = new User();
            Log.i("STEFAN", "User RECREATED");
        }
        if(currentUser != null)
            Log.i("STEFAN", currentUser.getName());
        return currentUser;
    }
    public static Intent putUserInIntent(Intent intent, User user){
        Bundle b = new Bundle();
        b.putSerializable(Constants.UserBundleKey, user);
        intent.putExtras(b);
        return intent;
    }

}
