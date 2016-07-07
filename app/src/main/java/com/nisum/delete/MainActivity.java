package com.nisum.delete;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;

import com.fasterxml.jackson.core.io.UTF8Writer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MainActivity extends Activity {

    private String SERVICE_RSS_2_JSON = "http://rss2json.com/api.json";
    private String RSS_URL = "http://www.atpworldtour.com/en/media/rss-feed/xml-feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doInBackground();
            }
        });
    }

//    private WeatherResponse mResponse;

    private void doInBackground(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadwithResponse(fetchWeatherReport());
//                mResponse = fetchWeatherReport();
            }
        });
        t.start();
    }

    private void backToMainThreadwithResponse(final AtpResponse response){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateListView(response);
            }
        });

    }

    private AtpResponse fetchWeatherReport() {
        try {

            /*Atp Tennis RSS*/

            URL url = new URL(SERVICE_RSS_2_JSON + "?rss_url="+ URLEncoder.encode(RSS_URL, "UTF-8"));
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("Please enter an HTTP URL.");
                return null;
            }
            String urlString = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String current;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }
            System.out.println(urlString);
            //convert string to json using jackson

            return (AtpResponse)Utils.fromJson(urlString,AtpResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateListView(AtpResponse response){
        // draw the items
        ListView listview = (ListView)findViewById(R.id.listView);
        MyListViewAdapter adapter = new MyListViewAdapter(this);

        listview.setAdapter(adapter);
        adapter.setData(response.items);
    }

}

