package com.example.ashwathymenon2.bmi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class WelcomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    TextView tvOne;
    EditText etWeight;
    Button btnCal, btnView;
    Spinner spnFeet, spnInch;
    SharedPreferences sp1;
    GoogleApiClient gac;
    Location loc;
    String s1;
    String temp1, add;
    SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tvOne = (TextView) findViewById(R.id.tvOne);
        btnCal = (Button) findViewById(R.id.btnCal);
        btnView = (Button) findViewById(R.id.btnView);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        etWeight = (EditText) findViewById(R.id.etWeight);
        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
        sp2=getSharedPreferences("MyP2",MODE_PRIVATE);
        String rn = sp1.getString("name", "");
        String s = "Welcome " + rn;
        s1=s;
        tvOne.setText(s);
        final ArrayList<String> ft = new ArrayList<>();
        int i1;
        for (i1 = 1; i1 <= 7; i1++)
            ft.add(i1 + "");
        ArrayAdapter<String> fta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ft);
        spnFeet.setAdapter(fta);


        final ArrayList<String> ft1 = new ArrayList<>();
        for (i1 = 1; i1 <= 10; i1++)
            ft1.add(i1 + "");
        ArrayAdapter<String> fta1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ft1);
        spnInch.setAdapter(fta1);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos1 = spnFeet.getSelectedItemPosition();
                String h1 = ft.get(pos1).toString();
                int h = Integer.parseInt(h1);
                int pos2 = spnInch.getSelectedItemPosition();
                String h2 = ft1.get(pos2).toString();
                int ht = Integer.parseInt(h2);
                String wt = etWeight.getText().toString();
                if(wt.length()==0)
                {
                    etWeight.setError("Enter the  weight");
                    etWeight.requestFocus();
                    return;
                }
                int wt1 = Integer.parseInt(wt);
                double h1m = h * 0.305;
                double h2m = ht * 0.0254;
                double height = h1m + h2m;
                double bmi = wt1 / (height * height);
                String b=bmi+"";
                SharedPreferences.Editor editor = sp2.edit();

                editor.putString("bmi",b);
                editor.putString("weight", wt);

                editor.commit();
                Intent i=new Intent(WelcomeActivity.this,CalculatedResult.class);
                startActivity(i);




            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(WelcomeActivity.this,ViewHistory.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);//so that you can see the menu nut on clicking nothing will happen
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//so that when you click something happens
        if (item.getItemId() == R.id.website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.google.com"));
            startActivity(i);

        }
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "App developed by Ashwathy", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);//build or suspend...
        gac = builder.build();
        if (gac != null)
            gac.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (gac != null)
            gac.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        Geocoder g = new Geocoder(this, Locale.ENGLISH);
        if(loc!=null)
        {
        try {
            List<Address> al = g.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            Address a = al.get(0);
             temp1=a.getLocality();
             add = a.getSubAdminArea();
            tvOne.setText(s1+"\n"+add);
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
            if(!(activeNetworkInfo!=null&&activeNetworkInfo.isConnected()))
            {
                Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Task t1=new Task();
                t1.execute("http://api.openweathermap.org/data/2.5/weather?units=metric&q="+temp1+"&appid="+
                        "c6e315d09197cec231495138183954bd");

            }
        } catch (IOException e) {
            e.printStackTrace();


        }}
        else
        {

            Toast.makeText(this,"Plese enable GPS",Toast.LENGTH_SHORT).show();
        }
    }

    class Task extends AsyncTask<String,Void,Double>
    {
        double temp;


        @Override
        protected Double doInBackground(String... strings) {
            String jason="",line="";
            try
            {
                URL url=new URL(strings[0]);
                HttpURLConnection hc=(HttpURLConnection)url.openConnection();
                hc.connect();
                InputStream is=hc.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br= new BufferedReader(isr);
                while ((line=br.readLine())!=null)
                {
                    jason=jason+ line+ "\n";
                }
                if(jason!=null)
                {
                    JSONObject j=new JSONObject(jason);
                    JSONObject q=j.getJSONObject("main");
                    temp=q.getDouble("temp");
                }

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext()," "+e,Toast.LENGTH_SHORT).show();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvOne.setText(s1+"  "+add+temp + " "+ add+ "" +aDouble);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"Connection Suspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection Failed",Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();


    }

}
