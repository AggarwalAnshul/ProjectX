package com.example.anshulaggarwal.projectx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.example.anshulaggarwal.projectx.Configure.QUESTIONS_SETA_TABLE_NAME;
import static com.example.anshulaggarwal.projectx.Configure.QUESTIONS_SETB_TABLE_NAME;
import static com.example.anshulaggarwal.projectx.Login.DATABASE_NAME;
import static com.example.anshulaggarwal.projectx.Login.INTENT_BUNDLE_ROLLNO_KEY;
import static com.example.anshulaggarwal.projectx.Login.Tag;
import static com.example.anshulaggarwal.projectx.Login.USERINFO_DATA_TABLE_COLUMN_3;
import static com.example.anshulaggarwal.projectx.Login.USERINFO_TABLE_NAME;
import static com.example.anshulaggarwal.projectx.Login.sqLiteDatabase;

public class startTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        //getting the number of questions from the questions tables
        int currentRollno = Integer.parseInt(getIntent().getStringExtra(INTENT_BUNDLE_ROLLNO_KEY));
        Log.d(Tag, "recieved bundle: " + currentRollno);
        int set = getSet(currentRollno);

        String tableName = QUESTIONS_SETA_TABLE_NAME;
        if (set == 2) {
            tableName = QUESTIONS_SETB_TABLE_NAME;
        }

        int questionCount = getTotalQuestions(tableName);
        ArrayList<Integer> sequence = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<questionCount; i++){
            sequence.add(i);
        }
 /*randomly organising the sequence list*/
        for(int i=questionCount-1; i>0; i--){
            int j = random.nextInt(i+1);
            int temp = sequence.get(i);
            sequence.set(i, sequence.get(j));
            sequence.set(j, temp);
        }
       
        /*now generating this much questions*/



    }

    public final int getTotalQuestions(String tableName) {
        sqLiteDatabase = openOrCreateDatabase(Configure.DATABASE_NAME, Context.MODE_PRIVATE, null);
        String query = "select * from " + tableName + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        Log.d(Tag, "Total Questions are: " + count);
        return count;
    }

    public final int getSet(int rollno) {
        if (rollno % 2 == 0) {
            return 1;
        }
        return 2;
    }
}
