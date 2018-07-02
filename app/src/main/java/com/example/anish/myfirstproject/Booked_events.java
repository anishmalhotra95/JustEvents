package com.example.anish.myfirstproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.neha.myfirstproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Anish on 8/5/2016.
 */

public class Booked_events extends Fragment implements AdapterView.OnItemClickListener
{
    ListView listView;
    ArrayList<CustomList> strList=new ArrayList<CustomList>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.myevents, container, false);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        listView=(ListView)v.findViewById(R.id.My_events_list);

        Toast.makeText(getActivity(),"khul gya",Toast.LENGTH_SHORT).show();

        MyAsyncClass obj = new MyAsyncClass();
        obj.execute();

        Toast.makeText(getActivity(),"2",Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(),"3",Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener(this);
        Toast.makeText(getActivity(),"4",Toast.LENGTH_SHORT).show();
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        UserClass.button_text="Book Tickets";
        Event_details newEvent1=new Event_details();
        Toast.makeText(getActivity(),"6",Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.fl, newEvent1).commit();
    }


    class MyAsyncClass extends AsyncTask
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params)
        {
            ArrayList<CustomList> strList=new ArrayList<CustomList>();
            try
            {
                Log.e("1","1");
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
                Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
                PreparedStatement ps = con.prepareStatement("select EventName from Events_SeatBooking where UserEmail=?");
                ps.setString(1,UserClass.Email);
                Log.e("2", "2");
                ResultSet rs = ps.executeQuery();
                Log.e("3","3");
                while(rs.next())
                {
                    Log.e("4","4");
                    CustomList c1 = new CustomList();
                    c1.setName(rs.getString("EventName"));
                    strList.add(c1);
                    Log.e("5", "5");
                }
            }
            catch(Exception ee)
            {
                Log.e("error!",""+ee);
            }
            return strList;
        }

        protected void onPostExecute(Object o)
        {
            super.onPostExecute(o);
            Log.e("6","6");

            MyAdapter adapter=new MyAdapter(getActivity(),strList);
            listView.setAdapter(adapter);
//        Intent intent = new Intent(Background.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//        iv.setVisibility(View.GONE);
        }
    }
}