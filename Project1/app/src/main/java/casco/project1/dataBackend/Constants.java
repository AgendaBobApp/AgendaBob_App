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
    public static final String PollNameBundleKey = "POLL NAME";
    public static final String PollDescBundleKey = "POLL DESC";
    public static final String UserBundleKey = "USER";
    public static final String[] WeekDays = {
            "Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
    };
    public static final String[] TimesHalfHour = {

    };
    public static User loadUser(Bundle bundle){
        User currentUser = new User();
        if (bundle != null) {
            currentUser = (User) bundle.getSerializable(Constants.UserBundleKey);
            if(currentUser == null)
                Log.i("STEFAN", "Bundle was not null, but user was.");
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
