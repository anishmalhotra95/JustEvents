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
 * Created by Neha on 8/5/2016.
 */

public class My_events extends Fragment implements AdapterView.OnItemClickListener
{
    ListView listView;
    ArrayList<CustomList> strList1=new ArrayList<CustomList>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.bookedevents, container, false);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        listView=(ListView)v.findViewById(R.id.booked_events_list);
        //MyAdapter adapter=new MyAdapter(getActivity(),strList1);

        Toast.makeText(getActivity(), "chla", Toast.LENGTH_SHORT).show();

        MyAsyncClass obj = new MyAsyncClass();
        obj.execute();

        Toast.makeText(getActivity(), "chla!!!", Toast.LENGTH_SHORT).show();

        //listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String EventName=strList1.get(position).getName();
        Event_details newEvent=new Event_details();
        Toast.makeText(getActivity(), EventName + "", Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.fl, newEvent).commit();
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
            try
            {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
                Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
                PreparedStatement ps = con.prepareStatement("select Name from Events_EventDetails where UserEmail=?");
                ps.setString(1,UserClass.Email);
                Log.e("chla1","1");
                ResultSet rs = ps.executeQuery();
                Log.e("chla2","2");
                while(rs.next())
                {
                    Log.e("chla3","3");
                    CustomList c1 = new CustomList();
                    c1.setName(rs.getString("Name"));
                    strList1.add(c1);
                }
            }
            catch(Exception ee)
            {
                Log.e("Exception !!",""+ee);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o)
        {
            super.onPostExecute(o);

            MyAdapter adapter=new MyAdapter(getActivity(),strList1);
            listView.setAdapter(adapter);
//        Intent intent = new Intent(Background.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//        iv.setVisibility(View.GONE);
        }
    }
}