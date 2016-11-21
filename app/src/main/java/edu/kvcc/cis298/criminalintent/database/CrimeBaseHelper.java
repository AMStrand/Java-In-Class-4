package edu.kvcc.cis298.criminalintent.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.Crime;
import edu.kvcc.cis298.criminalintent.CrimeLab;
import edu.kvcc.cis298.criminalintent.R;
import edu.kvcc.cis298.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by amahler4096 on 11/9/2016.  This class interacts with the database from the java code.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {
        // Variable to hold the context:
    Context mContext;

        // Create a version number that can be used to trigger a call to onUpgrade.
        // If when the app starts, the existing database version # does not match
        // that in the code, the onUpgrade method will be called.
    private static final int VERSION = 1;
        // Constant for th database name:
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);

        mContext = context;
    }

        // OnCreate method called to create the database if it does not exist:
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ")"
        );
            // Now that the table is created, use the csv file to seed the database with data:
        //LoadCrimeList();
    }

        // OnUpgrade method called if the version number doesn't match that in the code:
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void LoadCrimeList() {

            // Define a scanner to read in the file:
        Scanner scanner = null;

        try {
                // Attempt to instantiate the scanner:
            scanner = new Scanner(mContext.getResources().openRawResource(R.raw.crimes));

            while (scanner.hasNextLine()) {
                    // Get the next line and split it into parts:
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                    // Assign parts to local variables:
                String stringUUID = parts[0];
                String stringTitle = parts[1];
                String stringDate = parts[2];
                String stringSolved = parts[3];

                    //Set up variables for parsing:
                UUID uuid = UUID.fromString(stringUUID);
                    // New date format to parse the dates in the file:
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date date = format.parse(stringDate);
                    // Shorthand if/else statement:
                boolean solved = (stringSolved.equals("1")) ? true : false;

                // Get a reference to the CrimeLab singleton:
                CrimeLab mCrimeList = CrimeLab.get(mContext);

                mCrimeList.AddCrime(new Crime(uuid, stringTitle, date, solved));


            }
        }
        catch (Exception e) {
            Log.e("Read CSV", e.toString());
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
