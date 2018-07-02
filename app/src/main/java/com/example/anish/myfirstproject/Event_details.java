package com.example.anish.myfirstproject;

        import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

        import com.example.neha.myfirstproject.R;

        import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Event_details extends android.support.v4.app.Fragment implements View.OnClickListener
{
    TextView event_name, venue, date, time, organiser, contact, description,price;
    TextView en,vn,d,t,o,c,d1,p;
    ImageView poster;
    String decodedImage;
    Button SeatBook;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.content_event_details, container, false);

        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        vn= (TextView)v.findViewById(R.id.V);
        t= (TextView)v.findViewById(R.id.T);
        d= (TextView)v.findViewById(R.id.D);
        d1= (TextView)v.findViewById(R.id.D1);
        p= (TextView)v.findViewById(R.id.P);
        o= (TextView)v.findViewById(R.id.O);
        c = (TextView)v.findViewById(R.id.C);
        en=(TextView)v.findViewById(R.id.EN);

        event_name = (TextView)v.findViewById(R.id.EventName);
        venue = (TextView) v.findViewById(R.id.Venue);
        time = (TextView) v.findViewById(R.id.time);
        date = (TextView) v.findViewById(R.id.Date);
        organiser = (TextView) v.findViewById(R.id.OrganisedBy);
        contact = (TextView) v.findViewById(R.id.Contact);
        description = (TextView)v.findViewById(R.id.Description);
        price = (TextView) v.findViewById(R.id.Price);

        SeatBook=(Button)v.findViewById(R.id.BookASeat);
        poster = (ImageView)v.findViewById(R.id.imageView1);
        SeatBook.setText(UserClass.button_text);
        SeatBook.setOnClickListener(this);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
            Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
            PreparedStatement ps = con.prepareStatement("select * from Events_EventDetails where Name=?");
            ps.setString(1,UserClass.Eventname);
            Log.e("Name : ", UserClass.Eventname);
            getActivity().setTitle(UserClass.Eventname);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                event_name.setText(rs.getString("Name"
                ));
                UserClass.venue=rs.getString("Venue");
                venue.setText(UserClass.venue);

                UserClass.time1=rs.getString("Time").substring(0,8);
                time.setText(UserClass.time1);
                UserClass.date=rs.getString("Date");
                date.setText(UserClass.date);
                UserClass.Contact=rs.getString("PhoneNo");
                contact.setText(UserClass.Contact);
                UserClass.Description=rs.getString("Description");
                description.setText(UserClass.Description);
                UserClass.price=rs.getString("Price");
                price.setText(UserClass.price);
                UserClass.organiser=rs.getString("OrganisedBy");
                organiser.setText(UserClass.organiser);
                UserClass.decodedImage=rs.getString("Poster");
                byte[] decodedstring = Base64.decode(UserClass.decodedImage,Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedstring,0,decodedstring.length);
                UserClass.decodedByte=decodedByte;
                poster.setImageBitmap(UserClass.decodedByte);
            }
        }
        catch (Exception ee)
        {
            Log.e("error","Exception generated"+ee);
        }
        return v;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.BookASeat)
        {
            seatbook newEvent2=new seatbook();
            getFragmentManager().beginTransaction().replace(R.id.fl, newEvent2).commit();
        }
    }
    public void onBackPressed()
    {

    }
}