package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_sendOrRecive = "sendOrRecive";

    public void setTablet(boolean tablet){
        isTablet = tablet;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ITEM_ID);
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment, container, false);
        int position = dataFromActivity.getInt(ChatRoomActivity.POSITION);

        //show the message
        TextView message = (TextView)result.findViewById(R.id.message);
        message.setText("message: "+dataFromActivity.getString(ITEM_SELECTED));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("Listview ID=" + dataFromActivity.getString(ITEM_ID));

        TextView sendOrRecive = (TextView)result.findViewById(R.id.sendOrRecive);
        idView.setText("messge status =" + dataFromActivity.getString(ITEM_sendOrRecive));

        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {
            if(isTablet) { //both the list and details are on the screen:
                ChatRoomActivity parent = (ChatRoomActivity)getActivity();
                parent.deleteMessageId(Integer.parseInt(Long.toString(id) ), position); //this deletes the item and updates the list
                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }else{
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(ChatRoomActivity.ITEM_ID, dataFromActivity.getLong(ChatRoomActivity.ITEM_ID ));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });
        return result;
    }
}
