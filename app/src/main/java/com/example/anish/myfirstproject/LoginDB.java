package com.example.anish.myfirstproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.neha.myfirstproject.LocalDbHelper;
import com.example.neha.myfirstproject.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LoginDB extends Fragment implements View.OnClickListener
{
    Button register,cancel,addphoto;
    EditText name, city, phone, email, password;
    LocalDbHelper myHelper;
    int RequestLoadcode = 1, RequestImageLoad = 1,ProjectFlags=1 ;
    String encodedImage;
    ImageView Userphoto;
    String imagedecode;
    File file;
    int bytetranssfered;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.content_main2, container, false);
        StrictMode.ThreadPolicy tp=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        name=(EditText)v.findViewById(R.id.Name);
        city=(EditText)v.findViewById(R.id.city);
        phone=(EditText)v.findViewById(R.id.Phone);
        email=(EditText)v.findViewById(R.id.Email);
        password=(EditText)v.findViewById(R.id.Password);

        register=(Button)v.findViewById(R.id.Registerbutton);
        cancel=(Button)v.findViewById(R.id.cancel);
     //   addphoto=(Button)v.findViewById(R.id.disppic);

        register.setOnClickListener(this);
        addphoto.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed()
    {
        Intent i=new Intent(getActivity(),LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        byte[] image = null;

        String selectedImageType = "";
        if (requestCode == RequestLoadcode && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImage = data.getData();


//to get image type
            try {
                ContentResolver cR = getActivity().getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                selectedImageType = mime.getExtensionFromMimeType(cR.getType(selectedImage));
            } catch (Exception ee) {
                Toast.makeText(getActivity(), "tpe check krte hue" + ee, Toast.LENGTH_LONG).show();
            }

            Toast.makeText(getActivity(), "type" + selectedImageType, Toast.LENGTH_LONG).show();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                Userphoto.setImageBitmap(bitmap);


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
                addphoto.setText(selectedImage.toString());
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
                    addphoto.setText(imagedecode);
//yha tk krna hai

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity(), "databse2" + e, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.Registerbutton)
        {
            Toast.makeText(getActivity(),"abcd2233",Toast.LENGTH_SHORT).show();
                try {

                    Toast.makeText(getActivity(),"a12bcd",Toast.LENGTH_SHORT).show();
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                    String queryString = "jdbc:jtds:sqlserver://182.50.133.111; DatabaseName=aarushiDB; user=aarushiDBUser; password=Aarushi@db!2#";
                    Connection con = DriverManager.getConnection(queryString, "aarushiDBUser", "Aarushi@db!2#");
                    PreparedStatement ps=con.prepareStatement("insert into Events_UserDetails(Name,Email,Password,Phone,City)values(?,?,?,?,?)");

                    ps.setString(1, name.getText().toString());
                    ps.setString(2, email.getText().toString());
                    ps.setString(3, password.getText().toString());
                    ps.setString(4, phone.getText().toString());
                    ps.setString(5, city.getText().toString());
                    Toast.makeText(getActivity(),"abc4d",Toast.LENGTH_SHORT).show();
                    int i= ps.executeUpdate();
                    Toast.makeText(getActivity(),"abcdef",Toast.LENGTH_SHORT).show();

                    if(i>0)
                    {
                        myHelper=new LocalDbHelper(getActivity());
                        SQLiteDatabase db=myHelper.getWritableDatabase();
                        ContentValues cv=new ContentValues();
                        cv.put("email", email.getText().toString());
                        cv.put("password", password.getText().toString());
                        cv.put("contact", phone.getText().toString());
                        cv.put("IsLoggedIn", "0");
                        long id=db.insert("Details","", cv);
                        Toast.makeText(getActivity(), "Registration Successful!"+id, Toast.LENGTH_SHORT).show();
                        Intent k=new Intent(getActivity(),LoginActivity.class);
                        startActivity(k);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Registration Unsuccessful!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ee)
                {
                    Log.e("sdfd fsd fsd", " error in database" + ee);
                }
        }

        if(v.getId()==R.id.cancel)
        {
            Intent j=new Intent(getActivity(),LoginActivity.class);
            startActivity(j);
        }

    }
}
