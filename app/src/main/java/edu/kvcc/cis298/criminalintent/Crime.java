package edu.kvcc.cis298.criminalintent;

import java.util.UUID;

/**
 * Created by dbarnes on 10/5/2016.
 */
public class Crime {

    //Variables
    private UUID mId;
    private String mTitle;

    //Constructor
    public Crime() {
        //Generate unique id
        mId = UUID.randomUUID();
    }

    //Getters and Setters
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
