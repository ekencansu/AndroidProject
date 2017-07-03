package com.example.webexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
//import android.view.Menu;
//import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	
	 RadioButton erkek,kadin; //Degiskenler tanımlandı.
     EditText etxtyas,etxtboy,etxtkilo;
     Button Hesapla;
     TextView sonuc;
     int yas,boy,kilo;
     int yas1,boy1,kilo1;
     double ikilo,k;   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Context context = this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

		  etxtyas=(EditText) findViewById(R.id.editText2);//editText1'in activity_main deki yeri id'si ile etxtyas'a tanımlanıyor.
	        etxtboy=(EditText) findViewById(R.id.editText1);
	        etxtkilo=(EditText) findViewById(R.id.editText3);
	        
	        erkek=(RadioButton) findViewById(R.id.radioButton1);
	        kadin=(RadioButton) findViewById(R.id.radioButton2);
	       
	        Hesapla=(Button) findViewById(R.id.button1);
	        sonuc=(TextView) findViewById(R.id.textViewsonuc); 
	        //activity_xml deki bilesenler java koduna dahil ediliyor.
	       
	             Hesapla.setOnClickListener(new View.OnClickListener() {
	                   
	                    @Override
	                    public void onClick(View v) {
	                         
	                          
	                           if(erkek.isChecked()==true)
	                                  k=0.9;                          
	                           else
	                                  k=0.85;                          
	                           
	                          
	                    	   if(validateInputs(etxtyas.getText().toString(), etxtkilo.getText().toString(),etxtboy.getText().toString())){
	                    		   //validateInputs fonk. ile alanların dolu olup olmadıgı kontrol ediliyor.
	                    		   
	                    		   yas=Integer.parseInt(etxtyas.getText().toString());//Edittext deki degerler degiskenlere atanıyor.
	                    		   boy=Integer.parseInt(etxtboy.getText().toString());
	                    		    kilo=Integer.parseInt(etxtkilo.getText().toString());
	                    		    ikilo=(boy-100 + yas/10)*k;
	                    		    sonuc.setText("                          "+ikilo);//sonucu yazdiriyor.
	                    		    
	                    		    if(ikilo<kilo){
	                    		    	//Eger ideal kilo kendi kilosundan kucuk ise web sayfasına yonlendirme yapılıyor.
	    	       		            	Intent myintent = new Intent(context, WebExample2.class);//yonlendirme yapılacak activity'e gecis belirtiliyor.
	    	       		     		    startActivity(myintent);//Activity aktif hale getiriliyor.
	    	       		            }
	    	                             
                        	   }
	                    	   else{//Eger alanların hepsi dolu degilse "tüm alanları doldurmalısınız" diye mesaj veriyor.
	   	                        	
	   	               				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);// AlertDialog.Builder başlatılıyor.
	   	                
	   	               				// diyalog mesajı ve başlıgı belirtiliyor.
	   	               				builder.setMessage("Tüm alanları doldurmalısınız.")
	   	               				       .setTitle("Alert");
	   	                
	   	               				
	   	               				AlertDialog dialog = builder.create();// diyalog oluşturuluyor. yani yukardaki işlemlerin gerceklesmesi icin dialog yaratılıyor.
	   	               				
	   	               				dialog.show();//diyalog gösteriliyor.
                        	   }
	                           
	                                               
	                    }

					

	             });
		
		
	}
	
	private boolean validateInputs(String yas, String kilo, String boy){//Alanların kontrolu yapılıyor.
		if(!yas.equalsIgnoreCase("")&&!kilo.equalsIgnoreCase("")&&!boy.equalsIgnoreCase("")){//eger bos degilse true donduruyor.
			return true;
		}
		return false;//bos ise false donduruyor.
	}
}

	