package com.example.cansu.tckimlik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.Locale;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String NAMESPACE = "http://tckimlik.nvi.gov.tr/WS";
    private static final String URL = "https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx?WSDL";
    private static final String SOAP_ACTION = "http://tckimlik.nvi.gov.tr/WS/TCKimlikNoDogrula";
    private static final String METHOD_NAME = "TCKimlik";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void ctrlTCKN (View view) {

        final EditText TC = (EditText) findViewById(R.id.tckn);
        String tckn = TC.getText().toString().trim();
        final EditText names = (EditText) findViewById(R.id.ad);
        String name = names.getText().toString().trim().toUpperCase(new Locale("tr_TR"));
        final EditText surnames = (EditText) findViewById(R.id.soyad);
        final String surname = surnames.getText().toString().trim().toUpperCase(new Locale("tr_TR"));
        final EditText birthDates = (EditText) findViewById(R.id.yil);
        final String year = birthDates.getText().toString().trim();
        names.setText(names.getText().toString().toUpperCase());
        surnames.setText(surnames.getText().toString().toUpperCase());
        names.setText(names.getText().toString().toUpperCase());

        Button buton2=(Button)findViewById(R.id.button2);
        Button buton1=(Button)findViewById(R.id.button1);

        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TC.setText("");
                names.setText("");
                surnames.setText("");
                birthDates.setText("");
            }
        });



        if(checkInternetConnection()){
            if(name.isEmpty() || surname.isEmpty() || tckn.isEmpty()){
                Toast.makeText(MainActivity.this,"Lütfen gerekli alanları doldurunuz", Toast.LENGTH_SHORT).show();
            }else {
                new controlTCKNService().execute(tckn, name, surname, year);
            }
        } else {
            Toast.makeText(MainActivity.this,"Lütfen internet bağlantınızı kontrol ediniz", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean result = false;
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private class controlTCKNService extends AsyncTask<String, Void, Void> {

        private String resultText;
        private boolean result;
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        private AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        AlertDialog alert;
        protected void onPreExecute() {
            progressDialog.setMessage("Kontrol ediliyor...");
            progressDialog.show();

        }

        protected Void doInBackground(String... urls) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME); // istek oluşturuluyor.,
//Bu Soap mesajı, aldığı parametreleri ayrıştırır ve ilgili servise gider. Daha sonra bir request adlı nesneye propertyler eklendi.
            Request.addProperty("TCKimlikNo",urls[0]);
            Request.addProperty("Ad", urls[1]);
            Request.addProperty("Soyad", urls[2]);
            Request.addProperty("DogumYili", urls[3]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);//SoapEnvelope nesnesi oluşturdu ve versiyon olarak 1.1 sürümünü kullanıldı
            envelope.dotNet = true;//.Net servisi kullanacağı belirtildi ve bu envelope nesnesine request nesnesini eklendi.
            envelope.setOutputSoapObject(Request);
            AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);// web servis url verildi.nevi protokol tanımladık. Alışveriş buradan sağlanıyor.
            try
            {
                androidHttpTransport.call(SOAP_ACTION, envelope);//web servisi çağırıldı
                SoapObject response = (SoapObject) envelope.bodyIn; //web service den gelecek sonuçları kullanmak için obje yaratıldı
                result = Boolean.parseBoolean( response.getProperty(0).toString());
                if(result) {
                    resultText ="TC Kimlik Numarası Geçerli";
                }else{
                    resultText = "TC Kimlik Numarası Geçersiz";

                }
            }
            catch(ClassCastException e){
                result = false;
                resultText = "TC Kimlik Numarası Geçersiz";
            }
            catch(ConnectException e){
                result = false;
                resultText = "İnternet bağlantınız kesilmiş ya da TCKimlikNo Servisi devre dışı kalmış olabilir.";
            }
            catch (UnknownHostException e) {
                result = false;
                resultText = "İnternet bağlantınız kesilmiş ya da TCKimlikNo Servisi devre dışı kalmış olabilir.";
            }
            catch (UnknownServiceException e) {
                result = false;
                resultText = "İnternet bağlantınız kesilmiş ya da TCKimlikNo Servisi devre dışı kalmış olabilir.";
            }
            catch(Exception e){
                result = false;
                resultText = "İnternet bağlantınız kesilmiş ya da TCKimlikNo Servisi devre dışı kalmış olabilir.";
            }

            return null;
        }

        protected void onPostExecute(Void unused) {

            progressDialog.dismiss();
            alert = builder.setMessage(resultText)
                    .setCancelable(true)
                    .setTitle("Sonuç")
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create();
            alert.show();

        }

    }
}
