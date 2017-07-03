package com.example.spinnerexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
 

public class SpinnerActivity extends ActionBarActivity {
	
	String[]Marka = {"Seciniz","Samsung","Iphone"};//String tipinde dizi tanımlandı. 
	//Spinner da listenilecek veriler diziye girildi.
    Spinner spinner;//spinner tipinde degisken tanımlandı.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
		
        spinner = (Spinner)findViewById(R.id.spinner1);//Spinner tanımı yapıldı.
 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Marka);
        //yeni bir ArrayAdapter oluşturarak veri çekildi. yani string dizisindeki veriler spinner'a ArrayAdapter sayesinde aktarıldı.
        //this ile hangi activity icerisinde calısacagı belirtildi.
        //android.R.layout.simple_spinner_item ile spinner'ın goruntusu belirlendi.
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner’ın dropdown halindeki görüntüsünü belirlendi.
 
        spinner.setAdapter(adapter);// Oluşturulan ArrayAdapter'i Spinner'a yüklendi. yani veriler spinner'a aktırılmış oldu.
        
        
        
     // Herhangi bir iteme dokunulduğunda yapılacak işlemler yazılmaya başlandı.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	//OnItemSelectedListener() metodu ile spinner'ın selected ozelligi aktif hale geldi.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            	//Secilen ogeyi tekrar geri alarak kullanılmasını saglar.
            	
            	if(parent.getSelectedItem().toString().equals(Marka[0])){
                	
                }
             
                if(parent.getSelectedItem().toString().equals(Marka[1])){
                	Birinci();//Marka[1] secilmesi durumunda Birinci fonksiyon cagırıldı.
                }
                
                if(parent.getSelectedItem().toString().equals(Marka[2])){
                
                	Ikinci();//Marka[2] secilmesi durumunda Ikinci fonksiyon cagırıldı.
                }
                           	
            	
            }

			@Override
            public void onNothingSelected(AdapterView<?> parent) {
 
            }
        });
	}

	protected void Ikinci() {
	    Uri link = Uri.parse("http://www.apple.com/tr/iphone/");
	    //siteye yonlendirme işlemi yapılır.
	    
	    Intent tarayici = new Intent(Intent.ACTION_DEFAULT, link);
	    // İntent ile yönlendirme yapılıyor.
	    
	    startActivity(tarayici);//activity aktif hale getiriliyor.
		
		
	}

	protected void Birinci() {
		
		    Uri link = Uri.parse("http://www.samsung.com/tr/home/");
		    
		    Intent tarayici = new Intent(Intent.ACTION_DEFAULT, link);
		    
		    startActivity(tarayici);
			
		}
		
	
		
	}

	