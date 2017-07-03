package com.example.cansu.rehberkisiler;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import java.sql.SQLException;

public class TumKayit extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kisiler);
        TextView tv=(TextView)findViewById(R.id.tvTumKayit);
        Veritabanı db=new Veritabanı(TumKayit.this);
        try {
            db.baglantiyiAc();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String tumKayitlar=db.tumKayitlar();
        db.baglantiyiKapat();
        tv.setText(tumKayitlar);
    }
}


