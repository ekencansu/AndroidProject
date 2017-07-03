package com.example.cansu.card;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.card.payment.CardIOActivity;//io.card nın kendi içerisinde bulunan activity sınıflarıdır
import io.card.payment.CreditCard;

import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_LANGUAGE_OR_LOCALE = "io.card.payment.languageOrLocale";

    private Button scanBttn;
    private TextView scanInfo;
    private int MY_SCAN_REQUEST_CODE = 999;
    @Override
    protected void onResume() {
        super.onResume();

        if (CardIOActivity.canReadCardWithCamera()) {//Kameradan okutma yapılıyorsa
            scanBttn.setText("Kredi Kartınızı Okutun");
        } else {
            scanBttn.setText("Kredi Bilgilerinizi Giriniz");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        scanBttn = (Button)findViewById(R.id.btnScan);
        scanInfo = (TextView)findViewById(R.id.textScan);
        scanBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanCreditCard();

            }
        });
    }

    private void scanCreditCard() {

        Intent scanIntent = new Intent(this, CardIOActivity.class);

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false); // default: false

        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);// CardIOActivity sayfası "startActivityForResult" ile başlar.

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Görüntü işleme işleminden sonra çalışır.
        super.onActivityResult(requestCode, resultCode, data);


        String resultStr;
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

            resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";//bu kod ile kartı okuttuktan sonra ekrana kredi kartı numarasını maskeli bir şekilde yazmayı sağlar.

            if (scanResult.isExpiryValid()) {
                resultStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
            }

            if (scanResult.cvv != null) {
                resultStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
            }

            if (scanResult.postalCode != null) {
                resultStr += "Postal Code: " + scanResult.postalCode + "\n";
            }

            if (scanResult.cardholderName != null) {
                resultStr += "Cardholder Name : " + scanResult.cardholderName + "\n";
            }
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("UYARI");
            builder.setMessage("Tarama İptal Edildi!!!");



            builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });


            builder.show();


            resultStr = "Tarama İptal Edildi.";
        }
        scanInfo.setText(resultStr);
    }


}
