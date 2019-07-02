package com.hit.ispace;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {


    //database name
    private static final String DATABASE_NAME = "ispace.db";

    //tables names
    private static final String TABLE_NAME_LEVELS = "levels";
    private static final String TABLE_NAME_RECORDS = "records";
    private static final String TABLE_NAME_BANK = "bank";
    private static final String TABLE_NAME_SPACE_SHIPS = "spaceships";

    //levels columns
    private static final String LEVELS_COL_1 = "id";
    private static final String LEVELS_COL_2 = "level";
    private static final String LEVELS_COL_3 = "speed";

    //records columns
    private static final String RECORDS_COL_1 = "id";
    private static final String RECORDS_COL_2 = "level";
    private static final String RECORDS_COL_3 = "record";
    private static final String RECORDS_COL_4 = "name";

    //bank columns
    private static final String BANK_COL_1 = "coins_total";
    private static final String BANK_COL_2 = "coins_now";

    //spaceships
    private static final String SPACESHIPS_COL_1 = "id";
    private static final String SPACESHIPS_COL_2 = "name";
    private static final String SPACESHIPS_COL_3 = "src_path";
    private static final String SPACESHIPS_COL_4 = "locked";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_LEVELS + " ("+LEVELS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+LEVELS_COL_2+" TEXT, "+LEVELS_COL_3+" INTEGER)");
        db.execSQL("create table " + TABLE_NAME_RECORDS + " ("+RECORDS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RECORDS_COL_2+" INTEGER, "+RECORDS_COL_3+" INTEGER, "+RECORDS_COL_4+" TEXT)");
        db.execSQL("create table " + TABLE_NAME_BANK + " ("+BANK_COL_1+" INTEGER, "+BANK_COL_2+" INTEGER)");
        db.execSQL("create table " + TABLE_NAME_SPACE_SHIPS + " ("+SPACESHIPS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SPACESHIPS_COL_2+" TEXT, "+SPACESHIPS_COL_3+" TEXT, "+SPACESHIPS_COL_4+" INTEGER)");
        db.execSQL("INSERT INTO levels(level,speed) VALUES('Free Style',1)");
        db.execSQL("INSERT INTO levels(level,speed) VALUES('Getting Sick',2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_LEVELS);
        onCreate(db);
    }

    public boolean addNewRecord(int level , String name , int score ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECORDS_COL_2, level);
        contentValues.put(RECORDS_COL_4, name);
        contentValues.put(RECORDS_COL_3, score);

        Log.d(TAG, "addNewRecord: Adding " + level +"," +  name + ","+ score + " to " + TABLE_NAME_RECORDS);

        long result = db.insert(TABLE_NAME_RECORDS, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
