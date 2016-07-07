package com.nisum.delete;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;


public class MainActivity extends Activity {

    private String RSS_URL = "http://news.yahoo.com/rss/entertainment";

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

    private void doInBackground(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(fetchAtpTennisReport());
            }
        });
        t.start();
    }

    private void backToMainThreadWithResponse(final AtpResponse response){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                updateListView(response);
            }
        });

    }

    private AtpResponse fetchAtpTennisReport() {
        try {

            URL url = new URL(RSS_URL);
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

            JSONObject xmlJSONObj = XML.toJSONObject(urlString);
            xmlJSONObj = xmlJSONObj.getJSONObject("rss").getJSONObject("channel");
            return (AtpResponse)Utils.fromJson(xmlJSONObj.toString(),AtpResponse.class);

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
        adapter.setData(response.item);
    }

}

