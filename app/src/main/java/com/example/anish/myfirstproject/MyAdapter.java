package com.example.anish.myfirstproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.neha.myfirstproject.R;

import java.util.ArrayList;


public class MyAdapter extends BaseAdapter
{
    ArrayList<CustomList> list1=new ArrayList<CustomList>();
    Activity activity1;

    public MyAdapter(Activity activity, ArrayList<CustomList> data)
    {
        list1=data;
        activity1=activity;
    }

    @Override
    public int getCount()
    {
        return list1.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list1.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;
        if(convertView==null)
        v=activity1.getLayoutInflater().inflate(R.layout.item_layout, null);
        TextView name=(TextView)v.findViewById(R.id.name_text);
        CustomList d=list1.get(position);
        String strName=d.getName();


        name.setText(strName);
        name.setTextColor(-1);
        name.setTextSize(25);
        //convertView.setBackground(R.drawable.abc);
       // v.setImageBitmap(UserClass.decodedByte);
        try {

            byte[] decodedstring = Base64.decode(d.getPoster(),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedstring,0,decodedstring.length);
            UserClass.decodedByte=decodedByte;
            BitmapDrawable ob = new BitmapDrawable(activity1.getResources(), UserClass.decodedByte);
            v.setBackgroundDrawable(ob);

        }
        catch (Exception ee)
        {}
        return v;
    }
}
