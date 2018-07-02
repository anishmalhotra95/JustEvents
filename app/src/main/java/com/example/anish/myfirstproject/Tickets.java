package com.example.anish.myfirstproject;

import
        android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.neha.myfirstproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Anish on 12-08-2016.
 */
public class Tickets extends Fragment implements View.OnClickListener {

    TextView event_name, venue, date, time, CustomerName, price,ticketno;
    TextView en,Tc, Tvn, Td, Tt, Tp, tc,tn;
    ImageView poster;
    Button PrintTicket;
    String EventName;
    String ticket;
    int count=0,num,i;
    ListView listView;
    ArrayList<CustomList> strList = new ArrayList<CustomList>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v= inflater.inflate(R.layout.tickets, container, false);


        Log.e("Ticket :", "1");

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        en= (TextView)v. findViewById(R.id.TEN);
        Tc= (TextView)v. findViewById(R.id.TC);
        Tvn= (TextView)v.  findViewById(R.id.TV);
        Tt= (TextView)v.  findViewById(R.id.TT);
        Td= (TextView)v.  findViewById(R.id.TD);
        Tp= (TextView)v.  findViewById(R.id.TP);
        tc = (TextView)v.  findViewById(R.id.C);


        event_name = (TextView) v. findViewById(R.id.TEventName);
        CustomerName=(TextView) v. findViewById(R.id.CustomerName);
        venue = (TextView)v.  findViewById(R.id.TVenue);
        time = (TextView)v.  findViewById(R.id.Ttime);
        date = (TextView)v.  findViewById(R.id.TDate);
        price = (TextView)v.  findViewById(R.id.TPrice);
        ticket= String.valueOf((TextView)v.  findViewById(R.id.TicketNo));
        PrintTicket=(Button)v. findViewById(R.id.PrintTicket);
      //  poster = (ImageView)v.  findViewById(R.id.imageView1);

        event_name.setText(UserClass.Eventname);
        time.setText(UserClass.time1);
        date.setText(UserClass.date);
        venue.setText(UserClass.venue);
        price.setText(UserClass.price);
        //ticket=UserClass.Eventid+"00"+count;
        ticket="2001";
        num=UserClass.number;

        Log.e("Control Ticket me:", "2");
        while(count<num)
        {
            try
            {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
                Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
                PreparedStatement ps = con.prepareStatement("SELECT TOP 1 * FROM Events_Tickets ORDER BY S_No DESC");
                ResultSet rs=ps.executeQuery();
                if(rs.getString("S_No").equals(null))
                {
                    ticket=UserClass.Eventid+"00"+count;
                }
                else
                {
                    i=Integer.parseInt(rs.getString("S_No"));
                    i=i+1;
                    ticket=String.valueOf(i);
                }

                PreparedStatement ps1 = con.prepareStatement("insert into Events_Tickets(Ticket_No,Event_Id,User_Id) values(?,?,?)");
                ps1.setString(1,ticket);
                ps1.setString(2, UserClass.Eventid);
                ps1.setString(3, UserClass.Userid);
            }
            catch (Exception ee)
{

}
            count++;
        }
        PrintTicket.setOnClickListener(this);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
            Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
            PreparedStatement ps = con.prepareStatement("select * from Events_EventDetails where Name=?");
            ps.setString(1, UserClass.Eventname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                event_name.setText(rs.getString("Name"));
                venue.setText(rs.getString("Venue"));
                time.setText(rs.getString("Time"));
                date.setText(rs.getString("Date"));
                price.setText(rs.getString("Price"));
            }
            PreparedStatement ps1 = con.prepareStatement("select Name from Events_UserDetails where User_Id=?");
            ps1.setString(1, String.valueOf(UserClass.Userid));
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                CustomerName.setText(rs1.getString("Name"));

            }
        }
            catch (Exception ee)
            {
                Log.e("error","Exception generated "+ee);
            }
        return v;
    }

        @Override
        public void onClick(View v)
        {

        }
    }
