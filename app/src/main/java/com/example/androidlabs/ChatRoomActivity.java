package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private Button send;
    private Button receive;
    private EditText chatText;

    private boolean sendMsg = true;
    MyListAdapter mylist;

    private ArrayList<Message> chatMessage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

        ListView listView = (ListView) findViewById(R.id.list_of_message);

        chatText = (EditText) findViewById(R.id.message);

        send = (Button) findViewById(R.id.send_button);
        send.setOnClickListener( clk ->{
            sendMsg = true;
            mylist.add(new Message(chatText.getText().toString(), sendMsg));
//            listView.setSelection(chatMessage.size() - 1);
            chatText.getText().clear();
        });

        receive = (Button) findViewById(R.id.receive_button);
        receive.setOnClickListener( clk ->{
            sendMsg = false;
            mylist.add(new Message(chatText.getText().toString(), sendMsg));
//            listView.setSelection(chatMessage.size() - 1);
            chatText.getText().clear();
        });
//        mylist.add(new Message("hello", sendMsg));
//        mylist.add(new Message("who is that?", !sendMsg));

        listView.setAdapter(mylist= new MyListAdapter());
    }

    public class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return chatMessage.size();
        }

        @Override
        public Message getItem(int position) {
            return chatMessage.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View newView, ViewGroup viewGroup) {
            Message message = getItem(position);
            if (newView ==null){
                LayoutInflater inflater = getLayoutInflater();
                if(sendMsg){
                    TextView sendChat;
                    newView = inflater.inflate(R.layout.message_send, viewGroup, false);
                    sendChat = (TextView) newView.findViewById(R.id.sendText);;
                    sendChat.setText(message.getMsg());

                }else{
                    TextView receiveChat;
                    newView = inflater.inflate(R.layout.message_receive, viewGroup, false);
                    receiveChat = (TextView) newView.findViewById(R.id.receiveText);
                    receiveChat.setText(message.getMsg());
                }
            }

            return newView;
        }

        public void add(Message message){
            chatMessage.add(message);
            notifyDataSetChanged();
        }
    }
}


