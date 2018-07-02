package com.example.anish.myfirstproject;

import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neha.myfirstproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class seatbook extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener
{
    ImageView iv;
    TextView name,number,event;
    String Event_Id,User_Id,ticket;
    Integer item,num,i,count=0;
    Button Book;
    Spinner spinner;
    List<Integer> categories=new ArrayList<Integer>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.content_seatbook, container, false);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        iv=(ImageView)v.findViewById(R.id.imageView2);
        name=(TextView)v.findViewById(R.id.Event_name2);
        number=(TextView)v.findViewById(R.id.No_OfSeats2);

        event=(TextView)v.findViewById(R.id.EventName2);

        event.setText(UserClass.Eventname);
        Book=(Button)v.findViewById(R.id.booknow2);

        spinner = (Spinner)v.findViewById(R.id.number);

        categories.add(   1   );
        categories.add(   2   );
        categories.add(   3   );
        categories.add(   4   );
        categories.add(   5   );

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_dropdown_item,categories);
        spinner.setAdapter(dataAdapter);

//        byte[] decodedstring = Base64.decode(UserClass.decodedImage, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedstring, 0, decodedstring.length);

        iv.setImageBitmap(UserClass.decodedByte);

        spinner.setOnItemSelectedListener(this);
        Toast.makeText(getActivity(), "xyz", Toast.LENGTH_SHORT).show();
        Book.setOnClickListener(this);
        Toast.makeText(getActivity(), "xyz1", Toast.LENGTH_SHORT).show();
        return v;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.booknow2)
        {
            Log.e("button clicked","button click chal ra hai");
            MyAsyncClass obj = new MyAsyncClass();
            obj.execute();

            Tickets ne=new Tickets();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl, ne).commit();
        }
//            try
//            {
 //                Toast.makeText(getActivity(),"0",Toast.LENGTH_SHORT).show();
