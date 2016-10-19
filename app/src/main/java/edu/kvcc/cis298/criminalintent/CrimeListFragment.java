package edu.kvcc.cis298.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by amahler4096 on 10/19/2016.
 */
public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;

        // Declare a new crimeAdapter using the inner class written below in this file:
    private CrimeAdapter mAdapter;

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

            // Call the UpdateUI method to set up teh recyclerView with data:
        UpdateUI();

            // Return the view:
        return view;

    }

    private class CrimeHolder extends RecyclerView.ViewHolder {
            // Add a title variable to the viewHolder:
        public TextView mTitleTextView;

            // Constructor for the CrimeHolder:
        public CrimeHolder (View itemView) {
                // Call the parent constructor:
            super(itemView);

                // Get and assign the passed itemView to the TextView variable:
            mTitleTextView = (TextView) itemView;
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
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

                // Return a new CrimeHolder and pass in the view we just created:
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
                // Get the crime out of the crimes list:
            Crime crime = mCrimes.get(position);

                // Set the text on the viewHolder's TextView widget:
            holder.mTitleTextView.setText(crime.getTitle());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    private void UpdateUI() {
            // Using the static method on the CrimeLab class to return the singleton.
            // This will get us our one and only one instance of the CrimeLab.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

            // Get the list of crimes from the singleton CrimeLab and save to local var:
        List<Crime> crimes = crimeLab.getCrimes();

            // Create a new CrimeAdapter and send over the crime list so that it can
            // make new viewHolders and bind data to the viewHolders:
        mAdapter = new CrimeAdapter(crimes);

            // Set the Adapter on the RecyclerView:
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

}
