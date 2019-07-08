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
    private static final String TABLE_NAME_MY_SPACESHIP = "myspaceship";
    private static final String TABLE_NAME_REMEMBER = "remember";


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

    //spaceships columns
    private static final String SPACESHIPS_COL_1 = "id";
    private static final String SPACESHIPS_COL_2 = "name";
    private static final String SPACESHIPS_COL_3 = "src_path";
    private static final String SPACESHIPS_COL_4 = "locked";
    private static final String SPACESHIPS_COL_5 = "price";

    //myspaceship columns
    private static final String MYSPACESHIP_COL1 = "spaceship_id";

    //remember columns
    private static final String REMEMBER_COL1 = "is_remember";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_LEVELS + " ("+LEVELS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+LEVELS_COL_2+" TEXT, "+LEVELS_COL_3+" INTEGER)");
        db.execSQL("create table " + TABLE_NAME_RECORDS + " ("+RECORDS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RECORDS_COL_2+" INTEGER, "+RECORDS_COL_3+" INTEGER, "+RECORDS_COL_4+" TEXT)");
        db.execSQL("create table " + TABLE_NAME_BANK + " ("+BANK_COL_1+" INTEGER, "+BANK_COL_2+" INTEGER)");
        db.execSQL("create table " + TABLE_NAME_SPACE_SHIPS + " ("+SPACESHIPS_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SPACESHIPS_COL_2+" TEXT, "+SPACESHIPS_COL_3+" INTEGER, "+SPACESHIPS_COL_4+" INTEGER,"+SPACESHIPS_COL_5+" INTEGER )");
        db.execSQL("create table " + TABLE_NAME_MY_SPACESHIP + "("+MYSPACESHIP_COL1+" INTEGER)");
        db.execSQL("create table " + TABLE_NAME_REMEMBER + "("+REMEMBER_COL1+" INTEGER)");
        db.execSQL("INSERT INTO levels(level,speed) VALUES('Free Style',1)");
        db.execSQL("INSERT INTO levels(level,speed) VALUES('Getting Sick',2)");
        db.execSQL("INSERT INTO bank(coins_total,coins_now) VALUES(8000,0)");
        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(1,'Space Ship 1','spaceship_1',0,100)");
        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(2,'Space Ship 2','spaceship_2',1,150)");
        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(3,'Space Ship 3','spaceship_3',1,200)");
        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(4,'Space Ship 4','spaceship_4',1,250)");
        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(5,'Space Ship 5','spaceship_5',1,300)");
        db.execSQL("INSERT INTO spaceships(id,name,src_path,locked,price) VALUES(6,'Space Ship 6','spaceship_6',1,400)");
        db.execSQL("INSERT INTO myspaceship(spaceship_id) VALUES(1)");
        db.execSQL("INSERT INTO remember(is_remember) VALUES(1)");

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
        int num_affected = data.getCount();
        data.moveToNext();
        int coins_total = data.getInt(data.getColumnIndex(BANK_COL_1));
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
     * Returns top 5
     * @return
     */
    public List<UserScore> getTopHighScore(int id_level)
    {
        List<UserScore> userScoreList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT name,record FROM records where level="+id_level+ " ORDER BY record DESC limit 15";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            do {
                UserScore userScore = new UserScore();
                userScore.setLevel(id_level);
                userScore.setName(data.getString(data.getColumnIndex("name")));
                userScore.setScore(data.getInt(data.getColumnIndex("record")));
                userScoreList.add(userScore);
            } while (data.moveToNext());
        }
        data.close();
        return userScoreList;
    }

    public ArrayList<UserScore> checkScore(int id_level, int score)
    {
        ArrayList<UserScore> userScoreList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT name,record FROM records where record>"+score+" AND level="+id_level+ " ORDER BY record DESC";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            do {
                UserScore userScore = new UserScore();
                userScore.setLevel(id_level);
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
                spaceShipShop.setSrc_path(data.getString(data.getColumnIndex("src_path")));
                spaceShipShop.setLocked(data.getInt(data.getColumnIndex("locked")));
                spaceShipShop.setPrice(data.getInt(data.getColumnIndex("price")));
                spaceShipList.add(spaceShipShop);
            } while (data.moveToNext());
        }
        data.close();
        return spaceShipList;
    }

    public int getTotalCoins(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT coins_total FROM bank";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        int coins_total = data.getInt(data.getColumnIndex("coins_total"));
        data.close();
        return coins_total;
    }

    public boolean buySpaceShip(int id)
    {
        int total_coins, new_total_coins, cost_of_product;
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT coins_total FROM bank";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        total_coins = data.getInt(data.getColumnIndex("coins_total"));

        String query1 = "SELECT price FROM spaceships WHERE id="+id;
        Cursor data1 = db.rawQuery(query1, null);
        data1.moveToFirst();
        cost_of_product = data1.getInt(data1.getColumnIndex("price"));

        new_total_coins = total_coins - cost_of_product ;
        ContentValues contentValues = new ContentValues();
        contentValues.put(BANK_COL_1 , new_total_coins);
        long result = db.update(TABLE_NAME_BANK, contentValues,null, null);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(SPACESHIPS_COL_4 , 0);
        long result1 = db.update(TABLE_NAME_SPACE_SHIPS, contentValues1,"id="+id, null);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(MYSPACESHIP_COL1 , id);
        long result2 = db.update(TABLE_NAME_MY_SPACESHIP, contentValues2,null, null);

        if (result == -1 || result1 == -1 || result2 == -1 ) {
            return false;
        } else {
            return true;
        }
    }

    public int getHighScore(int id_level)
    {
        int user_score = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT record FROM records where level="+id_level+ " ORDER BY record DESC limit 1";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        if(data != null && data.moveToFirst()){
            user_score = data.getInt(data.getColumnIndex("record"));
        }
        else{
        user_score = 0;
        }
        return user_score;
    }

    //return the id of the spaceship the user has chosen to play with
    public int getSpaceShipId(){
        int spaceShipId;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM myspaceship";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        spaceShipId = data.getInt(data.getColumnIndex("spaceship_id"));
        return spaceShipId;
    }

    public String getSpaceShipSrc() {
        String spaceShipSrc;
        int spaceship_id;
        SQLiteDatabase db = getReadableDatabase();

        String id_query = "SELECT * from myspaceship";
        Cursor dataid = db.rawQuery(id_query, null);
        dataid.moveToFirst();
        spaceship_id = dataid.getInt(dataid.getColumnIndex(MYSPACESHIP_COL1));

        String src_query = "SELECT src_path FROM spaceships WHERE id="+spaceship_id;
        Cursor dataSrc = db.rawQuery(src_query, null);
        dataSrc.moveToFirst();
        spaceShipSrc = dataSrc.getString(dataSrc.getColumnIndex(SPACESHIPS_COL_3));

        return spaceShipSrc;
    }

    public boolean updateUseSpaceShip(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MYSPACESHIP_COL1 , id);

        Log.i(TAG, "updateUseSpaceShip: Update spaceShip " + id + " to " + TABLE_NAME_MY_SPACESHIP);

        long result = db.update(TABLE_NAME_MY_SPACESHIP, contentValues,null, null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateRemember(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMEMBER_COL1 , 0);

        Log.i(TAG, "updateUseSpaceShip: Update spaceShip " + 0 + " to " + TABLE_NAME_REMEMBER);

        long result = db.update(TABLE_NAME_REMEMBER, contentValues,null, null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public int ifCheckRemember() {
        int checkBoxId;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM remember";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        checkBoxId = data.getInt(data.getColumnIndex("is_remember"));
        return checkBoxId;
    }

}
