package com.example.cansu.androidwebservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cansu.androidwebservice.app.AndroidWebServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    Spinner sp;
    int secilen;
    TextView t1, t2, t3, t4;
    ProgressDialog pDialog;
    String veri_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_main.xml de olusturdugumuz textview leri koda tanitiyoruz.
        t1 = (TextView) findViewById(R.id.textView2);
        t2 = (TextView) findViewById(R.id.textView4);
        t3 = (TextView) findViewById(R.id.textView6);
        t4 = (TextView) findViewById(R.id.textView8);

        // activity_main.xml de olusturdugumuz spineri koda tanitiyoruz.
        sp = (Spinner) findViewById(R.id.spinner1);

        // Spinera Listener ekliyoruz
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                secilen = arg2; // spinerdan secilen degerin sonra degerini
                // aliyoruz. kacinci sirada olduqunu
                if (secilen != 0) { // secilen deger ilk deger degilse yani Kisi
                    // seciniz yaz?s? degilse
                    pDialog = new ProgressDialog(MainActivity.this);
                    pDialog.setMessage("Kisi Bilgileri Getiriliyor...");
                    pDialog.setIndeterminate(true);
                    pDialog.setCancelable(false); // ProgressDialog u iptal
                    // edilemez
                    // hale getirdik.
                    pDialog.show();
                    getData();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void getData() {
//TODO adding request to POST method and URL
        //Burada istegimizi olusturuyoruz, method parametresi olarak post seciyoruz ve url'imizi const'dan aliyoruz.

        StringRequest myReq = new StringRequest(Method.POST, Const.url,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // // TODO datalarimiz geldi parse islemi yapilmali
                        // // TODO lets parse it


                        JSONObject veri_json;
                        try {
                            veri_json = new JSONObject(response);// gelen
                            // veri_string
                            // degerini
                            // json
                            // arraye
                            // ceviriyoruz.
                            // try icinde yapmak zorunlu cunku cikabilecek bir
                            // sorunda uygulamanin patlamamasi icin

                            String isim, yas, mail, adres;
                            try {
                                // try icinde yapmak zorunlu cunku cikabilecek
                                // bir sorunda uygulaman?n patlamamasi icin.
                                // "veri_json" arrayindeki degerleri aliyoruz.
                                isim = veri_json.getString("isim");
                                yas = veri_json.getString("yas");
                                mail = veri_json.getString("mail");
                                adres = veri_json.getString("adres");

                                // bu aldigimiz degerleri textView lere
                                // yazdiriyoruz.
                                t1.setText(isim);
                                t2.setText(yas);
                                t3.setText(mail);
                                t4.setText(adres);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        pDialog.dismiss(); // ProgresDialog u kapatiyoruz.
                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO there is an error to connecting server or getting data
                // Server'a baglanirken yada veri cekilirken hata oldu
                System.out.println(error.getMessage());
            }
        }) {
            // TODO let put params to volley request
            // Burada gonderecegimiz request parametrelerini(birden fazla olabilir) set'liyoruz

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //TODO secilen kisinin id'sini kisi parametresine ekliyoruz
                params.put("kisi", secilen + "");
                return params;
            };
        };
        // Request(istek)'i Volley'in Requst sirasina atiyoruz
        // Adding request to volley request queue
        AndroidWebServer.getInstance().addToRequestQueue(myReq);

    }

}
