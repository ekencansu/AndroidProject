package com.example.activityconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.view.MenuItem;

public class FirstActivity extends ActionBarActivity implements OnClickListener {
	Button gonder;
	TextView ad;
	TextView numara;
	EditText editAd;
	EditText editNu;
	Integer num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		//Java dosyamız çalıştırıldığında activity_first layoutu çağırıldı.
		
		gonder=(Button)findViewById(R.id.button1);
		gonder.setOnClickListener(this);
		ad=(TextView)findViewById(R.id.textView1);
		numara=(TextView)findViewById(R.id.textView2);
		editAd=(EditText)findViewById(R.id.editText1);
		editNu=(EditText)findViewById(R.id.editText2);
		
		//String isim=(editAd.getText().toString());
		//String num=(editNu.getText().toString());
		
	}
	
	
	public void onClick(View v){
		//Bundle extras=new Bundle();
		
		Intent myintent=new Intent(FirstActivity.this,SecondActivity.class);
		//intent nesnesi olusturuluyor.Icine bulundugu activity ve geçiş yapacaği activity yazılıyor.
		
		myintent.putExtra("Name",editAd.getText().toString());
		//intenti baslatmadan once intent nesnesinin "putExtra" metodunu kullanarak veri gonderiyoruz.
		//"putExtra" metodu (key,value) şeklinde iki parametre alır.
		//value degeri integer,string olacilecegi gibi custom class da olabilir.
		//"getText" ile "editAd" deki veriyi alarak "Name" anahtari ile veriyi gonderiyoruz.
		
		myintent.putExtra("No",editNu.getText().toString());
		//intent nesnesinin "putExtra" metodunu kullanarak "editNu" daki veriyi "No" anahtari ile gonderiyoruz.
		
		//myintent.setClass(getApplicationContext(), SecondActivity.class);
		//"Intent myintent=new Intent(FirstActivity.this,SecondActivity.class);" ile ayni isi yapiyor.
		
		startActivity(myintent);//myintenti baslatarak ikinci activity cagırıyoruz(baslatıyoruz).
		
	}
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
