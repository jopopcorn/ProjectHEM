package com.example.user.projecthem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    int year, month, day;
    DatePicker  datePicker;
    Calendar c;
    TextView mtext;
    EditText medit;
    Button mbutton;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        c = Calendar.getInstance();

        year = c.get(c.YEAR);
        month = c.get(c.MONTH);
        day = c.get(c.DAY_OF_MONTH);

        mtext = (TextView)findViewById(R.id.textView);
        medit= findViewById(R.id.editText);
        mbutton = findViewById(R.id.button);

        checkedDay(year,month,day);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                checkedDay(year, monthOfYear, dayOfMonth);
            }
        });

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(fileName);

            }
        });


    }

    private void checkedDay(int year, int monthOfYear, int dayOfMonth) {

        mtext.setText(year + " - " + (monthOfYear+1) + " - " + dayOfMonth);


        fileName = year + "" + (monthOfYear+1) + "" + dayOfMonth + ".txt";

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData, "UTF-8");

            Toast.makeText(getApplicationContext(), "일정 저장", Toast.LENGTH_SHORT).show();
            medit.setText(str);
            mbutton.setText("수정하기");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "일정이 없음", Toast.LENGTH_SHORT).show();
            medit.setText("");
            mbutton.setText("일정 저장하기");
            e.printStackTrace();
        }

    }


    private void saveDiary(String readDay) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, Context.MODE_PRIVATE);
            String content = medit.getText().toString();

            fos.write(content.getBytes());

            fos.close();


            Toast.makeText(getApplicationContext(), "일정 저장 완료", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류오류", Toast.LENGTH_SHORT).show();
        }
    }
}
