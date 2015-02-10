package rbhat.saiyajin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rbhat.saiyajin.utils.Utilities;

/**
 * Created by rbhat on 8/1/15.
 */
public class SaiyajinDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Saiyajin.db";

    public SaiyajinDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String sql : SaiyajinDBContract.SQL_CREATE_ENTRIES)
            db.execSQL(sql);
        for(String exercise : SaiyajinDBContract.EXERCISES) {
            ContentValues values = new ContentValues();
            values.put(SaiyajinDBContract.ExerciseEntry.COLUMN_NAME_NAME, exercise);
            db.insert(SaiyajinDBContract.ExerciseEntry.TABLE_NAME, null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String sql : SaiyajinDBContract.SQL_DELETE_ENTRIES)
            db.execSQL(sql);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }

    public void addWorkout(int workoutPlanIndex, double weight, int reps, int set) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SaiyajinDBContract.WorkoutEntry.COLUMN_NAME_WPID, workoutPlanIndex);
        values.put(SaiyajinDBContract.WorkoutEntry.COLUMN_NAME_SET, set);
        values.put(SaiyajinDBContract.WorkoutEntry.COLUMN_NAME_WEIGHT, weight);
        values.put(SaiyajinDBContract.WorkoutEntry.COLUMN_NAME_reps, reps);
        db.insert(SaiyajinDBContract.WorkoutEntry.TABLE_NAME, null, values);
    }

    public int getExerciseIndex(String exercise) {
        int currentExerIndex = 0;
        for(int i = 0; i < SaiyajinDBContract.EXERCISES.length; i++) {
            if(SaiyajinDBContract.EXERCISES[i].equals(exercise))
            {
                currentExerIndex = i;
                break;
            }
        }
        return currentExerIndex;
    }

    public int addWorkoutPlan(int exerIndex) {
        Date curDate = new Date();

        String dateString = Utilities.getDateString(curDate);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SaiyajinDBContract.WorkoutPlanEntry.COLUMN_NAME_EXERID, exerIndex);
        values.put(SaiyajinDBContract.WorkoutPlanEntry.COLUMN_NAME_WDATE, dateString);
        return (int) db.insert(SaiyajinDBContract.WorkoutPlanEntry.TABLE_NAME, null, values);
    }

    public List<String> getAllExercises() {

        List<String> exercises = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SaiyajinDBContract.ExerciseEntry.COLUMN_NAME_NAME
        };
        Cursor cur = db.query(SaiyajinDBContract.ExerciseEntry.TABLE_NAME, projection, null, null, null, null, null );
        cur.moveToFirst();
        exercises.add(cur.getString(cur.getColumnIndex(SaiyajinDBContract.ExerciseEntry.COLUMN_NAME_NAME)));
        while(cur.moveToNext()) {
            exercises.add(cur.getString(cur.getColumnIndex(SaiyajinDBContract.ExerciseEntry.COLUMN_NAME_NAME)));
        }

        return exercises;
    }

    public List<ShowExerData> getShowWorkoutFor(int exerciseIndex) {

        List<ShowExerData> workouts = new ArrayList<ShowExerData>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SaiyajinDBContract.SQL_SHOW_WORKOUT_EXERCISES,
                new String[] {Integer.toString(exerciseIndex)});
        if(cur.getCount() == 0)
            return null;
        cur.moveToFirst();
        ShowExerData data = new ShowExerData();
        data.dateString = cur.getString(0);
        if(data.dateString == null || data.dateString.trim().isEmpty())
            return workouts;
        data.weight = cur.getInt(1);
        data.reps = cur.getInt(2);

        workouts.add(data);
        while(cur.moveToNext()) {
            ShowExerData sdata = new ShowExerData();
            sdata.dateString = cur.getString(0);
            sdata.weight = cur.getInt(1);
            sdata.reps = cur.getInt(2);
            workouts.add(sdata);
        }

        return workouts;
    }

    public List<BrowseListData> getShowWorkoutOn(String dateString) {
        //String dateString = Utilities.getDateString(date);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SaiyajinDBContract.SQL_SHOW_WORKOUT_ON_DATE,
                new String[] {dateString});
        if(cur.getCount() == 0)
            return null;

        //Map<String, List<ExerSet>> workouts = new HashMap<String, List<ExerSet>>();
        List<BrowseListData> workouts = new ArrayList<BrowseListData>();

        cur.moveToFirst();

        String name = SaiyajinDBContract.EXERCISES[cur.getInt(0)];
        List<ExerSet> exers = new ArrayList<ExerSet>();
        exers.add(new ExerSet(cur.getInt(1), cur.getInt(2)));

        BrowseListData listData = new BrowseListData();
        listData.name = name;
        listData.exerSets = exers;

        workouts.add(listData);

        while(cur.moveToNext()) {
            String ename = SaiyajinDBContract.EXERCISES[cur.getInt(0)];
            BrowseListData listItem = new BrowseListData();
            listItem.name = ename;
            BrowseListData existingItem = workoutContains(workouts, ename);
            if(existingItem != null)
                listItem = existingItem;
            else {
                listItem.exerSets = new ArrayList<ExerSet>();
                workouts.add(listItem);
            }
            listItem.exerSets.add(new ExerSet(cur.getInt(1), cur.getInt(2)));

        }

        return workouts;
    }

    private BrowseListData workoutContains(List<BrowseListData> list, String exerName) {
        for(int i = list.size()-1; i >=0 ; i--) {
            BrowseListData listData = list.get(i);
            if(listData.name.equals(exerName))
                return listData;
        }
        return null;
    }

}
