package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;


public class WeatherForecast extends AppCompatActivity {
    private ProgressBar progressbar;
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    protected static final String URL_IMAGE = "http://openweathermap.org/img/w/";
    private ImageView weatherImageView;
    private TextView currentTextView, minTextView, maxTextView, UVRating;
    private String uvURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImageView = (ImageView) findViewById(R.id.weatherImage);
        currentTextView = (TextView) findViewById(R.id.currentTemp);
        minTextView = (TextView) findViewById(R.id.minTemp);
        maxTextView = (TextView) findViewById(R.id.maxTemp);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        UVRating = (TextView) findViewById(R.id.UVRating);

        ForecastQuery theQuery = new ForecastQuery();
        theQuery.execute();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemp, maxTemp, currentTemp, image;
        private float windUV;
        private Bitmap weatherImage;
        @Override
        protected String doInBackground(String... strings) {
            String ret = null;
            String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";


            try{
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream,"UTF-8");

                int EVENT_TYPE;
                while((EVENT_TYPE = xpp.getEventType()) !=XmlPullParser.END_DOCUMENT){
                    switch (EVENT_TYPE){
                        case START_TAG:
                            String tagName = xpp.getName();
                            if(tagName.equals("temperature")){
                                currentTemp = xpp.getAttributeValue(null, "value");
                                publishProgress(25);

                                minTemp = xpp.getAttributeValue(null, "min");
                                publishProgress(50);

                                maxTemp = xpp.getAttributeValue(null, "max");
                                publishProgress(75);

                            }else if(tagName.equals("weather")){
                                image = xpp.getAttributeValue(null, "icon")+".png";
                                if(fileExistance(image)){
                                    FileInputStream fis = null;
                                    try{
                                        fis = openFileInput(image);
                                        Log.i(ACTIVITY_NAME, "Saved icon, " + image + " is displayed.");
                                    }catch (FileNotFoundException e){
                                        Log.i(ACTIVITY_NAME, "Saved icon, " + image + " is not found.");
                                    }
                                    weatherImage = BitmapFactory.decodeStream(fis);

                                }else{
                                    saveImage(image);
                                }
                                publishProgress(100);
                            }
                            break;
                        case END_TAG:
                            break;
                        case TEXT:
                            break;
                    }
                    xpp.next();
                }

            } catch (MalformedURLException e) {
                ret = "Malformed URL exception";
            } catch (IOException e) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            JSONObject jObject = GetJsonData(uvURL);
            try {
                windUV = (float) jObject.getDouble("value");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public void saveImage(String urlString){
            HttpURLConnection connection = null;
            try{
                URL url = new URL(URL_IMAGE + urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if(responseCode ==200){
                    weatherImage = BitmapFactory.decodeStream(connection.getInputStream());
                    FileOutputStream outputStream = openFileOutput(urlString, getBaseContext().MODE_PRIVATE);
                    weatherImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(ACTIVITY_NAME,"Weather icon, " + " is downloaded and displayed. " );
                }else{
                    Log.i(ACTIVITY_NAME, "Can't connect to the weather icon for downloading.");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public JSONObject GetJsonData(String url){
            String line = null;
            JSONObject jObject = null;
            try{
                URL u = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                line = sb.toString();
                connection.disconnect();
                is.close();
//                sb.delete(0, sb.length());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            try{
                jObject= new JSONObject(line);

            } catch (JSONException e) {
                Log.i(ACTIVITY_NAME, "Error parsing data " + e.toString());
                return null;
            }
            return jObject;
        }

        @Override
        public void onProgressUpdate(Integer... results){
            progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(results[0]);
        }

        @Override
        public void onPostExecute(String s){
            currentTextView.setText("Current " + currentTemp + "\u00b0");
            minTextView.setText("Min " + minTemp + "\u00b0");
            maxTextView.setText("Max " + maxTemp + "\u00b0");
            weatherImageView.setImageBitmap(weatherImage);
            progressbar.setVisibility(View.INVISIBLE);
            UVRating.setText("UV Rating " + windUV);
        }

    }
}
