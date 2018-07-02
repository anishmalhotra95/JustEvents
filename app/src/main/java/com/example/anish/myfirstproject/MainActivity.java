package com.example.anish.myfirstproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.neha.myfirstproject.LocalDbHelper;
import com.example.neha.myfirstproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<CustomList> strList = new ArrayList<CustomList>();
    LocalDbHelper dbobjct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        dbobjct = new LocalDbHelper(this);
        SQLiteDatabase db2 = dbobjct.getReadableDatabase();
        Cursor cr = db2.query("Details", new String[]{"User_id", "Email", "password", "contact"}, "IsLoggedIn=1", null, "", "", "");

        if (cr.moveToNext())
        {
            UserClass.Email = cr.getString(1);
            Toast.makeText(this, "hhgfg" + cr.getString(0) + "" + cr.getString(1), Toast.LENGTH_SHORT).show();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        listView = (ListView) findViewById(R.id.list_item);
        MyAdapter adapter = new MyAdapter(this, strList);
//      listView.setBackgroundResource(R.drawable.abc);
//      listView.setTextFilterEnabled(true);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
            Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
            PreparedStatement ps = con.prepareStatement("select Name,Event_Id,poster from Events_EventDetails");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomList c1 = new CustomList();
                c1.setName(rs.getString("Name"));
                c1.setPoster(rs.getString("Poster"));
                strList.add(c1);
            }
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        } catch (Exception ee) {
            Log.e("error", "Exception generated" + ee);
        }
        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        listView.setAdapter(adapter);
        Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener(this);
        Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.New_event) {
            fagment_NewEvent newEvent = new fagment_NewEvent();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, newEvent).commit();
            listView.setVisibility(View.GONE);
        } else if (id == R.id.booked_events) {

            listView.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "m", Toast.LENGTH_SHORT).show();
            Booked_events fr = new Booked_events();
            Toast.makeText(MainActivity.this, "n", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, fr).commit();
            Toast.makeText(MainActivity.this, "o", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "p", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.my_events) {
            listView.setVisibility(View.GONE);
            My_events newEvent1 = new My_events();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, newEvent1).commit();
        } else if (id == R.id.tickets) {
            listView.setVisibility(View.GONE);
            Tickets newEvent2 = new Tickets();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, newEvent2).commit();
            Toast.makeText(MainActivity.this, "working", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.share)
        {

        }
        else if (id == R.id.sign_out)
        {
            SQLiteDatabase db2 = dbobjct.getReadableDatabase();
            Cursor cr = db2.query("Details", new String[]{"Email"}, "", null, "", "", "");
            if (cr.moveToNext()) {
                if (cr.getString(0).equals(UserClass.Email)) {
                    SQLiteDatabase db1 = dbobjct.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("IsLoggedIn", "0");
                    long i = db1.update("Details", cv, "", null);
                    Toast.makeText(MainActivity.this, "xyz" + i, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ERROR!!", Toast.LENGTH_SHORT).show();
                }
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
        UserClass.Eventname = strList.get(position).getName();
        UserClass.button_text = "Book Now";
        Toast.makeText(this, UserClass.Eventname + "", Toast.LENGTH_SHORT).show();
        Event_details newEvent1 = new Event_details();
        Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, newEvent1).commit();
        Toast.makeText(this, "7", Toast.LENGTH_SHORT).show();
        listView.setVisibility(View.GONE);
    }
}