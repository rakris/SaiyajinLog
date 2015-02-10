package rbhat.saiyajin.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by rbhat on 15/1/15.
 */
public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener dateListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this.dateListener, year, month, day);
    }

    public void setDateListener(DatePickerDialog.OnDateSetListener dateListener) {
        this.dateListener = dateListener;
    }
}
