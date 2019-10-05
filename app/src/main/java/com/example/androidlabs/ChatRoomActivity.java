package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<String> objects = new ArrayList<String>();
    BaseAdapter myAdapter;
    Button send;
    Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(myAdapter = new MyListAdapter());
        send.setOnClickListener( clk ->{

        });

        receive.setOnClickListener( clk ->{

        });


    }


    public class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int p) {
            return p;
        }

        @Override
        public View getView(int p, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}


