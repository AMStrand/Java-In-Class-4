package edu.kvcc.cis298.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by amahler4096 on 10/19/2016.
 */
public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;

        // Declare a new crimeAdapter using the inner class written below in this file:
    private CrimeAdapter mAdapter;

        // Bool to know whether the subtitle is being shown:
    private boolean mSubtitleVisible;

        // String key for saving whether to show the subtitle:
    private static String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // Add this to let the fragment manager know it has an options menu,
            // so the fragment manager will call the onCreateOptionsMenu method,
            // where the work is done to inflate the menu and display it:
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
        // In a fragment, the onCreateView method occurs when the view is created, so this override method is used:
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // Inflate the view from the layout file into a view variable:
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

            // Get a handle to the recycler view using findViewById:
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

            // Set the layout manager for the recycler view - it needs to know how to
            // lay out the individual views that make up the recycler view:
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get the bool for whether to show the subtitle:
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

            // Call the UpdateUI method to set up the recyclerView with data:
        UpdateUI();

            // Return the view:
        return view;

    }

    public class CrimeHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {
            // Add a title variable to the viewHolder:
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

            // When a crime is clicked, enter the CrimeActivity:
        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }

        // Variable to hold a single crime:
        private Crime mCrime;

            // Constructor for the CrimeHolder:
        public CrimeHolder (View itemView) {
                // Call the parent constructor:
            super(itemView);
            itemView.setOnClickListener(this);

                // Get and assign the passed itemView to the TextView variable:
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

            // Write a method to take in an instance of a crime and assign the crime properties
            // to the various view widgets:
        public void bindCrime(Crime crime) {
                // Assign the passed in crime to the class level variable:
            mCrime = crime;
                // Set the TextViews and CheckBox based on the current crime:
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
            // Set up local version of the crime list:
        private List<Crime> mCrimes;

            // Constructor to set the list of crimes:
        public CrimeAdapter(List<Crime> crimeList) {
            mCrimes = crimeList;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Get a reference to a layout inflater to inflate our view:
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                // Use the inflater to inflate the default android list view:
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);

                // Return a new CrimeHolder and pass in the view we just created:
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
                // Get the crime out of the crimes list:
            Crime crime = mCrimes.get(position);

                // Send the crime to the bindCrime method to set the widget data:
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
            // Update the UI when returning to this fragment:
        UpdateUI();
    }

        // Override method called when the menu is created:
        // In the onCreate method, we set setHasOptionsMenu to true,
        // so the fragment manager knows to call this method:
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
            // Inflate the proper layout with the passed in menu:
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);

        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

        // Method for when a menu item is selected:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            // Switch statement to determine item selected and work accordingly:
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                    // Create a new crime:
                Crime crime = new Crime();
                    // Add the new crime to the singleton crime lab:
                CrimeLab.get(getActivity()).AddCrime(crime);
                    // Enter the crime pager activity to add information for the crime
                    // by asking that activity for its intent then using it to start that activity:
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                    // Method was successful, no further processing necessary:
                return true;
            case R.id.menu_item_show_subtitle:
                    // Change whether the subtitles are visible when the item is clicked:
                mSubtitleVisible = !mSubtitleVisible;
                    // Makes the app recreate the menu:
                getActivity().invalidateOptionsMenu();
                    // Update the subtitles:
                UpdateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        // Save whether the subtitle is visible on save instance state:
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    // ******************************************************************************************

    // PRIVATE METHODS:

    private void UpdateUI() {
            // Using the static method on the CrimeLab class to return the singleton.
            // This will get us our one and only one instance of the CrimeLab.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

            // Get the list of crimes from the singleton CrimeLab and save to local var:
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            // Create a new CrimeAdapter and send over the crime list so that it can
            // make new viewHolders and bind data to the viewHolders:
            mAdapter = new CrimeAdapter(crimes);

            // Set the Adapter on the RecyclerView:
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else {
            // Notify the adapter that the data may have changed and it should reload:
            mAdapter.notifyDataSetChanged();
        }

        UpdateSubtitle();
    }

    private void UpdateSubtitle() {

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);

    }

}
