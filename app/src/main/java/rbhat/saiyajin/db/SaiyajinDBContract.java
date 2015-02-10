package rbhat.saiyajin.db;

import android.provider.BaseColumns;

/**
 * Created by rbhat on 8/1/15.
 */
public final class SaiyajinDBContract {
    private SaiyajinDBContract() {}

    public static final String[] EXERCISES = new String[] {
            "Bench Press", "Dead lift", "Overhead Press", "Squat",
            "Bent Over Row", "One Arm Row", "Inclined Dumbbell Press",
            "Triceps Extension", "Triceps Push Down", "Shrugs", "One Arm Triceps Extension",
            "Seated Cable Rows", "Lat Pull Down", "Dumbbell Curls",
            "Hammer Curls", "Preacher Curls", "Concentration Curls", "Cable Curls",
            "Leg Press", "Leg Extension", "Hamstring Curls"
    };

    public static abstract class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "Exercise";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static abstract class WorkoutPlanEntry implements  BaseColumns {
        public static final String TABLE_NAME = "WorkoutPlan";
        public static final String COLUMN_NAME_EXERID = "exer_id";
        public static final String COLUMN_NAME_WDATE = "wdate";
    }

    public static abstract class WorkoutEntry implements BaseColumns {
        public static final String TABLE_NAME = "Workout";
        public static final String COLUMN_NAME_WPID = "wpid";
        public static final String COLUMN_NAME_SET = "wset";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_reps = "reps";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String[] SQL_CREATE_ENTRIES = new String[] {
            "CREATE TABLE IF NOT EXISTS "+ ExerciseEntry.TABLE_NAME + "("  +
                    ExerciseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
                    ExerciseEntry.COLUMN_NAME_NAME + TEXT_TYPE +
                    ")",
            "CREATE TABLE IF NOT EXISTS "+ WorkoutPlanEntry.TABLE_NAME + "("  +
                    WorkoutPlanEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
                    WorkoutPlanEntry.COLUMN_NAME_EXERID + " INTEGER REFERENCES "
                    + ExerciseEntry.TABLE_NAME +"("+ExerciseEntry._ID+")"+ COMMA_SEP +
                    WorkoutPlanEntry.COLUMN_NAME_WDATE + TEXT_TYPE +
            ")",
            "CREATE TABLE IF NOT EXISTS "+ WorkoutEntry.TABLE_NAME + "("  +
                    WorkoutEntry.COLUMN_NAME_WPID + " INTEGER NOT NULL REFERENCES " + WorkoutPlanEntry.TABLE_NAME
            + "("+WorkoutPlanEntry._ID+")" + COMMA_SEP + WorkoutEntry.COLUMN_NAME_SET + " INTEGER NOT NULL"+ COMMA_SEP +
                    WorkoutEntry.COLUMN_NAME_WEIGHT+" INTEGER" + COMMA_SEP + WorkoutEntry.COLUMN_NAME_reps + " INTEGER" +
                    COMMA_SEP + " PRIMARY KEY ("+WorkoutEntry.COLUMN_NAME_WPID +COMMA_SEP+ WorkoutEntry.COLUMN_NAME_SET+"))"

    };

    public static final String[] SQL_DELETE_ENTRIES = new String[] {
            "DROP TABLE IF EXISTS " + ExerciseEntry.TABLE_NAME,
            "DROP TABLE IF EXISTS " + WorkoutPlanEntry.TABLE_NAME,
            "DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME

    };

    public static final String SQL_SHOW_WORKOUT_EXERCISES =
            "SELECT WorkoutPlan.wdate, MAX(Workout.weight), Workout.reps FROM Workout, WorkoutPlan where "  +
                    "Workout.wpid=WorkoutPlan._id and WorkoutPlan.exer_id=? group by WorkoutPlan.wdate";

    public static final String SQL_SHOW_WORKOUT_ON_DATE =
            "SELECT WorkoutPlan.exer_id, Workout.weight, Workout.reps FROM Workout, " +
                    "WorkoutPlan where Workout.wpid=WorkoutPlan._id and WorkoutPlan.wdate = ?";
}
