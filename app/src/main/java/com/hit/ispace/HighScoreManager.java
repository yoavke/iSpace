package com.hit.ispace;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HighScoreManager
{
    DatabaseHelper mDatabaseHelper;
    private List<UserScore> highScores = new ArrayList<>();

    public HighScoreManager(){}

    //Push user scores into database
    public void pushUserScore(String name, int score)
    {
        UserScore userScore= new UserScore(name, score);
    }

    //Request highest scores from database
    public void getHighScore(final highScoreListener listener)
    {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String query = "SELECT name,record FROM records where level=1 ORDER BY record DESC limit 5";
        Cursor data = db.rawQuery(query, null);
        UserScore temp;
        while(data.moveToNext()){
            temp = new UserScore(data.getString(1),data.getInt(2));
            highScores.add(temp);
        }
    }

    public interface highScoreListener
    {
        void onChange(List<UserScore> highScores);
        void onError();
    }


}
