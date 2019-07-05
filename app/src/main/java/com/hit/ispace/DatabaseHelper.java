package com.hit.ispace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static java.sql.Types.NULL;

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
    private static final String SPACESHIPS_COL_5 = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table " + TABLE_NAME_LEVELS + " ("+LEVELS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+LEVELS_COL_2+" TEXT, "+LEVELS_COL_3+" INTEGER)");
//        db.execSQL("create table " + TABLE_NAME_RECORDS + " ("+RECORDS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RECORDS_COL_2+" INTEGER, "+RECORDS_COL_3+" INTEGER, "+RECORDS_COL_4+" TEXT)");
//        db.execSQL("create table " + TABLE_NAME_BANK + " ("+BANK_COL_1+" INTEGER, "+BANK_COL_2+" INTEGER)");
//        db.execSQL("create table " + TABLE_NAME_SPACE_SHIPS + " ("+SPACESHIPS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SPACESHIPS_COL_2+" TEXT, "+SPACESHIPS_COL_3+" INTEGER, "+SPACESHIPS_COL_4+" INTEGER,"+SPACESHIPS_COL_5+" INTEGER )");
//        db.execSQL("INSERT INTO levels(level,speed) VALUES('Free Style',1)");
//        db.execSQL("INSERT INTO levels(level,speed) VALUES('Getting Sick',2)");
//        db.execSQL("INSERT INTO bank(coins_total,coins_now) VALUES(8000,0)");
//        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(1,'Space Ship 1','R.drawable.spaceship1',1,500)");
//        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(2,'Space Ship 2','R.drawable.spaceship2',1,1000)");
//        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(3,'Space Ship 3','R.drawable.spaceship3',1,2000)");
//        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(4,'Space Ship 4','R.drawable.spaceship4',1,3000)");
//        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(5,'Space Ship 5','R.drawable.spaceship5',1,4000)");
//        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(6,'Space Ship 6','R.drawable.spaceship6',1,5000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_LEVELS);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_BANK);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_SPACE_SHIPS);
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

    public boolean updateCoin(int coins) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM bank";
        Cursor data = db.rawQuery(query, null);
        data.moveToLast();
        int coins_total = data.getInt(data.getColumnIndex("coins_now"));
        int new_coins = coins_total+coins;

        ContentValues contentValues = new ContentValues();
        contentValues.put(BANK_COL_1 , new_coins);

        Log.i(TAG, "updateCoin: Update coins " + new_coins + " to " + TABLE_NAME_RECORDS);

        long result = db.update(TABLE_NAME_BANK, contentValues,null, null);

        //if date as update incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Returns top 5 on Free Style game
     * @return
     */
    public List<UserScore> getTopFreeStyle()
    {
        List<UserScore> userScoreList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT name,record FROM records where level=1 ORDER BY record DESC limit 5";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            do {
                UserScore userScore = new UserScore();
                userScore.setName(data.getString(data.getColumnIndex("name")));
                userScore.setScore(data.getInt(data.getColumnIndex("record")));
                userScoreList.add(userScore);
            } while (data.moveToNext());
        }
        data.close();
        return userScoreList;
    }

    /**
     * Returns top 5 on Faster game
     * @return
     */
    public List<UserScore> getTopFaster(){
        List<UserScore> userScoreList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT name,record FROM records where level=2 ORDER BY record DESC limit 5";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            do {
                UserScore userScore = new UserScore();
                userScore.setName(data.getString(data.getColumnIndex("name")));
                userScore.setScore(data.getInt(data.getColumnIndex("record")));
                userScoreList.add(userScore);
            } while (data.moveToNext());
        }
        data.close();
        return userScoreList;
    }

    /**
     * Returns top 5 on Getting Sick game
     * @return
     */
    public List<UserScore> getTopGettingSick(){
        List<UserScore> userScoreList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT name,record FROM records where level=3 ORDER BY record DESC limit 5";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            do {
                UserScore userScore = new UserScore();
                userScore.setName(data.getString(data.getColumnIndex("name")));
                userScore.setScore(data.getInt(data.getColumnIndex("record")));
                userScoreList.add(userScore);
            } while (data.moveToNext());
        }
        data.close();
        return userScoreList;
    }

    public List<SpaceShipShop> getAllSpaceShip(){
        List<SpaceShipShop> spaceShipList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM spaceships";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            do {
                SpaceShipShop spaceShipShop = new SpaceShipShop();
                spaceShipShop.setId(data.getInt(data.getColumnIndex("id")));
                spaceShipShop.setName_ship(data.getString(data.getColumnIndex("name")));
                spaceShipShop.setSrc_path(data.getInt(data.getColumnIndex("src_path")));
                spaceShipShop.setLocked(data.getInt(data.getColumnIndex("locked")));
                spaceShipShop.setPrice(data.getInt(data.getColumnIndex("price")));
                spaceShipList.add(spaceShipShop);
            } while (data.moveToNext());
        }
        data.close();
        return spaceShipList;
    }

    public int getTotalCoins(){
        int total_coins = NULL ;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT coins_total FROM bank";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        int coins_total = data.getInt(data.getColumnIndex("coins_total"));
        data.close();
        return coins_total;
    }

    public boolean buySpaceShip(int cost_of_product)
    {
        int total_coins = NULL , new_total_coins;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT coins_total FROM bank";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        int coins_total = data.getInt(data.getColumnIndex("coins_total"));
        new_total_coins = total_coins - cost_of_product ;
        ContentValues contentValues = new ContentValues();
        contentValues.put(BANK_COL_1 , new_total_coins);

        Log.i(TAG, "buySpaceShip: Update coins " + new_total_coins + " to " + TABLE_NAME_RECORDS);

        long result = db.update(TABLE_NAME_BANK, contentValues,null, null);

        //if date as update incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}
