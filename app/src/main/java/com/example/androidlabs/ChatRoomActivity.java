package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private MyListAdapter mylist;
    private Message message;
    private ArrayList<Message> chatMessage = new ArrayList<Message>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

        ListView listView = (ListView) findViewById(R.id.list_of_message);

        chatText = (EditText) findViewById(R.id.message);

        send = (Button) findViewById(R.id.send_button);

        myDatabaseOpenHelper myHelper  = new myDatabaseOpenHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        String [] columns = {myDatabaseOpenHelper.COL_ID, myDatabaseOpenHelper.COL_MESSAGE, myDatabaseOpenHelper.COL_SENDORRE};

        Cursor results =  db.query(false, myHelper.TABLE_NAME, columns,null, null,null,null,null,null);

        int msgColumnIndex = results.getColumnIndex(myHelper.COL_MESSAGE);
        int srColumnIndex = results.getColumnIndex(myHelper.COL_SENDORRE);
        int idColumnIndex = results.getColumnIndex(myHelper.COL_ID);

        while(results.moveToNext()){
            String message = results.getString(msgColumnIndex);
            //here transform sr to number  1 or 0;
            Boolean sr = results.getInt(srColumnIndex)>0;
            long id = results.getLong(idColumnIndex);
            chatMessage.add(new Message(message,sr,id));
        }

        send.setOnClickListener( clk ->{
            sendMsg = true;
            message = new Message(chatText.getText().toString(), sendMsg);
            mylist.add(message);
            listView.setSelection(chatMessage.size() - 1);
            chatText.getText().clear();
            myHelper.addData(message.getMsg(),message.getResponse());
        });

        receive = (Button) findViewById(R.id.receive_button);
        receive.setOnClickListener( clk ->{
            sendMsg = false;
            message =new Message(chatText.getText().toString(), sendMsg);
            mylist.add(message);
            listView.setSelection(chatMessage.size() - 1);
            chatText.getText().clear();
            myHelper.addData(message.getMsg(),message.getResponse());
        });

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
            LayoutInflater inflater = getLayoutInflater();
            if(newView==null){
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


