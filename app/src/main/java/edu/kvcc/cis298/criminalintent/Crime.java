package edu.kvcc.cis298.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by dbarnes on 10/5/2016.
 */
public class Crime {

    //Variables
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    //Constructor
    public Crime() {
        //Generate unique id
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    // 4 parameter constructor:
    public Crime(UUID uuid, String title, Date date, boolean solved) {
        mId = uuid;
        mTitle = title;
        mDate = date;
        mSolved = solved;
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

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
