package com.example.cansu.booksapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddBook extends Activity {


    Button b1;
    EditText edit1,edit2,edit3,edit4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("BookS List");
        b1 = (Button)findViewById(R.id.button1);
        edit1 = (EditText)findViewById(R.id.editText1);
        edit2 = (EditText)findViewById(R.id.editText2);
        edit3 = (EditText)findViewById(R.id.editText3);
        edit4 = (EditText)findViewById(R.id.editText4);

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String adi,yazari,yili,fiyati;
                adi = edit1.getText().toString();
                yazari = edit2.getText().toString();
                yili = edit3.getText().toString();
                fiyati = edit4.getText().toString();
                if(adi.matches("") || yazari.matches("") || yili.matches("") || fiyati.matches("")  ){
                    Toast.makeText(getApplicationContext(), "Fill out all the information completely", Toast.LENGTH_LONG).show();
                }else{
                    Database db = new Database(getApplicationContext());
                    db.kitapEkle(adi, yazari, yili, fiyati);//kitap ekledik
                    db.close();
                    Toast.makeText(getApplicationContext(), "Your book has been successfully added.", Toast.LENGTH_LONG).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                    edit4.setText("");
                }


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }


}
