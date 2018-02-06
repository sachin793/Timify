package com.example.sachin.timify;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by SACHIN on 10/12/2017.
 */

/**
 * This is the activity which is showed when a notification is clicked
 */

public class View_Schedule extends AppCompatActivity {
    ListView listView;
    ArrayList<Item> arraylist=new ArrayList<Item>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
       listView= (ListView) findViewById(R.id.listView);
        CustomAdapter adapter=new CustomAdapter(this,arraylist);
        listView.setAdapter(adapter);
        prepareList(this);
    }



    public void prepareList(Context c)
    {
        int day=0;
        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        switch (dayLongName)
        {
            case "Monday":  day=0;
                break;
            case "Tuesday":day=1;
                break;
            case "Wednesday":day=2;
                break;
            case "Thursday":day=3;
                break;
            case "Friday":day=4;
                break;
            case "Saturday":day=5;
                break;
            case "Sunday":day=6;
                break;
            default:day=0;
        }
        String where=" day= ? ";
        String []data={day+""};
        Cursor cr=c.getContentResolver().query(Data.CONTENT_URI,null,where,data,null);
        while(cr.moveToNext())
        {
            String subject,type,timing;
            subject=cr.getString(cr.getColumnIndex(Data.SUBJECT));
            type=cr.getString(cr.getColumnIndex(Data.TYPE));
            timing=cr.getString(cr.getColumnIndex(Data.FROM))+"-"+cr.getString(cr.getColumnIndex(Data.TO));
            Item s=new Item(subject,type,timing);
            arraylist.add(s);
        }
    }


}

class CustomAdapter extends BaseAdapter
{
    Context context;
    TextView subject,type,timing,srno;
    ArrayList<Item> arrayList;
    public CustomAdapter(Context context,ArrayList<Item> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.row,viewGroup,false);
        srno=(TextView) row.findViewById(R.id.sr);
        subject=(TextView) row.findViewById(R.id.col1);
        type=(TextView) row.findViewById(R.id.col3);
        timing=(TextView) row.findViewById(R.id.col4);
        srno.setText(i+1+"");
        Item p=arrayList.get(i);
        subject.setText(p.subject);
        type.setText(p.type);
        timing.setText(p.timing);
        return row;
    }
}

