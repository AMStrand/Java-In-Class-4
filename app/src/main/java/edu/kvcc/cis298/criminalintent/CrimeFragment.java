package edu.kvcc.cis298.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by dbarnes on 10/5/2016.
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

        // Method to return the fragment to be created:
    public static CrimeFragment newInstance(UUID crimeID) {
            // Create a bundle and add the crime ID:
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);
            // Create a new crime fragment using the args and return its info:
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // Created objects (not int, string, etc) are serialized when saved, so we have to get
            // the serializable extra from the arguments we stored to find the specified crime's UUID:
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
            // Use that ID to find that crime:
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Use the inflator to inflate the layout file we want to use with this fragment
        //The first parameter is the layout resource. The second is the passed in
        //container that is the parent widget.
        //The last parameter is whether or not to statically assign the fragment
        //to the parent (FrameLayout) container.
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        //Get a reference to the EditText Widget in the layout file.
        //instead of just calling findViewById, which is a activity method,
        //we need to call the findViewById that is part of the view we just created.
        //aside from that, it operates the same.
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //We aren't doing anything here.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //We aren't doing anything here either.
            }
        });

        //Set Date Button text
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckbox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the crimes solve property
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}








