package com.example.ashwathymenon2.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculatedResult extends AppCompatActivity {
    TextView tvRes,tvCal,tv2,tv3,tv4;
    Button btnBack,btnSave,btnShare;
    SharedPreferences sp2;
    SharedPreferences sp1;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculated_result);
        tvRes=(TextView)findViewById(R.id.tvRes);
        tvCal=(TextView)findViewById(R.id.tvCal);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnShare=(Button)findViewById(R.id.btnShare);
        sp2=getSharedPreferences("MyP2",MODE_PRIVATE);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
        final String bmi=sp2.getString("bmi","");
      final  String weight=sp2.getString("weight","");
        double b=Double.parseDouble(bmi);
       int w= Integer.parseInt(weight);
        String s="Your BMI  is "+bmi+".";
        db=new Database(this);

       if(b<18.5)
        {

            s=s+" You are underweight.";
            tvRes.setText(s);
            tvRes.setTextColor(Color.parseColor("#280707"));
            tvCal.setText("Below 18.5 is Underweight");
            tvCal.setTextColor(Color.parseColor("#a71d3d"));
            tv2.setText("Between 18.5 to 25 is Normal");
            tv2.setTextColor(Color.parseColor("#417143"));
            tv3.setText("Between 25 to 30 is overweight");
            tv3.setTextColor(Color.parseColor("#417143"));
            tv4.setText("More than 30 is Obese");
            tv4.setTextColor(Color.parseColor("#417143"));
        }
        else if(b>=18.5&&b<=25)
       {
           s=s+" You are normal";
           tvRes.setText(s);
           tvRes.setTextColor(Color.parseColor("#280707"));
           tvCal.setText("Below 18.5 is Underweight");
           tvCal.setTextColor(Color.parseColor("#417143"));
           tv2.setText("Between 18.5 to 25 is Normal");
           tv2.setTextColor(Color.parseColor("#a71d3d"));
           tv3.setText("Between 25 to 30 is overweight");
           tv3.setTextColor(Color.parseColor("#417143"));
           tv4.setText("More than 30 is Obese");
           tv4.setTextColor(Color.parseColor("#417143"));

       }
        else if(b>25&&b<=30)
       {
           s=s+"You are overweight";
           tvRes.setText(s);
           tvRes.setTextColor(Color.parseColor("#280707"));
           tvCal.setText("Below 18.5 is Underweight");
           tvCal.setTextColor(Color.parseColor("#417143"));
           tv2.setText("Between 18.5 to 25 is Normal");
           tv2.setTextColor(Color.parseColor("#417143"));
           tv3.setText("Between 25 to 30 is overweight");
           tv3.setTextColor(Color.parseColor("#a71d3d"));
           tv4.setText("More than 30 is Obese");
           tv4.setTextColor(Color.parseColor("#417143"));

       }
        else
       {
           s=s+" You are obese";
           tvRes.setText(s);
           tvRes.setTextColor(Color.parseColor("#280707"));
           tvCal.setText("Below 18.5 is Underweight");
           tvCal.setTextColor(Color.parseColor("#417143"));
           tv2.setText("Between 18.5 to 25 is Normal");
           tv2.setTextColor(Color.parseColor("#417143"));
           tv3.setText("Between 25 to 30 is overweight");
           tv3.setTextColor(Color.parseColor("#417143"));
           tv4.setText("More than 30 is Obese");
           tv4.setTextColor(Color.parseColor("#a71d3d"));

       }
       final String s2=s;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1;
                String name=sp1.getString("name","");
                String age=sp1.getString("age","");
                String gen=sp1.getString("radio","");
                String phone=sp1.getString("phone","");

                s1="Name :"+name+"\nAge :"+age+"\nPhone :"+phone+"\nGender :"+gen+"\n"+s2;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, s1);
                startActivity(Intent.createChooser(i, "share via"));

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                String finalbmi="BMI is "+ bmi+ "Weight is "+ weight;
               long rid= db.addRecord(date,finalbmi);
                if(rid>0)
               Toast.makeText(getApplicationContext(),"Record inserted",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
