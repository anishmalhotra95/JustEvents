//package com.example.anish.myfirstproject;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
///**
// * Created by Neha on 7/25/2016.
// */
//public class seatbooking extends AppCompatActivity implements View.OnClickListener
//{
//    Intent i;
//    ImageView iv;
//    TextView name,number;
//    String n;
//    Button Book;
//
//    Bundle extras=i.getExtras();
//    String EventName=extras.getString("name");
//    @Nullable
//    @Override
//    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        View v=inflater.inflate(R.layout.seatbooking,container,false);
//
//
//        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(tp);
//
//        iv=(ImageView)v.findViewById(R.id.imageView);
//        name=(TextView)v.findViewById(R.id.Event_name2);
//        number=(TextView)v.findViewById(R.id.number);
//
//        name.setText(EventName);
//        n=number.getText().toString();
//        Book=(Button)v.findViewById(R.id.booknow2);
//
//        Book.setOnClickListener(this);
//        super.onCreateView(inflater, container, savedInstanceState);
//        return v;
//    }
//
//
//    @Override
//    public void onClick(View v)
//    {
//        if(v.getId()==R.id.booknow2)
//        {
//                try
//                {
//                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
//                    String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
//                    Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
//                    PreparedStatement ps = con.prepareStatement("select Event_Id from Events_EventDetails");
//                    ResultSet rs=ps.executeQuery();
//                    String EventId=rs.getString("Event_Id");
//                    PreparedStatement ps1 = con.prepareStatement("select User_Id from Events_UserDetails");
//                    ResultSet rs1=ps1.executeQuery();
//                    String UserId=rs1.getString("Event_Id");
//                    PreparedStatement ps2 = con.prepareStatement("insert ? into Events_SeatBooking where EventId=? and User_Id =?");
//                    ps2.setString(1,n);
//                    ps2.setString(2, EventId);
//                    ps2.setString(3, UserId);
//
//                    int j=ps2.executeUpdate();
//
//                    if(j>0)
//                    {
//                        Toast.makeText(getActivity(),n+"seats booked successfully !!",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(),"Seat booking failed!!",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch(Exception ee)
//                {
//                    Log.e("sdfd fsd fsd", " error in database" + ee);
//                }
//        }
//    }
//}
