package com.example.sachin.timify;

import android.support.annotation.IntRange;

/**
 * Created by MNNIT on 10/5/2017.
 */

public class schedule {
    String subject,teacher,room,duration,time,type;

    schedule(String subject,String teacher,String room,String time,String type)
    {
        this.room=room;
        this.subject=subject;
        this.teacher=teacher;
        this.time=time;
        this.type=type;
    }

    public String getDuration()
    {
        int h1=0,h2=0,m1=0,m2=0;
        int indexA=0,indexB=0,indexC=0;
        String t=time;
        int l=t.length();
        int v=1;
        for(int i=0;i<l;i++)
        {
            if(t.charAt(i)==':'&&v==1)
            {
                indexA=i;
                v++;
            }
            if(t.charAt(i)=='-')
            {
                indexB=i;
            }
            if (t.charAt(i)==':'&&v==2)
            {
                indexC=i;
            }
        }
        h1=Integer.parseInt(t.substring(0,indexA));
        m1=Integer.parseInt(t.substring(indexA+1,indexB));
        h2=Integer.parseInt(t.substring(indexB+1,indexC));
        m2=Integer.parseInt(t.substring(indexC+1));
        int v1=(h2-h1);
        int v2=(m2-m1);
        if(v2<0)
        {
            v1=v1-1;
            v2=v2+60;
        }
        if (v1!=0&&v2!=0)
        {
            return v1+" hours "+v2+" min.";
        }
        else if(v1!=0&&v2==0)
            return v1+" hours";

        return v2+" minutes";

    }
}

