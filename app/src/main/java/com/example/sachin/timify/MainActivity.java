package com.example.sachin.timify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    ViewPager viewPager;    //Manages the Fragments in Flipping
    TabLayout tabLayout;    //For the tabbing of Weekdays
    static String [] titles;
    public int pos;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        titles = res.getStringArray(R.array.days);  //Fetching the days name from the strings.xml

        onNotification();           //Method to set the notification

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentManager f = getSupportFragmentManager();
        myAdapter = new MyAdapter(f);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(7);         //It sets how much pages should be remained saved offscreen

        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        switch (dayLongName)
        {
            case "Monday":viewPager.setCurrentItem(0);
                break;
            case "Tuesday":viewPager.setCurrentItem(1);         //This switch checks which day of the week
                break;                                          // is today and which page is to show when open
            case "Wednesday":viewPager.setCurrentItem(2);       // the app
                break;
            case "Thursday":viewPager.setCurrentItem(3);
                break;
            case "Friday":viewPager.setCurrentItem(4);
                break;
            case "Saturday":viewPager.setCurrentItem(5);
                break;
            case "Sunday":viewPager.setCurrentItem(6);
                break;
            default:viewPager.setCurrentItem(0);
        }



    }
    public void Addtime(View view)
    {
        Intent i=new Intent(this,AddSchedule.class);
        Bundle b = new Bundle();                        //  This is the method which is called when the Floating
        b.putInt("ID",viewPager.getCurrentItem());      //  button is clicked
        i.putExtras(b) ;
        startActivity(i);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Cursor c=getContentResolver().query(Data.CONTENT_URI,null,null,null,null);
//        if(c.getCount()!=0&&c!=null)
//        {   String s="" ;
//            c.moveToFirst() ;
//            do {
//                s = s + c.getString(c.getColumnIndex(Data.DAY));
//                s=s+"\n" ;
//            }while(c.moveToNext()) ;
//            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//        }
//    }

    private void onNotification()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Notify",null);
        if(name==null)
        {
            name="1";
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("Notify",name);
            editor.commit();                                   //Here when for the first time
                                                              //this method will be called then
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE); //an alarm will be set for
        Date dat = new Date();                              // 7:00 which will repeat after 24 hours
        Calendar cal_alarm = Calendar.getInstance();        //The alarm will not be created again once created
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,7);
        cal_alarm.set(Calendar.MINUTE,0);
        cal_alarm.set(Calendar.SECOND,0);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
        }
        Intent myIntent = new Intent(this, MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),24*60*60*1000,pendingIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about_btn:
                about();
                return true;
            case R.id.help_btn:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHelp() {
        Uri uri = Uri.parse("https://developer.android.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void about() {
        Intent i=new Intent(this,album.class);
        startActivity(i);
    }

}

/**
 *      Adapter class which will populate the fragments in the main activity
 */

class MyAdapter extends FragmentStatePagerAdapter
{

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f=null;
        if(position==0)
        {
            f=new Monday();

        }
        else if(position==1)
        {
            f=new Tuesday();
        }
        else if(position==2)
        {
            f=new Wednesday();
        }
        else if(position==3)
        {
            f=new Thrusday();
        }
        else if(position==4)
        {
            f=new Friday();
        }
        else if(position==5)
        {
            f=new Saturday();
        }
        else
        {
            f=new Sunday();
        }

        return f;
    }

    @Override
    public int getCount()
    {
        return MainActivity.titles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.titles[position];
    }


}
