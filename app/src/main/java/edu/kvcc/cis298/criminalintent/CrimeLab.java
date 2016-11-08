package edu.kvcc.cis298.criminalintent;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by amahler4096 on 10/19/2016.
 */
public class CrimeLab {

        // Static variable to hold the instance of the CrimeLab:
    private static CrimeLab sCrimeLab;
        // Declare a list of instances of type Crime:
    private static List<Crime> mCrimeList;
        // This context will be the hosting activity, assigned in the constructor:
    private Context mContext;

        // Static get method to get the single instance of the class:
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

        // PRIVATE constructor:
    private CrimeLab(Context context) {
            //Create a new array list of crimes:
        mCrimeList = new ArrayList<>();
            // Set the class level context to the one passed in:
        mContext = context;
            // Load the crime list:
        LoadCrimeList();
    }

        // Public method to add a crime, used with the new crime menu button:
    public void AddCrime(Crime c) {
            // Add a crime to the crime list:
        mCrimeList.add(c);
    }

    public static List<Crime> getCrimes() {
        return mCrimeList;
    }

    public Crime getCrime(UUID id) {
            // foreach loop in java - for each Crime crime in mCrimelist:
        for (Crime crime : mCrimeList) {
                // If a crime in the list matches the search ID, return it:
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
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

                // Add the crime to the list:
                mCrimeList.add(new Crime(uuid, stringTitle, date, solved));
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
