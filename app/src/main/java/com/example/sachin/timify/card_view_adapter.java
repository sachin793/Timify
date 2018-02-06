package com.example.sachin.timify;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static com.example.sachin.timify.R.id.teacher;

/**
 * Created by MNNIT on 10/5/2017.
 */

public class card_view_adapter extends RecyclerView.Adapter<card_view_adapter.MyviewHolder> {


    protected ArrayList<schedule> arraylist;
    Context mCtx;
    int day;

    public class MyviewHolder extends RecyclerView.ViewHolder{
        public TextView subject,teacher,room,time,duration,type;
        public ImageButton button;
        public MyviewHolder(View itemView, Context context) {
            super(itemView);
            mCtx=context;
            subject= (TextView) itemView.findViewById(R.id.subject);
            teacher= (TextView) itemView.findViewById(R.id.teacher);
            room= (TextView) itemView.findViewById(R.id.room);
            time= (TextView) itemView.findViewById(R.id.time1);
            duration= (TextView) itemView.findViewById(R.id.duration);
            button=(ImageButton)itemView.findViewById(R.id.imagebutton);
            type=(TextView)itemView.findViewById(R.id.type);
        }
    }

    public card_view_adapter(ArrayList<schedule> arraylist,int position)
    {
        this.arraylist=arraylist;
        this.day=position;
    }


    @Override
    public card_view_adapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.card_design,parent,false);
        return new MyviewHolder(v,v.getContext());
    }

    @Override
    public void onBindViewHolder(final card_view_adapter.MyviewHolder holder, final int position) {
        schedule s= arraylist.get(position);
        holder.subject.setText(s.subject);
        holder.teacher.setText(s.teacher);
        holder.room.setText(s.room);
        holder.time.setText(s.time);
        holder.type.setText(s.type);
        holder.duration.setText(s.getDuration());
        final String teacher=s.teacher;
        final String subject=s.subject;
        final String room=s.room;
        int i=getIndex(s.time);
        final String type=s.type;
       final String from=s.time.substring(0,i);
       final String to=s.time.substring(i+1);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx,holder.button);
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                    edit(teacher,subject,room,from,to,type);
                                break;
                            case R.id.menu2:
                                delete(teacher,subject,room,from,to,day,position);
                                break;
                            case R.id.menu3:
                                share(teacher,subject,room,from,to,type);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });


    }

    private int getIndex(String s) {
        int i;
        for(i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='-')
            {
                break;
            }
        }
        return i;
    }

    /**
     *This method is called when Delete option from the popup menu is clicked
     */

    public void delete(String teacher,String subject,String room,String from,String to,int day,int position)
    {
           String where=Data.TEACHER+" = ? AND " +
              Data.SUBJECT+" = ? AND "+
                Data.ROOM+" = ? AND "+
                Data.FROM+" = ? AND "+
                Data.TO+" = ? AND "+
                //Data.TYPE+" = ? AND "+
                Data.DAY+" = ? ";
        String[] s={teacher,subject,room,from,to,""+day};
        Log.d("utkarsh",teacher+" "+subject+" "+room+" "+from+" "+to+" "+day);
        Cursor c= mCtx.getContentResolver().query(Data.CONTENT_URI,null,where,s,null);
        if(c!=null&&c.getCount()!=0)
        {
            mCtx.getContentResolver().delete(Data.CONTENT_URI,where,s);
            arraylist.remove(position);
            notifyDataSetChanged();
        }
        else
            Toast.makeText(mCtx,"no delete",Toast.LENGTH_SHORT).show();
        c.close();
    }

    /**
     * This method is called when Edit option from the options is clicked
     */

    public void edit(String teacher,String subject,String room,String from,String to,String type)
    {
        Intent i=new Intent(mCtx,AddSchedule.class);
        Bundle be=new Bundle();
        be.putString("teacher",teacher);
        be.putString("subject",subject);
        be.putString("room",room);
        be.putString("from",from);
        be.putString("to",to);
        be.putInt("update",23);
        be.putInt("day",day);
        be.putString("type",type);
        i.putExtras(be);
        mCtx.startActivity(i);
    }

    /**
     * This is called when Share option is clicked from the options menu
     */

    public void share(String teacher,String subject,String room,String from,String to,String type)
    {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,"Teacher: "+teacher+"\nSubject: "+subject+"\nRoom no.: "+room+"\nFrom: "+from+"\nTo:"+to+"\nType: "+type);
        mCtx.startActivity(share);
    }


    @Override
    public int getItemCount() {
        return arraylist.size() ;
    }


}





