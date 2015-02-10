package rbhat.saiyajinlog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rbhat.saiyajin.db.BrowseListData;
import rbhat.saiyajin.db.ExerSet;
import rbhat.saiyajin.db.SaiyajinDBHelper;
import rbhat.saiyajin.listitems.BrowseListViewAdapter;
import rbhat.saiyajin.listitems.ShowListViewAdapter;
import rbhat.saiyajin.utils.DatePickerFragment;
import rbhat.saiyajin.utils.Utilities;


public class BrowseActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private Button dateButton;
    private SaiyajinDBHelper dbHelper;
    private ListView browseList;
    private BrowseListViewAdapter browseListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        setTitle(getResources().getString(R.string.title_activity_browse));

        String dateString = Utilities.getDateString(new Date());

        browseList = (ListView) findViewById(R.id.browse_listview);
        browseListViewAdapter = new BrowseListViewAdapter(this, getDBHelper().getShowWorkoutOn(dateString));
        browseList.setAdapter(browseListViewAdapter);


        dateButton = (Button)findViewById(R.id.date_Button);
        dateButton.setText(dateString);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setDateListener(BrowseActivity.this);
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }

    private SaiyajinDBHelper getDBHelper() {
        if(dbHelper == null)
            dbHelper = new SaiyajinDBHelper(this);
        return dbHelper;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            dateButton.setText(""+selectedMonth+"/"+selectedDay+"/"+selectedYear);
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String monthString = Integer.toString(monthOfYear+1);
        if(monthOfYear+1 < 10)
            monthString = "0"+(monthOfYear+1);
        String dayString = Integer.toString(dayOfMonth);
        if(dayOfMonth < 10)
            dayString = "0"+dayOfMonth;

        String dateString = monthString+"/"+dayString+"/"+year;

        dateButton.setText(dateString);

        List<BrowseListData> workouts = getDBHelper().getShowWorkoutOn(dateString);

        browseListViewAdapter.onDateWorkoutChanged(workouts);

    }
}
