package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_linear);
        //setContentView(R.layout.activity_main_relative);
        setContentView(R.layout.activity_main_grid);
//        addListenerOnButton();
    }

//    public void addListenerOnButton() {
//
//        imageButton = (ImageButton) findViewById(R.id.imageButton1);
//
//        imageButton.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                Toast.makeText(MyAndroidAppActivity.this,
//                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//    }


}
