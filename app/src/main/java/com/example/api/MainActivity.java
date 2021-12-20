package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    TextView txt2;
    Button btn;

    String futureJokeString = "";
    String futureJokeString2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.textView);
        txt2 = findViewById(R.id.textView2);
        btn = findViewById(R.id.btnClick);

        btn.setOnClickListener(view -> new JokeLoader().execute());
    }

    private class JokeLoader extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            futureJokeString = "";
            futureJokeString2 = "";
            txt.setText("Загрузка...");
            txt2.setText("Загрузка...");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            String jsonString = getJson("http://worldtimeapi.org/api/ip");

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                futureJokeString = jsonObject.getString("timezone");
            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!futureJokeString.equals("")) {
                txt.setText(futureJokeString);
                txt2.setText(futureJokeString2);
            }
        }
    }


    private String getJson(String link)
    {
        String data = "";
        try {
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int t = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),
                        "utf-8"));
                data = r.readLine();
                urlConnection.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}