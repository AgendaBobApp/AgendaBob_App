package casco.project1.Interfaces;

import android.view.View;

/**
 * Created by Stefan on 5/3/2016.
 */
public interface Communicator {
    public void switchToPart1();

    public void switchToPart2(String newPollName, String newPollDescription);
    public void switchToPart2();

    public void switchToPart3();
}
