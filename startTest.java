package com.example.anshulaggarwal.projectx;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class startTest extends AppCompatActivity implements View.OnClickListener {

    int currentIndex = 0, questionCount = 0;
    TextView tv_question;
    Button btnA, btnB, btnC, btnD;
    ArrayList<ArrayList<String>> questions = new ArrayList<>();
    String currentAnswer = "";
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        tv_question = (TextView) findViewById(R.id.tv_question);
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);


        //getting the number of questions from the questions tables
        int currentRollno = Integer.parseInt(getIntent().getStringExtra(INTENT_BUNDLE_ROLLNO_KEY));
        Log.d(Tag, "recieved bundle: " + currentRollno);
        int set = getSet(currentRollno);

        String tableName = QUESTIONS_SETA_TABLE_NAME;
        if (set == 2) {
            tableName = QUESTIONS_SETB_TABLE_NAME;
        }

        questionCount = getTotalQuestions(tableName);

        /*storing the questions here in the arrayList*/
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        String query = "select * from " + tableName + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 1; i < 7; i++) {
                temp.add(cursor.getString(i));
            }
            questions.add(temp);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();

        Random random = new Random();
        for (int i = questionCount - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            ArrayList<String> temp =  questions.get(i);
            questions.set(i, questions.get(j));
            questions.set(j, temp);
        }
        /*randomly organising the sequence list*/


        generateQuestion(currentIndex);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    private void generateQuestion(int currentIndex) {
        if (currentIndex == questionCount) {
            Toast.makeText(this, "The quiz has ended: Score: " + score, Toast.LENGTH_SHORT).show();
            Log.d(Tag, " The quiz has ended with score: " + score);
            startActivity(new Intent(startTest.this, Login.class));
            startTest.this.finish();
        } else {
            tv_question.setText(questions.get(currentIndex).get(0));
            btnA.setText(questions.get(currentIndex).get(1));
            btnB.setText(questions.get(currentIndex).get(2));
            btnC.setText(questions.get(currentIndex).get(3));
            btnD.setText(questions.get(currentIndex).get(4));
            currentAnswer = questions.get(currentIndex).get(5);
        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnA:
                if (currentAnswer.equalsIgnoreCase("A")) {
                    score += 1;
                }
                currentIndex += 1;
                generateQuestion(currentIndex);
                break;
            case R.id.btnB:
                if (currentAnswer.equalsIgnoreCase("B")) {
                    score += 1;
                }
                currentIndex += 1;
                generateQuestion(currentIndex);
                break;
            case R.id.btnC:
                if (currentAnswer.equalsIgnoreCase("C")) {
                    score += 1;
                }
                currentIndex += 1;
                generateQuestion(currentIndex);
                break;
            case R.id.btnD:
                if (currentAnswer.equalsIgnoreCase("D")) {
                    score += 1;
                }
                currentIndex += 1;
                generateQuestion(currentIndex);
                break;
        }
    }
}
