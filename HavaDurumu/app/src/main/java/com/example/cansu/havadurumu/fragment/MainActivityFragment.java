package com.example.cansu.havadurumu.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.cansu.havadurumu.R;
import com.example.cansu.havadurumu.internet.Connection;
import com.example.cansu.havadurumu.internet.GetWeather;
import com.example.cansu.havadurumu.model.Info;
import com.example.cansu.havadurumu.model.WeatherFort;
import com.example.cansu.havadurumu.model.WeatherInfo;
import com.example.cansu.havadurumu.permissions.GPS;
import com.example.cansu.havadurumu.permissions.Permissions;
import com.example.cansu.havadurumu.preferences.Prefs;
import com.example.cansu.havadurumu.utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivityFragment extends Fragment {

    Typeface weatherFont;

    @BindView(R.id.button1) TextView button;
    TextView detailsField[] = new TextView[10] , weatherIcon[] = new TextView[11];
    @BindView(R.id.wind_view) TextView windView;
    @BindView(R.id.humidity_view) TextView humidityView;
    @BindView(R.id.direction_view) TextView directionView;
    @BindView(R.id.daily_view) TextView dailyView;
    @BindView(R.id.updated_field) TextView updatedField;
    @BindView(R.id.city_field) TextView cityField;
    @BindView(R.id.sunrise_view) TextView sunriseView;
    @BindView(R.id.sunset_view) TextView sunsetView;
    @BindView(R.id.sunrise_icon) TextView sunriseIcon;
    @BindView(R.id.sunset_icon) TextView sunsetIcon;
    @BindView(R.id.wind_icon) TextView windIcon;
    @BindView(R.id.humidity_icon) TextView humidityIcon;
    @BindView(R.id.horizontalScrollView) HorizontalScrollView horizontalScrollView;

    double tc;
    Handler handler;
    WeatherInfo json0;
    WeatherFort json1;
    @BindView(R.id.swipe) SwipeRefreshLayout swipeView;//Aktivity i yenilemek için

    Connection cc;
    Info json;
    MaterialDialog pd;
    GetWeather wt;
    Prefs preferences;
    GPS gps;
    View rootView;
    Permissions permission;

    public MainActivityFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);// bu MainActivityfragment'in layout'unu hazır hale getirir.
        ButterKnife.bind(this , rootView);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this.getActivity())
                .title(getString(R.string.please_wait))
                .content(getString(R.string.loading))
                .cancelable(false)
                .progress(true , 0);
        pd = builder.build();

        setHasOptionsMenu(true);
        preferences = new Prefs(getContext());
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        Bundle bundle = getArguments();
        int mode;
        if (bundle != null)
            mode = bundle.getInt(Constants.MODE , 0);
        else
            mode = 0;
        if (mode == 0)
            updateWeatherData(preferences.getCity(), null, null);//şehir hava durumu bilgisi güncelleme
        else
            updateWeatherData(null , Float.toString(preferences.getLatitude()) , Float.toString(preferences.getLongitude()));
        gps = new GPS(getContext());
        cityField.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        updatedField.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        humidityView.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        sunriseIcon.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        sunriseIcon.setTypeface(weatherFont);
        sunriseIcon.setText(getActivity().getString(R.string.sunrise_icon));
        sunsetIcon.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        sunsetIcon.setTypeface(weatherFont);
        sunsetIcon.setText(getActivity().getString(R.string.sunset_icon));
        windIcon.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        windIcon.setTypeface(weatherFont);
        windIcon.setText(getActivity().getString(R.string.speed_icon));
        humidityIcon.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        humidityIcon.setTypeface(weatherFont);
        humidityIcon.setText(getActivity().getString(R.string.humidity_icon));
        windView.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changeCity(preferences.getCity());
                    }
                });
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                    }
                }, 10000);
            }
        });
        directionView.setTypeface(weatherFont);
        directionView.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        dailyView.setText(getString(R.string.daily));
        dailyView.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        sunriseView.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        sunsetView.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        button.setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        pd.show();
        for (int i = 0; i < 11; ++i)//10 günlük hava durumu bilgisi alınır (daily kısım)
        {
            String f = "details_view" + (i + 1) , g = "weather_icon" + (i + 1);
            if (i != 10) {
                int resID = getResources().getIdentifier(f, "id", getContext().getPackageName());
                detailsField[i] = (TextView) rootView.findViewById(resID);
                detailsField[i].setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
            }
            int resIDI = getResources().getIdentifier(g, "id" , getContext().getPackageName());
            weatherIcon[i] = (TextView)rootView.findViewById(resIDI);
            weatherIcon[i].setTypeface(weatherFont);
            weatherIcon[i].setTextColor(ContextCompat.getColor(getContext() , R.color.textColor));
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {//search ve gps enable (location)
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location :
                permission = new Permissions(getContext());
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION} , Constants.READ_COARSE_LOCATION);
                break;
            case R.id.search :
                showMenuInputDialog();

                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            cc = new Connection(getContext());//internet olup olmadığını kontrol eder
            if (!cc.isNetworkAvailable())
                showNoInternet();
            else {//internet var ise hava durumu bilgilerini getirir.
                pd.show();
                updateWeatherData(preferences.getCity(), null, null);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }

    private void updateWeatherData(final String city, final String lat, final String lon) {// şehirin konum bilgisi alınarak havadurumu bilgileri getirilir
        wt = new GetWeather(getContext());
        new Thread() {
            public void run() {
                try {
                    if (lat == null && lon == null) {
                        json = wt.execute(city).get();
                    } else if (city == null) {
                        json = wt.execute(lat, lon).get();
                    }
                }
                catch (InterruptedException iex) {
                    Log.e("InterruptedException" , "iex");
                }
                catch (ExecutionException eex) {
                    Log.e("ExecutionException" , "eex");
                }
                if (pd.isShowing())
                    pd.dismiss();
                if (swipeView != null && swipeView.isRefreshing())
                    swipeView.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeView.setRefreshing(false);
                        }
                    });
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                            if (!preferences.getLaunched()) {

                            } else {
                                cc = new Connection(getContext());
                                if (!cc.isNetworkAvailable()) {
                                    showNoInternet();
                                }
                                else {
                                    if (pd.isShowing())
                                        pd.dismiss();
                                    showInputDialog();
                                }
                            }
                        }
                    });
                }
                else {
                    handler.post(new Runnable() {
                        public void run() {//şehir bilgisini post eder
                            preferences.setLaunched();
                            renderWeather(json);
                            Snackbar snackbar = Snackbar.make(rootView, "Loaded Weather Data", 500);
                            snackbar.show();



                        }
                    });
                }
            }
        }.start();
    }


    public List<WeatherFort.WeatherList> getDailyJson() {
        return json.fort.getList();
    }//günlük hava durumu bilgisini liste içerisinden alır

    public void changeCity(String city)//şehir bilgisi alarak hava durumu bilgisini güncelleme
    {
        if (!swipeView.isRefreshing())
            pd.show();
        updateWeatherData(city, null, null);
        preferences.setCity(city);
    }

    public void changeCity(String lat , String lon)//şehir enlem ve boylam bilgisi alarak hava durumu bilgisi güncelleme
    {
        pd.show();
        updateWeatherData(null, lat, lon);
    }

    private void showInputDialog() {
        new MaterialDialog.Builder(this.getActivity())
                .title(getString(R.string.change_city))
                .content(getString(R.string.could_not_find))
                .negativeText(getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog , @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, @NonNull CharSequence input) {
                        changeCity(input.toString());
                    }
                })
                .cancelable(false)
                .show();
    }

    public void showNoInternet() {// Eger internet yok ise wifi veya mobil ağ seceneği getirirlir
        new MaterialDialog.Builder(getContext())
                .title(getString(R.string.no_internet_title))
                .cancelable(false)
                .content(getString(R.string.no_internet_content))
                .positiveText(getString(R.string.no_internet_mobile_data))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
                        startActivityForResult(intent , 0);
                    }
                })
                .negativeText(getString(R.string.no_internet_wifi))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS) , 0);
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode ,
                                           @NonNull String permissions[] ,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.READ_COARSE_LOCATION: {
                // İstek iptal edilirse, sonuç dizileri boş olur.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCity();
                } else {
                    permission.permissionDenied();
                }
                break;
            }
        }
    }

    private void showCity() {//gps enable ve konum alma
        gps = new GPS(getContext());
        if (!gps.canGetLocation())
            gps.showSettingsAlert();
        else {
            String lat = gps.getLatitude();
            String lon = gps.getLongitude();
            changeCity(lat, lon);
        }
    }

    private void setWeatherIcon(int id , int i) {//hava durumu simgeleri
        String icon = "";
        if (i == 10) {
            if (checkDay())
                switch (id) {
                    case 501:
                        icon = getActivity().getString(R.string.day_drizzle);
                        break;
                    case 500:
                        icon = getActivity().getString(R.string.day_drizzle);
                        break;
                    case 502:
                        icon = getActivity().getString(R.string.day_rainy);
                        break;
                    case 503:
                        icon = getActivity().getString(R.string.day_rainy);
                        break;
                    case 504:
                        icon = getActivity().getString(R.string.day_rainy);
                        break;
                    case 511:
                        icon = getActivity().getString(R.string.day_rain_wind);
                        break;
                    case 520:
                        icon = getActivity().getString(R.string.day_rain_drizzle);
                        break;
                    case 521:
                        icon = getActivity().getString(R.string.day_drizzle);
                        break;
                    case 522:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 531:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 200:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 201:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 202:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 210:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 211:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 212:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 221:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 230:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 231:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 232:
                        icon = getActivity().getString(R.string.day_thunder);
                        break;
                    case 300:
                        icon = getActivity().getString(R.string.day_rain_drizzle);
                        break;
                    case 301:
                        icon = getActivity().getString(R.string.day_rain_drizzle);
                        break;
                    case 302:
                        icon = getActivity().getString(R.string.day_heavy_drizzle);
                        break;
                    case 310:
                        icon = getActivity().getString(R.string.day_rain_drizzle);
                        break;
                    case 311:
                        icon = getActivity().getString(R.string.day_rain_drizzle);
                        break;
                    case 312:
                        icon = getActivity().getString(R.string.day_heavy_drizzle);
                        break;
                    case 313:
                        icon = getActivity().getString(R.string.day_rain_drizzle);
                        break;
                    case 314:
                        icon = getActivity().getString(R.string.day_heavy_drizzle);
                        break;
                    case 321:
                        icon = getActivity().getString(R.string.day_heavy_drizzle);
                        break;
                    case 600:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 601:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 602:
                        icon = getActivity().getString(R.string.snow);
                        break;
                    case 611:
                        icon = getActivity().getString(R.string.sleet);
                        break;
                    case 612:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 903:
                    case 615:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 616:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 620:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 621:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 622:
                        icon = getActivity().getString(R.string.day_snowy);
                        break;
                    case 701:
                    case 702:
                    case 721:
                        icon = getActivity().getString(R.string.smoke);
                        break;
                    case 751:
                    case 761:
                    case 731:
                        icon = getActivity().getString(R.string.dust);
                        break;
                    case 741:
                        icon = getActivity().getString(R.string.fog);
                        break;
                    case 762:
                        icon = getActivity().getString(R.string.volcano);
                        break;
                    case 771:
                    case 900:
                    case 781:
                        icon = getActivity().getString(R.string.tornado);
                        break;
                    case 904:
                        icon = getActivity().getString(R.string.day_clear);
                        break;
                    case 800:
                        icon = getActivity().getString(R.string.day_clear);
                        break;
                    case 801:
                        icon = getActivity().getString(R.string.day_cloudy);
                        break;
                    case 802:
                        icon = getActivity().getString(R.string.day_cloudy);
                        break;
                    case 803:
                        icon = getActivity().getString(R.string.day_cloudy);
                        break;
                    case 804:
                        icon = getActivity().getString(R.string.day_cloudy);
                        break;
                    case 901:
                        icon = getActivity().getString(R.string.storm_showers);
                        break;
                    case 902:
                        icon = getActivity().getString(R.string.hurricane);
                        break;
                }
            else
                switch (id) {
                    case 903:
                    case 701:
                    case 702:
                    case 721:
                        icon = getActivity().getString(R.string.smoke);
                        break;
                    case 751:
                    case 761:
                    case 731:
                        icon = getActivity().getString(R.string.dust);
                        break;
                    case 741:
                        icon = getActivity().getString(R.string.fog);
                        break;
                    case 762:
                        icon = getActivity().getString(R.string.volcano);
                        break;
                    case 771:
                    case 900:
                    case 781:
                        icon = getActivity().getString(R.string.tornado);
                        break;
                    case 901:
                        icon = getActivity().getString(R.string.storm_showers);
                        break;
                    case 902:
                        icon = getActivity().getString(R.string.hurricane);
                        break;
                }
        }
        else {
            switch(id) {
                case 501 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 500 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 502 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 503 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 504 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
                case 511 : icon = getActivity().getString(R.string.weather_rain_wind);
                    break;
                case 520 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 521 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 522 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 531 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 200 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 201 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 202 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 210 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 211 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 212 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 221 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 230 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 231 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 232 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 300 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 301 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 302 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 310 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 311 : icon = getActivity().getString(R.string.weather_shower_rain);
                    break;
                case 312 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 313 : icon = getActivity().getString(R.string.weather_rain_drizzle);
                    break;
                case 314 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 321 : icon = getActivity().getString(R.string.weather_heavy_drizzle);
                    break;
                case 600 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 601 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 602 : icon = getActivity().getString(R.string.weather_heavy_snow);
                    break;
                case 611 : icon = getActivity().getString(R.string.weather_sleet);
                    break;
                case 612 : icon = getActivity().getString(R.string.weather_heavy_snow);
                    break;
                case 903 :
                case 615 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 616 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 620 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 621 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 622 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 701 :
                case 702 :
                case 721 : icon = getActivity().getString(R.string.weather_smoke);
                    break;
                case 751 :
                case 761 :
                case 731 : icon = getActivity().getString(R.string.weather_dust);
                    break;
                case 741 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 762 : icon = getActivity().getString(R.string.weather_volcano);
                    break;
                case 771 :
                case 900 :
                case 781 : icon = getActivity().getString(R.string.weather_tornado);
                    break;
                case 904 : icon = getActivity().getString(R.string.weather_sunny);
                    break;
                case 800 : icon = getActivity().getString(R.string.weather_sunny);
                    break;
                case 801 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 802 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 803 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 804 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 901 : icon = getActivity().getString(R.string.weather_storm);
                    break;
                case 902 : icon = getActivity().getString(R.string.weather_hurricane);
                    break;
            }
        }
        weatherIcon[i].setText(icon);
    }

    private boolean checkDay() {//takvimden gün bilgisini alma
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);

        return !(hours >= 18 || hours <= 6);
    }

    private void renderWeather(Info jsonObj){//json dan şehir konum bilgisi ve hava durumu bilgilerini çekme
        try {
            json0 = jsonObj.day;
            json1 = jsonObj.fort;
            tc = json0.getMain().getTemp();
            preferences.setLatitude((float) json1.getCity().getCoord().getLatitude());
            preferences.setLongitude((float) json1.getCity().getCoord().getLongitude());
            preferences.setCity(json1.getCity().getName());
            int a = (int) Math.round(json0.getMain().getTemp());
            final String city = json1.getCity().getName().toUpperCase(Locale.US) +
                    ", " +
                    json1.getCity().getCountry();
            cityField.setText(city);
            cityField.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {
                    Snackbar.make(v, city, Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
            Log.i("Location" , "Location Received");
            List<WeatherFort.WeatherList> details = json1.getList();
            for (int i = 0; i < 10; ++i)
            {
                details.set(i , json1.getList().get(i));
            }
            for (int i = 0; i < 10; ++i)//daily hava durumu bilgisi
            {
                final WeatherFort.WeatherList J = details.get(i);
                long date1 = J.getDt();
                Date expiry = new Date(date1 * 1000);
                String date = new SimpleDateFormat("EE, dd" , Locale.US).format(expiry);
                SpannableString ss1 = new SpannableString(date + "\n"
                        + J.getTemp().getMax() + "°" + "      "
                        + J.getTemp().getMin() + "°" + "\n");
                ss1.setSpan(new RelativeSizeSpan(1.1f) , 0 , 7 , 0); // uzunluk set edildi.
                ss1.setSpan(new RelativeSizeSpan(1.4f) , 8 , 12 , 0);
                detailsField[i].setText(ss1);//Günlük hava durumu bilgisini getir
                setWeatherIcon( (int) J.getWeather().get(0).getId(), i);
                detailsField[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                    }
                });

                    }

            final String d1 = new java.text.SimpleDateFormat("hh:mm a" , Locale.US).format(new Date(json0.getSys().getSunrise() * 1000));
            final String d2 = new java.text.SimpleDateFormat("hh:mm a" , Locale.US).format(new Date(json0.getSys().getSunset() * 1000));
            sunriseView.setText(d1);
            sunsetView.setText(d2);
            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = "Last update: " + df.format(new Date(json0.getDt() * 1000));
            updatedField.setText(updatedOn);
            final String humidity = String.format(getString(R.string.humidity_) , json0.getMain().getHumidity());
            humidityView.setText(humidity);
            humidityIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.humidity) , json0.getMain().getHumidity()) + " %" , Snackbar.LENGTH_SHORT).show();
                }
            });
            humidityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.humidity) , json0.getMain().getHumidity()) + " %" , Snackbar.LENGTH_SHORT).show();
                }
            });
            final String wind = String.format(Locale.ENGLISH , getString(R.string.wind) , json0.getWind().getSpeed() , preferences.getUnits().equals("metric") ? getString(R.string.mps) : getString(R.string.mph));
            windView.setText(wind);
            windIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.wind_speed) , json0.getWind().getSpeed() , preferences.getUnits().equals("metric") ? getString(R.string.mps) : getString(R.string.mph)) , Snackbar.LENGTH_SHORT).show();
                }
            });
            windView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.wind_speed) , json0.getWind().getSpeed() , preferences.getUnits().equals("metric") ? getString(R.string.mps) : getString(R.string.mph)) , Snackbar.LENGTH_SHORT).show();
                }
            });
            humidityIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.humidity) , json0.getMain().getHumidity()) , Snackbar.LENGTH_SHORT).show();
                }
            });
            humidityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.humidity) , json0.getMain().getHumidity()) , Snackbar.LENGTH_SHORT).show();
                }
            });
            sunriseIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.sunrise) , d1) , Snackbar.LENGTH_SHORT).show();
                }
            });
            sunriseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.sunrise) , d1) , Snackbar.LENGTH_SHORT).show();
                }
            });
            sunsetIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.sunset) , d2) , Snackbar.LENGTH_SHORT).show();
                }
            });
            sunsetView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(rootView , String.format(Locale.ENGLISH , getString(R.string.sunset) , d2) , Snackbar.LENGTH_SHORT).show();
                }
            });
            setWeatherIcon( (int) json0.getWeather().get(0).getId(), 10);
            weatherIcon[10].setOnClickListener(new View.OnClickListener()
            {
                public void onClick (View v)
                {
                    try {
                        String rs = json0.getWeather().get(0).getDescription();
                        String[] strArray = rs.split(" ");
                        StringBuilder builder = new StringBuilder();
                        for (String s : strArray) {
                            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                            builder.append(cap.concat(" "));
                        }
                        Snackbar.make(v , String.format(getString(R.string.hey_there_condition) , builder.toString()), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    catch (Exception e) {
                        Log.e("Error", "Main Weather Icon OnClick Details could not be loaded");
                    }
                }
            });
            String r1 = Integer.toString(a) + "°";
            button.setText(r1);
            int deg = json0.getWind().getDirection();
            setDeg(deg);
        }catch(Exception e){
            Log.e(MainActivityFragment.class.getSimpleName() , "One or more fields not found in the JSON data");
        }
    }

    private void setDeg(int deg) {
        int index = Math.abs(Math.round(deg % 360) / 45);
        switch (index) {
            case 0 : directionView.setText(getActivity().getString(R.string.top));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.north) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1 : directionView.setText(getActivity().getString(R.string.top_right));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.north_east) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2 : directionView.setText(getActivity().getString(R.string.right));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.east) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3 : directionView.setText(getActivity().getString(R.string.bottom_right));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.south_east) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 4 : directionView.setText(getActivity().getString(R.string.down));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.south) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 5 : directionView.setText(getActivity().getString(R.string.bottom_left));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.south_west) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 6 : directionView.setText(getActivity().getString(R.string.left));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.west) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case 7 : directionView.setText(getActivity().getString(R.string.top_left));
                directionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view , getString(R.string.north_west) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }



    private void showMenuInputDialog() {//Search etme kısmı
        new MaterialDialog.Builder(getContext())
                .title(getString(R.string.change_city))
                .content(getString(R.string.enter_city))
                .negativeText(getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog , @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, @NonNull CharSequence input) {
                        changeCity(input.toString());
                    }
                }).show();
    }
}
