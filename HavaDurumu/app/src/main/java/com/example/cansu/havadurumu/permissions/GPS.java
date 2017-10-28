package com.example.cansu.havadurumu.permissions;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class GPS implements LocationListener {

    private final Context mContext;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location; // konum
    private double latitude; // enlem
    private double longitude; // boylam

    private double latt;
    private double lott;

    // Değiştirilecek minimum mesafe Metre olarak güncelleme
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // Milisaniye cinsinden güncelleme arasındaki minimum süre
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    // Konum yöneticisi
    private LocationManager locationManager;




    public GPS(Context context) {
        this.mContext = context;
        getLocation();
    }

    private Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(Service.LOCATION_SERVICE);

            // GPS durumunu alma
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Network durumunu alma
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.i("Cant" , "Perform Action");// network sağlayıcısı etkin değil

            } else {
                this.canGetLocation = true;
                // İlk olarak network sağlayıcıdan konum al
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }
                    }
                }
                // GPS kullanılarak enlem ve boylam bilgisi alma
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }

    //Gps kullanmayı durdurma
    public void stopUsingGPS(){
        if(locationManager != null)
            try {
                locationManager.removeUpdates(GPS.this);
            }
            catch (SecurityException ex) {
                ex.printStackTrace();
            }
    }

    //Enlem bilgisi alma
    public String getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // Enlem bilgisini döndürme
        return Double.toString(latitude);
    }

  //Boylam bilgisi alma
    public String getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // boylam bilgisini döndürme
        return Double.toString(longitude);
    }

    //Gps açık olup olmadığını kontrol etme
    //Boolean sonuç döndürür
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

   //Ayarlar kısmndan GPS açma
    public void showSettingsAlert() {
        new MaterialDialog.Builder(mContext)
                .title("Enable GPS")
                .content("You need to enable GPS for this function to work")
                .positiveText("ENABLE")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                })
                .negativeText("CANCEL")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

   /* public void onLocationUpdate(Location location) {
        GetLocation(location.getLatitude(), location.getLongitude());
    }*/




    /*private void GetLocation(double latt, double lott) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();

    }*/

    @Override
    public void onLocationChanged(Location location) {
       // latt = location.getLatitude();
       // lott = location.getLongitude();

        //GetLocation(latt,lott);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}