package com.example.cansu.havadurumu.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.cansu.havadurumu.utils.Constants;

import static com.example.cansu.havadurumu.utils.Constants.DEFAULT_CITY;
import static com.example.cansu.havadurumu.utils.Constants.DEFAULT_LAT;
import static com.example.cansu.havadurumu.utils.Constants.DEFAULT_LON;

public class Prefs {
    private static SharedPreferences prefs;

    public Prefs(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public String getCity() {//Ankara lokasyonu default olarak gelir.
        String city = DEFAULT_CITY;
        return prefs.getString(Constants.CITY , DEFAULT_CITY);
    }

    public void setCity(String city) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.CITY , city);
        prefsEditor.apply();
    }

    public void setLaunched() {
        prefs.edit().putBoolean(Constants.FIRST , true).apply();
    }

    public boolean getLaunched() {
        return prefs.getBoolean(Constants.FIRST , false);
    }


    public void setLatitude(float DEFAULT_LAT) {
        prefs.edit().putFloat(Constants.LATITUDE , Float.parseFloat(String.valueOf(DEFAULT_LAT))).apply();
    }

    public float getLatitude() {//Ankara enlem bilgisi default olarak alınır
        return prefs.getFloat(Constants.LATITUDE , Float.parseFloat(DEFAULT_LAT));
    }

    public void setLongitude(float DEFAULT_LON) {
        prefs.edit().putFloat(Constants.LONGITUDE , Float.parseFloat(String.valueOf(DEFAULT_LON))).apply();
    }

    public float getLongitude() {//Ankara boylam bilgisi default olarak alınır
        return prefs.getFloat(Constants.LONGITUDE , Float.parseFloat(DEFAULT_LON));
    }


    public String getUnits() {
        return prefs.getString(Constants.UNITS , "metric");
    }


    public void setv3TargetShown(boolean bool) {
        prefs.edit().putBoolean(Constants.V3TUTORIAL , bool).apply();
    }

    public boolean getv3TargetShown() {
        return prefs.getBoolean(Constants.V3TUTORIAL , false);
    }


    public String getWeatherKey() {//API key
        return prefs.getString(Constants.OWM_KEY , Constants.OWM_APP_ID);
    }
}
