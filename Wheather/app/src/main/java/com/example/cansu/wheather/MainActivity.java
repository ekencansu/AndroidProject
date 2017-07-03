package com.example.cansu.wheather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    TextView cities, countries, degrees,coordinates;
    EditText cityNames;
    Button weatherButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities=(TextView)findViewById(R.id.city);
        countries=(TextView)findViewById(R.id.country);
        degrees=(TextView)findViewById(R.id.degree);
        coordinates=(TextView)findViewById(R.id.coordinate);
        weatherButton=(Button)findViewById(R.id.weathButton);
        cityNames= (EditText) findViewById(R.id.cityName);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Weather().execute();
            }
        });
    }
    private class Weather extends AsyncTask<Void,Void,Void>{

        int tempNo;
        String countryName;
        String name;
        Double latitude,longitude;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();//Arka plan işlemi başlamadan önce ön yüzde değiştirilmesi istenen değişkenlerin (ProgressBar gibi animasyonlar) ve
            // AsyncTask içinde gerekli değişkenlerin değer ataması yapılır.
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //Arka planda yapılması istenen işlem burada gerçekleşir. Bu metod içinde yapılan işlemler ön yüzde kullanıcının uygulamayı kullanmasını kesinlikle etkilemez. Eğer buradaki işlemler sonucunda ana akışa bir değişken gönderilmesi gerekiyorsa
            // "return" metodu ile bu değişkenonPostExecute metoduna paslanabilir.

            String weatherUrl="http://api.openweathermap.org/data/2.5/find?q="+ cityNames.getText() +"&units=metric";

            JSONObject jsonObject=null;
            try {
                String json=JSONParser.getJSONFromUrl(weatherUrl);
                try {
                    jsonObject=new JSONObject(json);
                }catch (JSONException e){
                    Log.e("JSONPARSER", "Error creating Json Object" +e.toString());}
//En baştaki json objesinden list adlı array'ı çek
                JSONArray listArray=jsonObject.getJSONArray("list");
//list'in ilk objesini çek
                JSONObject firstObj=listArray.getJSONObject(0);
//Bu alanda Name'i çek
                name = firstObj.getString("name");
//ilk objenin içindeki objelerden main'i çek
                JSONObject main=firstObj.getJSONObject("main");
//Sıcaklık
                tempNo=main.getInt("temp");
//Ulke
                JSONObject country=firstObj.getJSONObject("sys");
                countryName=country.getString("country");
//koordinat
                JSONObject koord=firstObj.getJSONObject("coord");
                latitude=koord.getDouble("lat");
                longitude=koord.getDouble("lon");
            }catch (JSONException e){
                Log.e("json","doINbackgrond");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void args) {
            //doInBackground metodu tamamlandıktan sonra işlemlerin sonucu bu metoda result değişkeni ile gönderilir. Buradaki işlemler ana akışı etkiler ve herhangi bir hataya sebep olmaz.
            // Arka plandaki işlemden gelen bir veri ön yüzde gösterilmek isteniyorsa bu metod içinde gösterim işlemi yapılabilir.


            cities.setText("City: "+name);
            countries.setText("Country: "+countryName);
            degrees.setText("Degree: "+tempNo + "\u2103");
            coordinates.setText("Coordinate:\n Latitude: "+latitude+"\n Longitude: "+ longitude);
        }
    }
}