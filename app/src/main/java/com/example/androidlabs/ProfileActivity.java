package com.example.androidlabs;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        Intent dataFromPreviousPage = getIntent();
        String emailAdd = dataFromPreviousPage.getStringExtra("emailAddress");
        EditText email = (EditText) findViewById(R.id.emailAddress);
        email.setText(emailAdd);

         mImageButton = (ImageButton) findViewById(R.id.imgButton);
         mImageButton.setOnClickListener(clk->{
             Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                 startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
             }
         });

        Log.e(ACTIVITY_NAME, "In function:" + "onCreate");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton = (ImageButton) findViewById(R.id.imgButton);
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"onStart invoked");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ACTIVITY_NAME,"onResume invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ACTIVITY_NAME,"onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(ACTIVITY_NAME,"onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(ACTIVITY_NAME,"onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ACTIVITY_NAME,"onDestroy invoked");
    }
}
