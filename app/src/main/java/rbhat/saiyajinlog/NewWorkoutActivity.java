package rbhat.saiyajinlog;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rbhat.saiyajin.db.ExerSet;
import rbhat.saiyajin.db.SaiyajinDBContract;
import rbhat.saiyajin.db.SaiyajinDBHelper;
import rbhat.saiyajin.db.ShowExerData;


public class NewWorkoutActivity extends Activity {

    private LinearLayout workoutList = null;
    private View currentItem = null;
    private int setCount = 1;


    private int currentExerIndex = 0;
    private boolean workoutPlanAdded = false;
    private SaiyajinDBHelper dbHelper = null;
    private int currentWorkoutPlanIndex = 0;


    List<ExerSet> currentSetReps = new ArrayList<ExerSet>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        setTitle(date);

//start
        List<String> exercises = getDBHelper().getAllExercises();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_spinner_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner workoutSpinner = (Spinner) findViewById(R.id.add_workout_spinner);
        workoutSpinner.setAdapter(adapter);
        workoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentExerIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //end

//        autoTextView = (AutoCompleteTextView) findViewById(R.id.add_workout_autotext);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                SaiyajinDBContract.EXERCISES);
//        autoTextView.setAdapter(adapter);
//        autoTextView.addTextChangedListener(new AutoTextViewTextWatcher());


        workoutList = (LinearLayout) findViewById(R.id.add_workout_sets_reps_list);
        currentItem = getLayoutInflater().inflate(R.layout.layout_add_new_item, null);
        setSetText();;
        workoutList.addView(currentItem);

    }



    public void onAddNewButtonClicked(View view) {

        addWeightRepsToList();

        ExerSet exerSet = currentSetReps.get(currentSetReps.size()-1);

        currentItem = getLayoutInflater().inflate(R.layout.layout_add_new_item, null);
        EditText weightText = getWeightText();
        weightText.setText(Double.toString(exerSet.weight));
        EditText repsText = getRepsText();
        repsText.setText(Integer.toString(exerSet.reps));
        setSetText();

        workoutList.addView(currentItem);
    }

    public void onAddNextButtonClicked(View view) {

        addWeightRepsToList();

        doneWithWorkout();

        workoutList.removeAllViews();

        currentItem = getLayoutInflater().inflate(R.layout.layout_add_new_item, null);
        setCount = 1;
        setSetText();;
        workoutList.addView(currentItem);

    }

    private void addWeightRepsToList() {

        addWorkoutPlan();

        EditText weightText = getWeightText();
        double weight = Double.parseDouble(weightText.getText().toString());
        EditText repsText = getRepsText();
        int reps = Integer.parseInt(repsText.getText().toString());

        ExerSet set = new ExerSet();
        set.weight = (int) weight;
        set.reps = reps;
        currentSetReps.add(set);
    }

    private void addWorkoutPlan() {
        if(!workoutPlanAdded) {
            dbHelper = getDBHelper();
            currentWorkoutPlanIndex = dbHelper.addWorkoutPlan(currentExerIndex);
            workoutPlanAdded = true;
        }

    }

    private EditText getWeightText() {
        return (EditText)currentItem.findViewById(R.id.add_workout_weight_text);
    }

    private EditText getRepsText() {
        return (EditText)currentItem.findViewById(R.id.add_workout_reps_edittext);
    }

    private void setSetText() {
        TextView label = (TextView) currentItem.findViewById(R.id.add_workout_set_text);
        label.setText(Integer.toString(setCount++)+". Weight");
    }

    private void doneWithWorkout() {
        workoutPlanAdded = false;
        for(int i = 0; i< currentSetReps.size(); i++) {
            ExerSet eset = currentSetReps.get(i);
            getDBHelper().addWorkout(currentWorkoutPlanIndex,  eset.weight,eset.reps, i+1);
        }
        currentSetReps.clear();
    }

    private SaiyajinDBHelper getDBHelper() {
        if(dbHelper == null)
            dbHelper = new SaiyajinDBHelper(this);
        return dbHelper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_done:
                addWeightRepsToList();
                doneWithWorkout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
