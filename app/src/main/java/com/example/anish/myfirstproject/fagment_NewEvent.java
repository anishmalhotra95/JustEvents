package com.example.anish.myfirstproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.neha.myfirstproject.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.GregorianCalendar;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;



public class fagment_NewEvent extends android.support.v4.app.Fragment implements View.OnClickListener {
    static EditText name, venue, time, date, organizer, contact, description, price,totalseats;
    Button createevent, poster;
    ImageView img1;
    int RequestLoadcode = 1, RequestImageLoad = 1,ProjectFlags=1 ;
    String encodedImage;

    String imagedecode;
    File file;
    int bytetranssfered;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_fagment__new_event, container, false);

        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        name = (EditText) v.findViewById(R.id.EventName1);
        venue = (EditText) v.findViewById(R.id.Venue1);
        time = (EditText) v.findViewById(R.id.time1);
        date = (EditText) v.findViewById(R.id.Date1);
        organizer = (EditText) v.findViewById(R.id.OrganisedBy1);
        contact = (EditText) v.findViewById(R.id.Contact1);
        description = (EditText) v.findViewById(R.id.Description1);
        price = (EditText) v.findViewById(R.id.Price1);
        totalseats= (EditText) v.findViewById(R.id.Totalseats1);
        createevent = (Button) v.findViewById(R.id.CreateEvent1);
        poster = (Button) v.findViewById(R.id.Poster1);
        createevent.setOnClickListener(this);
        time.setOnClickListener(this);
        date.setOnClickListener(this);
        img1 = (ImageView) v.findViewById(R.id.Img1);
        poster.setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        byte[] image = null;

        String selectedImageType = "";
        if (requestCode == RequestLoadcode && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImage = data.getData();


//to get image type
            try
            {
                ContentResolver cR = getActivity().getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                selectedImageType = mime.getExtensionFromMimeType(cR.getType(selectedImage));
            }
            catch (Exception ee)
            {
                Toast.makeText(getActivity(), "tpe check krte hue" + ee, Toast.LENGTH_LONG).show();
            }

            Toast.makeText(getActivity(), "type" + selectedImageType, Toast.LENGTH_LONG).show();

            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                img1.setImageBitmap(bitmap);


                //     Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100

                Toast.makeText(getActivity(), "type" + selectedImageType, Toast.LENGTH_LONG).show();

                if (selectedImageType == "JPEG" || selectedImageType == "jpeg") {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    image = stream.toByteArray();
                } else if (selectedImageType == "JPG" || selectedImageType == "jpg") {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    image = stream.toByteArray();
                } else if (selectedImageType == "PNG") {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    image = stream.toByteArray();
                } else {
                    Toast.makeText(getActivity(), "Please pick a JPEG or PNG photo!", Toast.LENGTH_SHORT).show();

                }
                poster.setText(selectedImage.toString());
//                ImageView.Poster1 = selectedImage.toString();

            } catch (Exception ee) {
                Toast.makeText(getActivity(), "bitmap mei error" + ee, Toast.LENGTH_LONG).show();
            }
            try {
                encodedImage = Base64.encodeToString(image, Base64.NO_WRAP);

            } catch (Exception ee) {
                Toast.makeText(getActivity(), "encoding error" + ee, Toast.LENGTH_LONG).show();
            }


            if (requestCode == RequestImageLoad && resultCode == getActivity().RESULT_OK && data != null) {
                selectedImage = data.getData();
                try {
                    String[] filepathcolumn = {MediaStore.Images.Media.DATA};


                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filepathcolumn, null, null, null);
                    cursor.moveToFirst();

                    int index = cursor.getColumnIndex(filepathcolumn[0]);


                    //String type ka variable
                    imagedecode = cursor.getString(index);

                    //buton set text
                    poster.setText(imagedecode);
//yha tk krna hai

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), "databse2" + e, Toast.LENGTH_LONG).show();
                }
            }
        }
        class sendfileonft extends AsyncTask {

            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(getActivity(),
                        "ProgressDialog",
                        "Wait for " + "few" + " seconds");
            }

            @Override
            protected Void doInBackground(Object[] params)

            {
                FTPClient client = new FTPClient();
                try {
                    client.connect("helloyatri.com", 21);
                    client.login("helloyatri", "helloYatri@123");
                    client.upload(file, new MyTransferListener());
                    publishProgress(bytetranssfered);

                } catch (Exception e) {
                    Log.d("connection", "" + e);
                    Toast.makeText(getActivity(), "unsuccess full mthod" + e, Toast.LENGTH_LONG).show();
                    try {
                        client.disconnect(true);
                    } catch (Exception e2) {

                        Toast.makeText(getActivity(), "disconnect mthod" + e, Toast.LENGTH_LONG).show();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                progressDialog.dismiss();
            }
        }
    }

    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {

            // Transfer started
            //System.out.println(" Upload Started ...");
        }

        public void transferred(int length) {

            bytetranssfered = length;

            // Yet other length bytes has been transferred since the last time this
            // method was called
            //System.out.println(" transferred ..." + length);
        }

        public void completed() {


            // Transfer completed

            //System.out.println(" completed ..." );
        }

        public void aborted() {

            // Transfer aborted
            //System.out.println(" aborted ..." );
        }

        public void failed() {

            // Transfer failed
            // System.out.println(" failed ...");
        }


    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.Poster1)
        {
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), ProjectFlags);


        }


        if(v.getId()==R.id.Date1)
        {
            GregorianCalendar gc=new GregorianCalendar();
            DatePickerDialog dp=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                }
            },2016,2,25);
            dp.show();
        }
        if(v.getId()==R.id.time1)
        {
            TimePickerDialog tp=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time.setText(hourOfDay+":"+minute);
                }
            },8,10,false);
            tp.show();
        }

      if(v.getId()==R.id.CreateEvent1)
      {
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
            Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
            PreparedStatement ps = con.prepareStatement("insert into Events_EventDetails(Name,Venue,Time,Date,PhoneNo,Description,Price,OrganisedBy,UserEmail, Poster,Totalseats)values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, name.getText().toString());
            ps.setString(2, venue.getText().toString());
            ps.setString(3, time.getText().toString());
            ps.setString(4, date.getText().toString());
            ps.setString(5, contact.getText().toString());
            ps.setString(6, description.getText().toString());
            ps.setString(7, price.getText().toString());
            ps.setString(8, organizer.getText().toString());
            ps.setString(9, UserClass.Email);
            ps.setString(10, encodedImage);
            ps.setString(11, totalseats.getText().toString());

            int i=ps.executeUpdate();
            if(i>0)
            {
                Toast.makeText(getActivity(),"Event created successfully!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(),"Event creation failed!!",Toast.LENGTH_SHORT).show();
            }

            Intent j=new Intent(getActivity(),Event_details.class);
            Bundle b=new Bundle();
            b.putString("name",name.getText().toString());
            j.putExtras(b);
            startActivity(j);
        }
        catch (Exception ee)
        {
            Log.e("sdfd fsd fsd"," error in database" + ee);
        }
    }
}
}
