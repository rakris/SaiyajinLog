package rbhat.saiyajinlog;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import rbhat.saiyajin.db.SaiyajinDBContract;
import rbhat.saiyajin.db.SaiyajinDBHelper;
import rbhat.saiyajin.db.ShowExerData;
import rbhat.saiyajin.listitems.ShowListViewAdapter;


public class ShowActivity extends Activity {


    private SaiyajinDBHelper dbHelper = null;

    private ListView showListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        //getDbHelper().onUpgrade(getDbHelper().getWritableDatabase(), 1, 1);

        setTitle(getResources().getString(R.string.show_activity_title));


        showListView = (ListView) findViewById(R.id.show_listview);
        View headerView = getLayoutInflater().inflate(R.layout.layout_listview_header, null);
        showListView.addHeaderView(headerView);
        final ShowListViewAdapter listAdapter = new ShowListViewAdapter(this, null);
        showListView.setAdapter(listAdapter);

        List<String> exercises = getDbHelper().getAllExercises();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_spinner_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner exercises_spinner = (Spinner) findViewById(R.id.exercises_spinner);
        exercises_spinner.setAdapter(adapter);
        exercises_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<ShowExerData> workouts = getDbHelper().getShowWorkoutFor(position);
                listAdapter.setShowExers(workouts);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private SaiyajinDBHelper getDbHelper() {
        if(dbHelper == null)
            dbHelper = new SaiyajinDBHelper(this);
        return dbHelper;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
}
