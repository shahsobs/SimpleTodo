package in.sobs.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SNS on 24-Jun-16.
 */
public class SQLdbAdapter {
    SQLHelper helper;

    public SQLdbAdapter(Context context) {
        helper = new SQLHelper(context);
    }

    public long insertTask(String task) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.TASK, task);
        long id = db.insert(SQLHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public  void insertTask3(String task){

    }

    public List<String> readTask3(){
        String[] me = {"hello","world"};
        List<String> l1 = Arrays.asList(me);
                return  l1;
    }



    public List<String> readTask(int what) {
        SQLiteDatabase db = helper.getWritableDatabase();
//        int[] idArray;
        List<String> getAllTask = new ArrayList<String>();
        List<String> getAllTaskId = new ArrayList<String>();

        // select _id , task column from DB.
        String[] columns = { SQLHelper.UID, SQLHelper.TASK };
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);
//        StringBuffer sb = new StringBuffer();
        int x=0;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String task = cursor.getString(1);

            getAllTask.add(task);
            getAllTaskId.add(Integer.toString(id));

//            sb.append("\n");
//            sb.append(cid + " " + name + " " + password + "\n");
        }
        if (what==1)
            return getAllTask;
        else {
            return getAllTaskId;
        }
    }

    public String deleteAll() {//only for my use
        SQLiteDatabase db = helper.getWritableDatabase();
//        int count = db.delete(SQLHelper.TABLE_NAME, "");
        db.execSQL("DELETE from " + SQLHelper.TABLE_NAME);
//        String stringCount = Integer.toString(count);
//        return  stringCount;
        return "";
    }

    public ArrayList<String> showAllTask() {
        SQLiteDatabase db = helper.getWritableDatabase();
//        int[] idArray;
        ArrayList<String> showAllTask = new ArrayList<>();

        // select _id , task column from DB.
        String[] columns = { SQLHelper.UID, SQLHelper.TASK };
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);
//        StringBuffer sb = new StringBuffer();
        int x=0;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String stringId = Integer.toString(id);
            String task = cursor.getString(1);

            showAllTask.add(stringId);
            showAllTask.add(task);
//            sb.append("\n");
//            sb.append(cid + " " + name + " " + password + "\n");
        }

        return showAllTask;
    }

    public int deleteOne(int position) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        db.execSQL("DELETE from " + SQLHelper.TABLE_NAME);
//        return 1;
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(SQLHelper.TABLE_NAME, SQLHelper.UID+" =?", new String[]{Integer.toString(position)});
        return  count;
//        String stringCount = Integer.toString(count);
//        return "Number of rows deleted is "+stringCount;
    }

    public int updateTask(String idValue, String taskData) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.TASK, taskData);
        int count = db.update(SQLHelper.TABLE_NAME, cv, SQLHelper.UID+" =?", new String[]{idValue});
        return  count;
//        String stringCount = Integer.toString(count);
//        return "Number of rows updated is "+stringCount;
    }

    static class SQLHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "taskdb";
        private static final String TABLE_NAME = "tasktable";
        private static final int DATABASE_VERSION = 1;
        // create column var
        private static final String UID = "_id";
        private static final String TASK = "task";

//        private static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + UID
//                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "" + TASK + " TEXT, " + PASSWORD + " VARCHAR(255));";

        private static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + UID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "" + TASK + " TEXT);";
        private static final String DROP_TABLE = "DROP TABLE if EXISTS " + TABLE_NAME;

        private Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
//            L.t(context, "Constructor was called.");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE...
            try {
                db.execSQL(CREATE);
//                L.t(context, "OnCreate was called.");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                L.t(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            try {
                L.t(context, "onUpgrade was called.");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                L.t(context, "" + e);
            }
        }

    }

}

