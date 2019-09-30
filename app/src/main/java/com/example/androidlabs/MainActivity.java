package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        EditText editText =(EditText) findViewById(R.id.email);
        //read from file
        sharedPreferences = getSharedPreferences("FileName", MODE_PRIVATE);
        String defaultEmail = sharedPreferences.getString("emailAddress", "");
        editText.setText(defaultEmail);

        Button page1Button = (Button) findViewById(R.id.logInButton);
        if(page1Button!=null){
            page1Button.setOnClickListener(clk-> {
                try {
                    Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                    goToProfile.putExtra("emailAddress", editText.getText().toString());
                    startActivity(goToProfile);
                } catch ( ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        //save user input
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText editText = (EditText) findViewById(R.id.email);
        editor.putString("defaultMail", editText.getText().toString());
        editor.commit();
    }
}
