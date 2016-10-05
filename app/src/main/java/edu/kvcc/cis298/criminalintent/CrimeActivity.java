package edu.kvcc.cis298.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        //Gets the fragment manager from the activity so that we can add
        //a fragment to a frame layout in a layout file.
        FragmentManager fm = getSupportFragmentManager();

        //Get the current fragment from the FrameLayout. When the app starts
        //there is no fragment in the framelayout, so it will return null.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //If the fragment that is returned is null, which it will be when the program
        //starts up, we will make a new fragment from the CrimeFragment class.
        //We then use the FragmentManager to start a transaction of adding the
        //fragment to the container. The add method does the actual add, and last,
        //the commit method saves the work.
        //All of these methods use method chaining so we can just continue to call
        //the next method on it. It could be broken up like this:
        //fm.beginTransaction();
        //fm.add(R.id.fragment_container, fragment)
        //fm.commit();
        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


    }
}








