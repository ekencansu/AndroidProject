package com.example.hesapmakinesi;

//import com.example.dosyakaydetme.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HesapActivity extends ActionBarActivity implements OnClickListener {
	Button buton0,buton1,buton2,buton3,buton4,buton5,buton6,buton7,buton8,buton9;

	Button topla;
	Button cikar;
	Button carp;
	Button bol;
	Button sifirla;
	Button sil;
	Button esittir;
	EditText numara;

	double num1=0, sonuc=0;
	String islem;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hesap);
		
		buton0=(Button)findViewById(R.id.btn0);
		buton1=(Button)findViewById(R.id.btn1);
		buton2=(Button)findViewById(R.id.btn2);
		buton3=(Button)findViewById(R.id.btn3);
		buton4=(Button)findViewById(R.id.btn4);
		buton5=(Button)findViewById(R.id.btn5);
		buton6=(Button)findViewById(R.id.btn6);
		buton7=(Button)findViewById(R.id.btn7);
		buton8=(Button)findViewById(R.id.btn8);
		buton9=(Button)findViewById(R.id.btn9);
		
		topla=(Button)findViewById(R.id.topla);
		cikar=(Button)findViewById(R.id.cikar);
		carp=(Button)findViewById(R.id.carp);
		bol=(Button)findViewById(R.id.bol);
		sifirla=(Button)findViewById(R.id.sifirla);
		sil=(Button)findViewById(R.id.sil);
		esittir=(Button)findViewById(R.id.esit);
		numara=(EditText)findViewById(R.id.EditText1);
		
		
		buton0.setOnClickListener(this); 
		buton1.setOnClickListener(this); 
		buton2.setOnClickListener(this); 
		buton3.setOnClickListener(this); 
		buton4.setOnClickListener(this); 
		buton5.setOnClickListener(this); 
		buton6.setOnClickListener(this);
		buton7.setOnClickListener(this); 
		buton8.setOnClickListener(this);
		buton9.setOnClickListener(this); 
		topla.setOnClickListener(this); 
		cikar.setOnClickListener(this);
		bol.setOnClickListener(this); 
		carp.setOnClickListener(this);
		esittir.setOnClickListener(this); 
		sifirla.setOnClickListener(this); 
		sil.setOnClickListener(this); 
		
	}
	
	@Override
	public void onClick(View v) {
		int num = numara.getText().length();
		if(num<9)
		{
			switch(v.getId()){
			case R.id.btn0:
				numara.setText(numara.getText().toString()+"0");
				break;
			case R.id.btn1:
				numara.setText(numara.getText().toString()+"1");
				break;
			case R.id.btn2:
				numara.setText(numara.getText().toString()+"2");
				break;
			case R.id.btn3:
				numara.setText(numara.getText().toString()+"3");
				break;
			case R.id.btn4:
				numara.setText(numara.getText().toString()+"4");
				break;
			case R.id.btn5:
				numara.setText(numara.getText().toString()+"5");
				break;
			case R.id.btn6:
				numara.setText(numara.getText().toString()+"6");
				break;
			case R.id.btn7:
				numara.setText(numara.getText().toString()+"7");
				break;
			case R.id.btn8:
				numara.setText(numara.getText().toString()+"8");
				break;
			case R.id.btn9:
				numara.setText(numara.getText().toString()+"9");
				break;
				
			case R.id.topla:
				if(!numara.getText().toString().equals("")){
					//yukarıda ekran boş ise boş kalması sağlandı.
					 double num1 = Double.parseDouble(numara.getText().toString());
					//numara içerisindeki değeri getText ile aldırıp double'a					 
					//çevirip num1 değişkenine aktarıyor.
					 numara.setText("");
					 String islem = "+";
				}
				else{
					Toast.makeText(this, "Sayi giriniz: ", Toast.LENGTH_LONG).show();
				}
				break;
				
			case R.id.cikar:
				if(!numara.getText().toString().equals("")){
					//yukarıda ekran boş ise boş kalması sağlandı.
					 num1 = Double.parseDouble(numara.getText().toString());
					//numara içerisindeki değeri getText ile aldırıp double'a					 
					//çevirip num1 değişkenine aktarıyor.
					 numara.setText("");
					 String islem = "-";
				}
				else{
					Toast.makeText(this, "Sayi giriniz: ", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.carp:
				if(!numara.getText().toString().equals("")){
					//yukarıda ekran boş ise boş kalması sağlandı.
					 double num1 = Double.parseDouble(numara.getText().toString());
					//numara içerisindeki değeri getText ile aldırıp double'a					 
					//çevirip num1 değişkenine aktarıyor.
					 numara.setText("");
					 String islem = "*";
				}
				else{
					Toast.makeText(this, "Sayi giriniz: ", Toast.LENGTH_LONG).show();
				}
				break;
				
			case R.id.bol:
				if(!numara.getText().toString().equals("")){
					//yukarıda ekran boş ise boş kalması sağlandı.
					 double num1 = Double.parseDouble(numara.getText().toString());
					//numara içerisindeki değeri getText ile aldırıp double'a					 
					//çevirip num1 değişkenine aktarıyor.
					 numara.setText("");
					 String islem = "/";
				}
				else{
					Toast.makeText(this, "Sayi giriniz: ", Toast.LENGTH_LONG).show();
				}
				break;
				
			case R.id.esit:
				if(!numara.getText().toString().equals("") && !String.valueOf(num1).equals("0")){
					if(islem.equals("+")){
						
						sonuc=num1 + Double.parseDouble(numara.getText().toString());
					}
					
					else if(islem.equals("-")){
						
						sonuc=num1 - Double.parseDouble(numara.getText().toString());
						
					}
					
					else if(islem.equals("*")){
						
						sonuc=num1 * Double.parseDouble(numara.getText().toString());
						
					}
					
					else if(islem.equals("/")){
						
						sonuc=num1 / Double.parseDouble(numara.getText().toString());
						
					}
					
					/*numara.setText(String.valueOf(sonuc));
					num1=0;
					sonuc=0;*/
				}		
				
				else{
					Toast.makeText(this, "Sayi giriniz: ", Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
				if(R.id.sifirla == v.getId()){
				num1=0;
				sonuc=0;
				numara.setText("");
				}
				
				else if(R.id.sil == v.getId()){
					
				 String text = numara.getText().toString();
				 
                 text=text.substring(0,text.length()-1);

                  numara.setText("");  
				}



                }
				
}
		
		
		
		
		 