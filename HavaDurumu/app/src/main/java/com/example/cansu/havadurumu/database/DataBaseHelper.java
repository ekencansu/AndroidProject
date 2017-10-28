package com.example.cansu.havadurumu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cansu.havadurumu.model.WeatherFort;
import com.example.cansu.havadurumu.model.WeatherInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.cansu.havadurumu.model.WeatherFort.City;
import static com.example.cansu.havadurumu.model.WeatherInfo.Weather;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Weather.db";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";




    public TableCity City;
    public TableWeather Weather;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        City = new TableCity();
        Weather = new TableWeather();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table oluşturma
        db.execSQL(DataBaseContract.Query.CREATE_TABLECITY);
        db.execSQL(DataBaseContract.Query.CREATE_TABLEWEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // veritabanı silme ve db oluşturma
        db.execSQL(DataBaseContract.Query.DROP_TABLECITY);
        db.execSQL(DataBaseContract.Query.DROP_TABLEWEATHER);

        onCreate(db);
    }

    public class TableCity{
        private TableCity(){}

      public  WeatherInfo.Weather weather= new Weather();

        //public  WeatherFort.Weather weather;


        public boolean insert(){
            WeatherInfo.Weather weather= new Weather();
            WeatherFort.City weather1= new City();

         if(!isCityExsist(weather.getId())){
                SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
                // veri doldurma
                ContentValues values = new ContentValues();
                values.put(DataBaseContract.TableCity.COLUMN_NAME_NAME, weather1.getName());
                values.put(DataBaseContract.TableCity.COLUMN_NAME_ID,weather.getId());
                values.put(DataBaseContract.TableCity.COLUMN_NAME_COUNTRY, weather1.getCountry());
                // Satır ekleme
                db.insert(DataBaseContract.TableCity.TABLE_NAME, null, values);
                return true;
            }
            return false;
        }

        public void updateWLU(long cityID){
            //saatlik güncelleme
            if(isCityExsist(cityID)){
                SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
                // veri doldurma
                ContentValues values = new ContentValues();
                SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                Calendar calendar = Calendar.getInstance();
                values.put(DataBaseContract.TableCity.COLUMN_NAME_WUPDATE, iso.format(calendar.getTime()));
                // satır güncelleme
                db.update(DataBaseContract.TableCity.TABLE_NAME, values, DataBaseContract.Query.UPDATE_WLU + cityID, null);
            }
        }

        public void updateFLU(long cityID){
            //günlük güncelleme
            if(isCityExsist(cityID)){
                SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
                // veri doldurma
                ContentValues values = new ContentValues();
                SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                Calendar calendar = Calendar.getInstance();
                values.put(DataBaseContract.TableCity.COLUMN_NAME_FUPDATE, iso.format(calendar.getTime()));
                // satır güncelleme
                db.update(DataBaseContract.TableCity.TABLE_NAME, values, DataBaseContract.Query.UPDATE_FLU+cityID, null);
                Log.d("SQLTEST[C]", "DataBase WLU set");
            }else{
                Log.d("SQLTEST[C]", "No city with id = "+cityID);
            }
        }

        public List<City> selectAll(){
            List<City> cities = new ArrayList<City>();
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();
            Cursor c = db.rawQuery(DataBaseContract.Query.GET_ALL_CITIES, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        WeatherInfo.Weather city= new Weather();
                        WeatherFort.City city1= new City();

                        city.setId(c.getInt(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_ID)));
                        city1.setName(c.getString(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_NAME)));
                        city1.setCountry(c.getString(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_COUNTRY)));
                        cities.add(city1);
                    } while (c.moveToNext());
                }
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
            }

            return cities;
        }

        public City select(long code){
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();

            Cursor c = db.rawQuery(DataBaseContract.Query.GET_CITY_BY_ID+code, null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    WeatherInfo.Weather city= new Weather();
                    WeatherFort.City city1= new City();

                    city.setId(c.getInt(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_ID)));
                    city1.setName(c.getString(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_NAME)));
                    city1.setCountry(c.getString(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_COUNTRY)));
                    if(c!=null)
                        if(!c.isClosed())
                            c.close();
                    return city1;
                }
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
            }
            return null;
        }

        public void delete(long code){
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            db.delete(DataBaseContract.TableCity.TABLE_NAME, DataBaseContract.Query.DELETE_CITY_WHERE_ID + code, null);
            db.delete(DataBaseContract.TableWeather.TABLE_NAME, DataBaseContract.Query.DELETE_WEATHER_WHERE_CITYID + code, null);
        }

        private boolean isCityExsist(long code){
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();
            Cursor c = db.rawQuery(DataBaseContract.Query.GET_CITY_BY_ID + code, null);
            if (c.getCount() > 0) {
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
                return true;
            }
            if(c!=null)
                if(!c.isClosed())
                    c.close();
            return false;
        }

        public Calendar getWeatherLastUpdate(long id){
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();

            Cursor c = db.rawQuery(DataBaseContract.Query.GET_WUPDATE+id, null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    String strDate = c.getString(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_WUPDATE));
                    if(strDate == null){
                        if(c!=null)
                            if(!c.isClosed())
                                c.close();
                        return null;
                    }
                    SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                    try {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(iso.parse(strDate));
                        if(c!=null)
                            if(!c.isClosed())
                                c.close();
                        return calendar;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(c!=null)
                        if(!c.isClosed())
                            c.close();
                    return null;
                }
            }
            if(c!=null)
                if(!c.isClosed())
                    c.close();
            return null;
        }

        public Calendar getForecastLastUpdate(long id){
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();
            Cursor c = db.rawQuery(DataBaseContract.Query.GET_FUPDATE+id, null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    String strDate = c.getString(c.getColumnIndex(DataBaseContract.TableCity.COLUMN_NAME_FUPDATE));
                    if(strDate == null){
                        if(c!=null)
                            if(!c.isClosed())
                                c.close();
                        return null;
                    }
                    SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                    try {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(iso.parse(strDate));
                        if(c!=null)
                            if(!c.isClosed())
                                c.close();
                        return calendar;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(c!=null)
                        if(!c.isClosed())
                            c.close();
                    return null;
                }
            }
            if(c!=null)
                if(!c.isClosed())
                    c.close();
            return null;
        }

    }

    public class TableWeather {
        private TableWeather() {}

        public long insert(WeatherFort.Temp weather, long cityID) {
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            // veri doldurma

            WeatherInfo weather2 = new WeatherInfo();
            WeatherFort.Temp temp = new WeatherFort.Temp();

            ContentValues values = new ContentValues();
            values.put(DataBaseContract.TableWeather.COLUMN_NAME_DAY, weather.getDay());
            values.put(DataBaseContract.TableWeather.COLUMN_NAME_MIN, weather.getMin());
            values.put(DataBaseContract.TableWeather.COLUMN_NAME_MAX, weather.getMax());
            SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
            values.put(DataBaseContract.TableWeather.COLUMN_NAME_DATE, iso.format(weather2.getDt()));
            values.put(DataBaseContract.TableWeather.COLUMN_NAME_CITYID, cityID);
            // insert row
            long id = db.insert(DataBaseContract.TableWeather.TABLE_NAME, null, values);
            return id;
        }

        public List<WeatherFort.Temp> selectAll( long cityID){
            List<WeatherFort.Temp> forecast = new ArrayList<>();
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();
            // veri doldurma
            Cursor c = db.rawQuery(DataBaseContract.Query.GET_FORECAST + cityID, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        WeatherFort.Temp temp = new WeatherFort.Temp();
                        WeatherInfo temp1 = new WeatherInfo();

                        temp.setDay(c.getDouble(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_DAY)));
                        temp.setMin(c.getDouble(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_MIN)));
                        temp.setMax(c.getDouble(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_MAX)));
                        String strDate = c.getString(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_DATE));
                        SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(iso.parse(strDate));
                            temp1.setDt(calendar);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        forecast.add(temp);
                    } while (c.moveToNext());
                }
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
            }
            if(c!=null)
                if(!c.isClosed())
                    c.close();
            return forecast;
        }

        public WeatherFort.Temp select( long id) {
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();
            // veri dolsurma
            Cursor c = db.rawQuery(DataBaseContract.Query.GET_WEATHER_BY_ID + id, null);
            if (c != null)
            {
                c.moveToFirst();
                if (c.getCount() > 0) {

                    WeatherFort.Temp temp = new WeatherFort.Temp();
                    WeatherInfo temp1 = new WeatherInfo();

                    temp.setDay(c.getDouble(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_DAY)));
                    temp.setMin(c.getDouble(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_MIN)));
                    temp.setMax(c.getDouble(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_MAX)));;
                    String strDate = c.getString(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_DATE));
                    SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                    try {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(iso.parse(strDate));
                        temp1.setDt(calendar);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(c!=null)
                        if(!c.isClosed())
                            c.close();
                    return temp;
                }
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
            }
            return null;
        }

        public void updateByCurrent(WeatherFort current, long id){
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            // satır güncelleme
            db.update(DataBaseContract.TableWeather.TABLE_NAME, values, DataBaseContract.Query.UPDATE_CURRENT_WEATHER + id, null);
        }

        public long getTodayWeatherID(long cityID){
            SQLiteDatabase db = DataBaseHelper.this.getReadableDatabase();
            Calendar today = Calendar.getInstance();
            long id = -1;

            Cursor c = db.rawQuery(DataBaseContract.Query.GET_FORECAST+cityID, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id = c.getLong(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_ID));
                        String strDate = c.getString(c.getColumnIndex(DataBaseContract.TableWeather.COLUMN_NAME_DATE));
                        SimpleDateFormat iso = new SimpleDateFormat(DATE_FORMAT);
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(iso.parse(strDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } while (c.moveToNext());
                }
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
            }
            return id;
        }

        public void delete(long id){
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            db.delete(DataBaseContract.TableWeather.TABLE_NAME, DataBaseContract.Query.DELETE_WEATHER_WHERE_ID + id, null);
        }

        public void deleteByCity(long cityID){
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            db.delete(DataBaseContract.TableWeather.TABLE_NAME, DataBaseContract.Query.DELETE_WEATHER_WHERE_CITYID + cityID, null);
        }

        public void deleteWhole(){
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            db.delete(DataBaseContract.TableWeather.TABLE_NAME, null, null);
        }

        public void deleteOldWeather(){
            SQLiteDatabase db = DataBaseHelper.this.getWritableDatabase();
            Calendar today = Calendar.getInstance();
            SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
            db.delete(DataBaseContract.TableWeather.TABLE_NAME, "strftime('%Y-%m-%d', date) < strftime('%Y-%m-%d', '"+iso.format(today.getTime())+"')", null);
        }

    }



}
