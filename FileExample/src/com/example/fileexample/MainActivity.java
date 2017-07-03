package com.example.fileexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
 

 
public class MainActivity extends Activity {
    EditText disim,dicerik,disimoku;
    Button yaz,oku;
    TextView dosya;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disim = (EditText)findViewById(R.id.fname);
        dicerik = (EditText)findViewById(R.id.ftext);
        disimoku = (EditText)findViewById(R.id.fnameread);
        yaz = (Button)findViewById(R.id.btnwrite);
        oku = (Button)findViewById(R.id.btnread);
        dosya = (TextView)findViewById(R.id.dosya);
        yaz.setOnClickListener(new View.OnClickListener() {
 
        @Override
        public void onClick(View arg0) {
           
        String filename = disim.getText().toString();//Edittext deki bilgiler değişkenlere atanıyor.
        String filecontent = dicerik.getText().toString();
        
        FileReadWrite dis = new FileReadWrite();//dis nesnesi olusturluyor.
        dis.Write(filename, filecontent);//dis nesnesi ile FileReadWrite sınıfındaki Write fonk. cagırılıyor.
        
        if(dis.Write(filename, filecontent)){//Eger dosya olusmussa olustu diye mesaj gosterliyor.
        Toast.makeText(getApplicationContext(), filename+".txt olusturuldu", Toast.LENGTH_SHORT).show();
        }
        
        else{
            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
 
        }
        }
    });
    oku.setOnClickListener(new View.OnClickListener() {
 
        @Override
        public void onClick(View arg0) {
            
            String dosyaoku = disimoku.getText().toString();//Edittext deki bilgiler dosyaoku değişkennine atanıyor.
            
            FileReadWrite dis = new FileReadWrite();//dis nesnesi olusturuluyor.
         
            String text = dis.Read(dosyaoku);//dis nesnesi ile FileReadWrite sınıfındaki Read fonk. cagırılıyor.
            
            if(text != null){//Eger text bos degilse yani dosyadan veri okunmussa veri dosya textView'e yazdırılıyor.
            dosya.setText(text);
            }
            else {
                Toast.makeText(getApplicationContext(), "Dosya Bulunamadi", Toast.LENGTH_SHORT).show();
                dosya.setText(null);
            }
 
        }
    });
    }
    
    
    }