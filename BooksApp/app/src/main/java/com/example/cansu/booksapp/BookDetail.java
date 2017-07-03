package com.example.cansu.booksapp;


import java.util.HashMap;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BookDetail extends Activity {

    Button buton1,buton2;
    TextView text1,text2,text3,text4;
    int id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Kitap Listesi");

        buton1 = (Button)findViewById(R.id.button1);
        buton2 = (Button)findViewById(R.id.button2);

        text1 = (TextView)findViewById(R.id.name);
        text2 = (TextView)findViewById(R.id.autor);
        text3 = (TextView)findViewById(R.id.year);
        text4 = (TextView)findViewById(R.id.price);

        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);//id değerini integer olarak aldık. Burdaki 0 eğer değer alınmazsa default olrak verilecek değer

        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.kitapDetay(id);//Bu id li row un değerini hashmap e aldık

        text1.setText(map.get("kitap_adi"));
        text2.setText(map.get("yazar").toString());
        text3.setText(map.get("yil").toString());
        text4.setText(map.get("fiyat").toString());


        buton1.setOnClickListener(new View.OnClickListener() {//Kitap düzenle butonuna tıklandıgında tekrardan kitabın id sini gönderdik

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookEdit.class);
                intent.putExtra("id", (int)id);
                startActivity(intent);
            }
        });

        buton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookDetail.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Kitap Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Database db = new Database(getApplicationContext());
                        db.kitapSil(id);
                        Toast.makeText(getApplicationContext(), "Kitap Başarıyla Silindi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);//bu id li kitabı sildik ve Anasayfaya döndük
                        finish();

                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
                });
                alertDialog.show();

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
