package com.hit.ispace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddScore extends AppCompatActivity {

    private static final String TAG = "AddScore";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd;
    private EditText editTextLevel,editTextUser,editTextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score);
        editTextLevel = (EditText) findViewById(R.id.level);
        editTextUser = (EditText) findViewById(R.id.user_name);
        editTextScore = (EditText) findViewById(R.id.score);
        btnAdd = (Button) findViewById(R.id.add_score);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newLevel = editTextLevel.getText().toString();
                String newUser = editTextUser.getText().toString();
                String newScore = editTextScore.getText().toString();
                if ((editTextLevel.length() != 0)&&(editTextUser.length() != 0)&&(editTextScore.length() != 0))   {
                    AddData(newLevel,newUser,newScore);
                    editTextLevel.setText("");
                    editTextUser.setText("");
                    editTextScore.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
                }

            }
        });
    }

    public void AddData(String level, String name, String score) {
        boolean insertData = mDatabaseHelper.addData(level,name,score);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}