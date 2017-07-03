package com.example.cansu.booksapp;

import java.util.HashMap;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookEdit extends Activity{


    Button buton1;
    EditText edit1,edit2,edit3,edit4;
    int id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedit);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Book Detail");

        buton1 = (Button)findViewById(R.id.button1);
        edit1 = (EditText)findViewById(R.id.editText1);
        edit2 = (EditText)findViewById(R.id.editText2);
        edit3 = (EditText)findViewById(R.id.editText3);
        edit4 = (EditText)findViewById(R.id.editText4);

        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);

        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.kitapDetay(id);

        edit1.setText(map.get("book name"));
        edit2.setText(map.get("autor").toString());
        edit3.setText(map.get("year").toString());
        edit4.setText(map.get("price").toString());

        buton1.setOnClickListener(new View.OnClickListener() {

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
                    db.kitapDuzenle(adi, yazari, yili, fiyati,id);//gönderdiğimiz id li kitabın değperlerini güncelledik.
                    db.close();
                    Toast.makeText(getApplicationContext(), "Successfully Arranged Your Book.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
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
