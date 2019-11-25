package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private Button send;
    private Button receive;
    private EditText chatText;

    private boolean sendMsg;
    MyListAdapter mylist;
    private Message message;
    private ArrayList<Message> chatMessage = new ArrayList<>();
    myDatabaseOpenHelper myHelper  = new myDatabaseOpenHelper(this);

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_sendOrRecive = "sendOrRecive";
    public static final int EMPTY_ACTIVITY = 345;
    public static final String POSITION = "POSITION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);

        ListView listView = (ListView) findViewById(R.id.list_of_message);

        chatText = (EditText) findViewById(R.id.message);

        SQLiteDatabase db = myHelper.getWritableDatabase();

        String [] columns = {myDatabaseOpenHelper.COL_ID, myDatabaseOpenHelper.COL_MESSAGE, myDatabaseOpenHelper.COL_SENDORRE};

        Cursor results =  db.query(false, myHelper.TABLE_NAME, columns,null, null,null,null,null,null);

        printCursor(results);

        send = (Button) findViewById(R.id.send_button);

        send.setOnClickListener( clk ->{
            sendMsg = true;
            message = new Message(chatText.getText().toString(), true);
            mylist.add(message);
            chatText.getText().clear();
            myHelper.addData(message.getMsg(), message.getResponse());
        });

//        receive = (Button) findViewById(R.id.receive_button);
//        receive.setOnClickListener( clk ->{
//            sendMsg = false;
//            message = new Message(chatText.getText().toString(), false);
//            mylist.add(message);
//            chatText.getText().clear();
//            myHelper.addData(message.getMsg(), message.getResponse());
//        });


        boolean isTable = findViewById(R.id.fragmentLocation) != null;

        listView.setAdapter(mylist= new MyListAdapter());
        listView.setOnItemClickListener((list, item, position, id )->{
            Bundle dataToPass = new Bundle();

            Message message = (Message)list.getItemAtPosition(position);

            dataToPass.putString(ITEM_SELECTED,message.getMsg());

            dataToPass.putLong(ITEM_ID, message.getID());

            dataToPass.putInt(POSITION, position);

            Boolean response = chatMessage.get(position).getResponse();
            String sendOrResponse;
            if(response){
                sendOrResponse = "send";
            }else
                sendOrResponse = "recive";
            dataToPass.putString(ITEM_sendOrRecive, sendOrResponse);

            if(isTable){
                DetailFragment dFragment = new DetailFragment ();
                dFragment.setArguments(dataToPass);
                dFragment.setTablet(true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment)
//                        .add(R.id.fragmentLocation,dFragment)
//                        .addToBackStack("AnyName")
                        .commit();
            }else{
                Intent emptyActity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                emptyActity.putExtras(dataToPass); //send data to next activity
                startActivityForResult(emptyActity, EMPTY_ACTIVITY); //make the transition
            }
        });

    }


    public void printCursor(Cursor cursor){
        String TAG = "PrintCursor";
        Log.i(TAG, "db version number "+ myHelper.VERSION_NUM);
        Log.i(TAG, "db total column "+ cursor.getColumnCount());
        Log.i(TAG, "db columns name "+ Arrays.toString(cursor.getColumnNames()));
        Log.i(TAG, "db number results "+ cursor.getCount());
        Log.i(TAG, "db row of results "+ cursor.getCount());


        int msgColumnIndex = cursor.getColumnIndex(myHelper.COL_MESSAGE);
        int srColumnIndex = cursor.getColumnIndex(myHelper.COL_SENDORRE);
        int idColumnIndex = cursor.getColumnIndex(myHelper.COL_ID);

        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            String message = cursor.getString(msgColumnIndex);
            //here transform sr to number  1 or 0;
         //   boolean isSend = cursor.getInt
            sendMsg = cursor.getInt(srColumnIndex)>0;
            long id = cursor.getLong(idColumnIndex);
            chatMessage.add(new Message(message,sendMsg,id));
        }
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
                sendMsg = getItem(position).getResponse();
                if(sendMsg){
                    TextView sendChat;
                    newView = inflater.inflate(R.layout.message_send, null);
                    sendChat = (TextView) newView.findViewById(R.id.sendText);;
                    sendChat.setText(message.getMsg());

                }else{
                    TextView receiveChat;
                    newView = inflater.inflate(R.layout.message_receive, null);
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
    public void deleteMessageId(int id, int position)
    {
        myHelper.deleteEntry(id);
        chatMessage.remove(mylist.getItem(position));
        mylist.notifyDataSetChanged();
    }

    //This function only gets called on the phone. The tablet never goes to a new activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EMPTY_ACTIVITY)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long ID = data.getLongExtra(ITEM_ID, 0);

                int position = data.getIntExtra(POSITION, 0);

                deleteMessageId(Integer.parseInt(Long.toString(ID)), position);
            }
        }
    }
}


