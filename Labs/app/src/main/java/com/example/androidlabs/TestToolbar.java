package com.example.androidlabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {
    String sth = "You clicked on the overflow menu";
    Toolbar tBar;
    Toast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        tBar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lab7menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View middle = getLayoutInflater().inflate(R.layout.activity_extra_msg, null);
        EditText etext = (EditText) middle.findViewById(R.id.view_edit_text);

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.t1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                break;
            case R.id.t2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("The Message")
                        .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                myToast.setText( etext.getText().toString());
                                sth = etext.getText().toString();
                            }
                        }).setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                }).setView(middle);
                builder.create().show();
                break;
            case R.id.t3:
                Snackbar sb = Snackbar.make(tBar, "Go Back?", Snackbar.LENGTH_LONG )
                        .setAction("back", e-> finish());
                sb.show();
                break;

            case R.id.t4:
                myToast.makeText(this,sth, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
