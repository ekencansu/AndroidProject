package com.example.cansu.havadurumu.internet;

import android.content.Context;
import android.util.Log;

import com.example.cansu.havadurumu.model.WeatherInfo;
import com.example.cansu.havadurumu.preferences.Prefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request
{

    public Context context;

    public Request(Context mContext) {
        this.context = mContext;
    }

    private WeatherInfo gsonWeather(URL url) throws IOException {

        HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
        connection1.addRequestProperty("x-api-key", new Prefs(context).getWeatherKey());

        InputStream content = connection1.getInputStream();

        try {
            //Sunucu yanıtını okuma ve json olarak ayrıştırma
            Reader reader = new InputStreamReader(content);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            WeatherInfo posts = gson.fromJson(reader, WeatherInfo.class);
            System.out.println(gson.toJson(posts));
            content.close();
            return posts;
        } catch (Exception ex) {
            Log.e("GetWeather", "Failed to parse JSON due to: " + ex);
        }
        return null;
    }

}
