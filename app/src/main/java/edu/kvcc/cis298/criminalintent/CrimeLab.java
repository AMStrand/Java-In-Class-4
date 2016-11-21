package edu.kvcc.cis298.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.database.CrimeBaseHelper;
import edu.kvcc.cis298.criminalintent.database.CrimeDbSchema;

import static edu.kvcc.cis298.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by amahler4096 on 10/19/2016.
 */
public class CrimeLab {

        // Static variable to hold the instance of the CrimeLab:
    private static CrimeLab sCrimeLab;
        // This context will be the hosting activity, assigned in the constructor:
    private Context mContext;
        // Variable to hold the sqlite database:
    private SQLiteDatabase mDatabase;

        // Static get method to get the single instance of the class:
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

        // PRIVATE constructor:
    private CrimeLab(Context context) {
            // Set the class level context to the one passed in:
        mContext = context;
            // Set the database:
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

        // Public method to add a crime to the database:
    public void AddCrime(Crime c) {
            // Creates the set of values to be added into the database:
        ContentValues values = getContentValues(c);
            // Insert the values into the database:
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();
            // Create a cursor wrapper that holds a query of all the crimes in the database:
        CrimeCursorWrapper cursor = queryCrimes(null, null);
            // Enter try statement for database work in case of exceptions:
        try {
                // Move the cursor to the first entry:
            cursor.moveToFirst();
                // While the cursor is still within the database entries:
            while (!cursor.isAfterLast()) {
                    // Add the current crime from the cursor:
                crimes.add(cursor.getCrime());
                    // Move to the next entry:
                cursor.moveToNext();
            }
        }
        catch (Exception ex) {
                // Error message to log if an exception is thrown:
            Log.e("DATABASE CURSOR", ex.getMessage());
        }
        finally {
                // Close the cursor:
            cursor.close();
        }
            // Return the crimes list:
        return crimes;
    }

    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor = queryCrimes(
                    // Where statement for the UUID match:
                CrimeTable.Cols.UUID + " = ?",
                    // Send over the uuid from the passed in crime:
                new String[] {id.toString()}
        );

        try {
                // Check if a result was found:
            if (cursor.getCount() == 0) {
                    // If no result found, return null:
                return null;
            }
                // Move the cursor to the first result (should be the only result)
            cursor.moveToFirst();
                // Return that crime:
            return cursor.getCrime();
        }
        finally {
                // Close tht cursor:
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
            // Convert uuid to a string value:
        String uuidString = crime.getId().toString();
            // Get the content values for the crime:
        ContentValues values = getContentValues(crime);
            // Update the table, giving the table name, update values, the where clause
            // for the UUID match, and the uuid of the specified crime:
            // The where clause is parameterized to prevent SQL injection attacks,
            // so the second parameter (String[]) is filled in for the "?".
            // The String[] values are escaped so that they cannot be sent in as code.
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

        // Public method to get the content values for a specific crime:
    private static ContentValues getContentValues (Crime crime) {
            // Create a new content values object that stores key => value pairs:
        ContentValues values = new ContentValues();
            // Put the values of the chosen crime into the content value object,
            // assigned to their corresponding columns in the database:
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
            // Return the set of content values:
        return values;
    }

        // Private method to query the database:
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
            // Create a cursor that holds the query:
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,           // Null selects all columns
                whereClause,
                whereArgs,
                null,           // groupBy
                null,           // having
                null            // orderBy - ascending, descending, etc
                                // limit - # of results (overloaded - not necessary)
        );
            // Return the cursor:
        return new CrimeCursorWrapper(cursor);
    }
}