//                Class.forName("net.sourceforge.jtds.jdbc.Driver");
//                String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
//                Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
//
//                PreparedStatement ps = con.prepareStatement("select Event_Id, 'User_Id'=(select User_Id from Events_UserDetails where Email=?) from Events_EventDetails where Name=?");
//
//                ps.setString(1,UserClass.Email);
//                ps.setString(2, UserClass.Eventname);
//                ResultSet rs=ps.executeQuery();
//                if(rs.next())
//                {
//                    Event_Id=rs.getString("Event_Id");
//                    UserClass.Eventid=Event_Id;
//                    User_Id=rs.getString("User_Id");
//                    UserClass.Userid=User_Id;
//                }
//                Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
//
//                PreparedStatement ps2 = con.prepareStatement("insert into Events_SeatBooking (Event_Id,EventName,User_Id,UserEmail,Seat)values (?,?,?,?,?)");
//                ps2.setString(1, Event_Id);
//                ps2.setString(2, UserClass.Eventname);
//                ps2.setString(3, User_Id);
//                ps2.setString(4, UserClass.Email);
//                ps2.setString(5, item.toString());
//                int j = ps2.executeUpdate();
//                Toast.makeText(getAlctivity(), "10", Toast.LENGTH_SHORT).show();
//                if (j > 0)
//                {
//                    Toast.makeText(getActivity(), item + " seats booked successfully !!", Toast.LENGTH_SHORT).show();
//                    MyAsyncClass obj = new MyAsyncClass();
//                    obj.execute();
////                    UserClass.button_text="Book More Tickets";
////                    Event_details newEvent1=new Event_details();
////                    Toast.makeText(getActivity(),"6",Toast.LENGTH_SHORT).show();
////                    getFragmentManager().beginTransaction().replace(R.id.fl, newEvent1).commit();
//                }
//                else
//                {
//                    Toast.makeText(getActivity(), "Seat booking failed!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//            catch(Exception ee)
//            {
//                Log.e("sajvcvdvhvd fsd fsd", " error in database" + ee);
//            }
//        }
              //  String Event_Id=rs.getString("Event_Id").toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        item = categories.get(position);
        UserClass.number=item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    class MyAsyncClass extends AsyncTask
    {

        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {

      //      super.onPreExecute();

            dialog.setTitle("Processing");
            dialog.setMessage("");
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params)
        {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
                Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");

                PreparedStatement ps = con.prepareStatement("select Event_Id, 'User_Id'=(select User_Id from Events_UserDetails where Email=?) from Events_EventDetails where Name=?");

                ps.setString(1, UserClass.Email);
                ps.setString(2, UserClass.Eventname);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    Event_Id = rs.getString("Event_Id");
                    UserClass.Eventid = Event_Id;
                    User_Id = rs.getString("User_Id");
                    UserClass.Userid = User_Id;

                    Log.e("User ID and Event ID : ", UserClass.Userid+", "+UserClass.Eventid);
                }

                PreparedStatement ps2 = con.prepareStatement("insert into Events_SeatBooking (Event_Id,EventName,User_Id,UserEmail,Seat)values (?,?,?,?,?)");
                ps2.setString(1, Event_Id);
                ps2.setString(2, UserClass.Eventname);
                ps2.setString(3, User_Id);
                ps2.setString(4, UserClass.Email);
                ps2.setString(5, item.toString());
                int j = ps2.executeUpdate();

                if (j > 0) {
                    Log.e("inserted","ho gya!"+j);

//                    UserClass.button_text="Book More Tickets";
//                    Event_details newEvent1=new Event_details();
//                    Toast.makeText(getActivity(),"6",Toast.LENGTH_SHORT).show();
//                    getFragmentManager().beginTransaction().replace(R.id.fl, newEvent1).commit();
                } else {

                    Log.e("inserted","nhi hua!");}



                PreparedStatement ps1 = con.prepareStatement("SELECT TOP 1 * FROM Events_Tickets ORDER BY S_No DESC");
                Log.e("1","1");
                ResultSet rs1 = ps1.executeQuery();
                Log.e("2","2");
                rs1.next();
                 Log.e("3","3");
                String str = null;
                try
                {
                    str=rs1.getString("S_No");
                }
                catch (Exception ee)
                {

                }
                Log.e("4","4");
                if (str==null)
                    {
                        Log.e("5","5");
                        ticket = UserClass.Eventid + "00" + count;
                                                                                                                                                                                                                                                  Log.e("ticket", "" + ticket);
                    }
                    else
                    {
                        Log.e("6","6");
                        i = Integer.parseInt(rs1.getString("S_No"));
                        Log.e("7","7");
                        i = i + 1;
                        Log.e("8","8");
                        ticket = String.valueOf(i);

                        Log.e("ticket1", "" + ticket);
                    }
                    Log.e("9","9");

                num = UserClass.number;

                Log.e("10","10");
                while (count < num)
                {
                    Log.e("11","11");
                    PreparedStatement ps3 = con.prepareStatement("insert into Events_Tickets(Ticket_No,Event_Id,User_Id) values(?,?,?)");
                    Log.e("12","12");
                    ps3.setString(1, ticket);
                    Log.e("13","13");
                    ps3.setString(2, UserClass.Eventid);
                    Log.e("14","14");
                    ps3.setString(3, UserClass.Userid);
                    Log.e("15","15");
                    int k=ps3.executeUpdate();
                    Log.e("chl gya",""+k);
                    count++;
                }
            }
            catch (Exception ee)
            {
                Log.e("sajvcvdvhvd fsd fsd", " error in database" + ee);
            }
            return null;
        }
//            }
//            catch(Exception ee)
//            {
//                Log.e("ssssss", ee+"");
//            }
//            String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
//            Connection con=null;
//            try {
//                con=DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
//            }
//            catch (Exception ee){}


        @Override
        protected void onPostExecute(Object o)
        {
            Log.e("16","16");
            super.onPostExecute(o);
            Log.e("17","17");
            Log.e("18","18");
            Log.e("19","19");
//        Intent intent = new Intent(Background.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//        iv.setVisibility(View.GONE);
            dialog.dismiss();
            Log.e("20","20");

        }
    }
}
