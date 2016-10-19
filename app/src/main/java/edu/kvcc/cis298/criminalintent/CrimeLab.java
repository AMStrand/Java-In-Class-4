package edu.kvcc.cis298.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by amahler4096 on 10/19/2016.
 */
public class CrimeLab {

        // Static variable to hold the instance of the CrimeLab:
    private static CrimeLab sCrimeLab;
        // Declare a list of instances of type Crime:
    private List<Crime> mCrimeList;

        // Static get method to get the single instance of the class:
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

        // PRIVATE constructor:
    private CrimeLab(Context context) {
        mCrimeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimeList.add(crime);
        }
    }

    public List<Crime> getCrimes() {
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

}
