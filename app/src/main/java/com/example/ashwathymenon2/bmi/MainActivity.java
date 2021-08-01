package com.example.ashwathymenon2.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etName, etAge, etPhone;
    Button btnReg;
    SharedPreferences sp1;
    RadioGroup rbGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnReg = (Button) findViewById(R.id.btnReg);
        rbGender = (RadioGroup) findViewById(R.id.rbGender);
        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);

        String n = sp1.getString("name", "");

        if (!n.equals("")) {
            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(i);
            finish();
        } else {
            btnReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int r1 = rbGender.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(r1);
                    String radio = rb.getText().toString();
                    String name = etName.getText().toString();
                    if (name.length() == 0) {
                        etName.setError("Name is empty");
                        etName.requestFocus();
                        return;
                    }
                    String age = etAge.getText().toString();
                    if (age.length() == 0) {
                        etAge.setError("Age is empty");
                        etAge.requestFocus();
                        return;
                    }
                    String phone = etPhone.getText().toString();
                    if (phone.length() == 0 || phone.length() != 10) {
                        etPhone.setError("Invalid phone number");
                        etPhone.requestFocus();
                        return;
                    }
                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("name", name);
                    editor.putString("age", age);
                    editor.putString("phone", phone);
                    editor.putString("radio",radio);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etAge.setText("");
                    etPhone.setText("");
                    Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
            });

        }
    }
}