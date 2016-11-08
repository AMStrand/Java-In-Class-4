package edu.kvcc.cis298.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by amahler4096 on 11/2/2016.
 */
public class DatePickerFragment extends DialogFragment {

        // Set a string for the key for the extras in the intent
        // used as the return data for the result of this dialog closing:
        // (public so we can access it from CrimeFragment)
    public static final String EXTRA_DATE =
            "edu.kvcc.cis298.cis298inclass4.date";

        // Key for the date that is sent over to the date picker;
    private static final String ARG_DATE = "date";

        // Local variable for the date picker:
    private DatePicker mDatePicker;

        // Static method that another fragment can use to get
        // a new datePickerFragment with a correct set date:
    public static DatePickerFragment newInstance(Date date) {
            // Make a bundle and add the date string and value:
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
            // Create a new datePickerFragment, set its args, and return it:
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the date that was passed over:
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
            // Make a calendar and set the date to the one passed in:
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
            // Save each part of the date in a variable to feed to the date picker:
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create a view and inflate it to create the dialog view:
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

            // Connect the date picker widget to the object:
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);

            // Initialize the date picker to the passed in date:
        mDatePicker.init(year, month, day, null);

            // Create an alert dialog, giving it a title and an ok button
            // by chaining together the methods of the properties to set,
            // by using the Builder method in the AlertDialog class:
            // (has to finish with the create() method)
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    // Take in the selected date from the DatePicker:
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                    // Save the date on a calendar:
                                Date date = new GregorianCalendar(year, month, day)
                                        .getTime();
                                    // Call the SendResult method and pass over the
                                    // activity result of ok and the passed in date:
                                SendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }

    private void SendResult(int resultCode, Date date) {
            // Safety check for if fragment is null:
        if (getTargetFragment() == null) {
            return;
        }
            // Create a new intent object to hold the return data and add the date as an extra:
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
            // Get the target fragment and call onActivityResult, sending over
            // the request code, result code, and the intent with our data.
            // The crime fragment can then handle the work using the information.
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

}
