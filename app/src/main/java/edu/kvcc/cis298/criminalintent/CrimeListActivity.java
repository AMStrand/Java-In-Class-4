package edu.kvcc.cis298.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by amahler4096 on 10/19/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
