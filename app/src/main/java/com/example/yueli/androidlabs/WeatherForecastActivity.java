package com.example.yueli.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WeatherForecastActivity extends Activity {

    TextView tv_current_temperature,tv_min_temperature,tv_max_temperature,tv_wind_speed;
    ProgressBar progressBar;
    ImageView imageView;
    private static final String TAG = "WeatherForecastActivity";

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setReadTimeout(10000 /* milliseconds */);
            httpConn.setConnectTimeout(15000 /* milliseconds */);
            httpConn.setRequestMethod("GET");
            httpConn.setDoInput(true);
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.i(TAG, ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private class ForecastQueryTask extends AsyncTask<String, Integer, Bitmap> {
        String wind_speed;
        String current_temp, min_temp, max_temp;
        Bitmap bitmap = null;
        InputStream in = null;

        @Override
        protected Bitmap doInBackground(String... urls) {
            String iconName = "";
            URL url = null;

            try {
                in = OpenHttpConnection(urls[0]);
                //in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                //return readFeed(parser);

                //parser.require(XmlPullParser.START_TAG, null, "current");
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    if (name.equals("temperature")) {
                        current_temp = parser.getAttributeValue(null,"value");
                        publishProgress(25);
                        min_temp = parser.getAttributeValue(null,"min");
                        publishProgress(50);
                        max_temp = parser.getAttributeValue(null,"max");
                        publishProgress(75);
                    }
                    if (name.equals("speed")) {
                        wind_speed = parser.getAttributeValue(null,"value");
                        //publishProgress(88);
                    }
                    if (name.equals("weather")) {
                        iconName = parser.getAttributeValue(null,"icon");
                        //publishProgress(88);
                    }
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


            if (!fileExistance(iconName+".png")) {
                Log.i(TAG, iconName+".png"+" is not found locally, downloading it now");
                Bitmap image = HttpUtils.getImage("http://openweathermap.org/img/w/" + iconName + ".png");
                try{
                    FileOutputStream outputStream = openFileOutput(iconName+".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Log.i(TAG, iconName+".png"+" is found locally");
            }

            FileInputStream fis = null;
            try{
                fis = openFileInput(iconName + ".png");
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(fis);

            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            //super.onPostExecute(result);

            tv_current_temperature=(TextView)findViewById(R.id.textView6);
            tv_min_temperature=(TextView)findViewById(R.id.textView7);
            tv_max_temperature=(TextView)findViewById(R.id.textView8);
            tv_wind_speed=(TextView)findViewById(R.id.textView9);
            imageView=(ImageView)findViewById(R.id.imageView);

            tv_current_temperature.setText("current temperature:" + current_temp);
            tv_min_temperature.setText("min temperature:" + min_temp);
            tv_max_temperature.setText("max temperature:" + max_temp);
            tv_wind_speed.setText("wind speed:" + wind_speed);
            imageView.setImageBitmap(bitmap);
            //imageView.setImageBitmap(bm);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);


        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(50);
        //new ForecastQueryTask().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
        new ForecastQueryTask().execute("http://api.openweathermap.org/data/2.5/weather?q=toronto,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }
}
