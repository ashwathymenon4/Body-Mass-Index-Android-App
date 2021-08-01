package com.example.ashwathymenon2.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewHistory extends AppCompatActivity {
TextView tvHis;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        tvHis=(TextView)findViewById(R.id.tvHis);
        db=new Database(this);
        tvHis.setText("");
        ArrayList<String> a=new ArrayList<String>();
        a=db.viewHis();
        if(a.size()==0)
        {
            tvHis.setText("No RECORDS");

        }
        else
        {
            for(String m: a)
            {
                tvHis.setText(tvHis.getText()+"\n"+ m);
            }
        }
    }
}
