package edu.kvcc.cis298.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.database.CrimeDbSchema;

import static edu.kvcc.cis298.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by amahler4096 on 11/21/2016.
 */
public class CrimeCursorWrapper extends CursorWrapper {

        // Constructor:
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

        // Public method to get a crime from the database:
    public Crime getCrime() {
            // Get the values for a specific crime from the database:
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
            // Create a new crime using the given uuid:
        Crime crime = new Crime(UUID.fromString(uuidString));
        // Set the remaining properties on the crime:
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);
            // Return the crime:
        return null;
    }
}









