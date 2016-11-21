package edu.kvcc.cis298.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by dbarnes on 10/5/2016.
 */
public class Crime {

        // Variables:
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

        // Constructor for a crime that needs a uuid:
    public Crime() {
            // Calls this class again, but creates a random uuid
            // to send over to the other constructor:
        this(UUID.randomUUID());
    }

        // Constructor for a crime with a given uuid:
        // Primarily used to create a new Crime when
        // reading from the database.
    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

        // 4 parameter constructor:
    public Crime(UUID uuid, String title, Date date, boolean solved) {
        mId = uuid;
        mTitle = title;
        mDate = date;
        mSolved = solved;
    }

        // Getters and Setters:
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
