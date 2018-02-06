package com.example.sachin.timify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by SACHIN on 10/4/2017.
 */

public class AddSchedule extends AppCompatActivity{
    EditText subject,teacher,room;
    Button button ;
    Spinner type;
    EditText from;
    EditText to;
    int pos,checker=0,day;
    String teach,sub,kamra,from1,to1,typ;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule);

        Bundle b = getIntent().getExtras();
        pos = b.getInt("ID");
        day=b.getInt("day");
        checker=b.getInt("update");
        teach = b.getString("teacher");             //Getting the values from the Bundle
        sub = b.getString("subject");               //These are the values which are sent an edit
        kamra = b.getString("room");                //button is called
        from1 = b.getString("from");
        to1 = b.getString("to");
        typ=b.getString("type");

        button = (Button) findViewById(R.id.button20);
        from = (EditText) findViewById(R.id.editText2);
        to = (EditText) findViewById(R.id.editText3);
        type = (Spinner) findViewById(R.id.spinner);
        room = (EditText) findViewById(R.id.editText30);
        subject = (EditText) findViewById(R.id.editText);
        teacher = (EditText) findViewById(R.id.editText90);
        type= (Spinner) findViewById(R.id.spinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {            //Cancel Button Work
                finish();
            }
        });

        if (teach != null && sub != null && kamra != null && from1 != null && to1 != null) {
            fillData(teach, sub, kamra, from1, to1,typ);        //Checks if edit is called or not
        }

        /**
         * This is our Time Picker
         */
        from.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour=selectedHour+"",minute=selectedMinute+"";
                        if(selectedHour/10==0)
                        {
                            hour="0"+hour;
                        }
                        if(selectedMinute/10==0)
                        {
                            minute="0"+minute;
                        }
                        from.setText(hour + ":" + minute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        /**
         * This is again a time picker
         */

        to.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(AddSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String hour=selectedHour+"",minute=selectedMinute+"";
                        if(selectedHour/10==0)
                        {
                            hour="0"+hour;
                        }
                        if(selectedMinute/10==0)
                        {
                            minute="0"+minute;
                        }
                        to.setText(hour + ":" + minute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }


    public void fillData(String teacher1,String subject1,String room1,String from1,String to1,String type)
    {
        teacher.setText(teacher1);
        subject.setText(subject1);
        from.setText(from1);
        to.setText(to1);
        room.setText(room1);

    }

    /**
     *  This method is called when Save button is called
     */

    public void save(View v)
    {
        Log.d("SAVE CALLED","call hua");
        String strTeacher = teacher.getText().toString();
        String strSubject = subject.getText().toString();
        String strFrom = from.getText().toString();
        String strTo = to.getText().toString();
        String strRoom = room.getText().toString();

        if(TextUtils.isEmpty(strSubject)) {
            subject.setError("Enter subject here");
            return;
        }
        if(TextUtils.isEmpty(strFrom)) {
            from.setError("Enter starting time");
            return;
        }
        if(TextUtils.isEmpty(strTo)) {
            to.setError("Enter ending time");
            return;
        }
        if(TextUtils.isEmpty(strRoom)) {
            room.setError("Enter Room No.");
            return;
        }
        if(TextUtils.isEmpty(strTeacher)) {
            teacher.setError("Enter Teacher Name");
            return;
        }
        else
        {
        ContentValues values=new ContentValues();
        values.put(Data.SUBJECT, String.valueOf(subject.getText()));
        values.put(Data.TEACHER, String.valueOf(teacher.getText()));
        values.put(Data.FROM, String.valueOf(from.getText()));
        values.put(Data.ROOM, String.valueOf(room.getText()));
        values.put(Data.TO, String.valueOf(to.getText()));
        values.put(Data.TYPE, String.valueOf(type.getSelectedItem()));
        values.put(Data.DAY,pos);
        Toast.makeText(this,day+"",Toast.LENGTH_SHORT).show();
        if(checker==0)
        {getContentResolver().insert(Data.CONTENT_URI,values);
        }
        else
        {
            String where=Data.TEACHER+" = ? AND " +
                    Data.SUBJECT+" = ? AND "+
                    Data.ROOM+" = ? AND "+
                    Data.FROM+" = ? AND "+
                    Data.TO+" = ? AND "+
                    Data.DAY+" = ? ";
            String [] s={teach,sub,kamra,from1,to1,String.valueOf(day)};
            getContentResolver().update(Data.CONTENT_URI,values,where,s);
        }
        finish();
        }
    }


}