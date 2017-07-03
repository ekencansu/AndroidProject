package com.example.activityconnection;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.view.MenuItem;


public class SecondActivity extends ActionBarActivity implements OnClickListener {
	
	Button geridon;
	TextView alinan;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		geridon=(Button)findViewById(R.id.button2);
		alinan=(TextView)findViewById(R.id.textView3);
		geridon.setOnClickListener(this);
		
		Bundle paketim = new Bundle();//Bundle nesnesi olusturuldu.
		//Gelen veriyi buraya Bundle icine cekip bu Bundle üzerinden calisir.
		
		paketim=getIntent().getExtras();//Gonderilen anahtar degerleri yani gelen verileri aliyor.
		
		String isim=paketim.getString("Name");//Bundle icerisindeki veriyi alarak baska degiskene atiyor.
		String num=paketim.getString("No");
		
		alinan.setText("Kullanici adi:  " + isim +"\n\n\nNumara:  " +num);
		//gelen verileri setText ile "alinan" textView'e yazdiriyor.
		//Ekranda Yazdiriyor
		
	}

	@Override
	public void onClick(View v) {
		 Toast.makeText(this, "Ana Ekrana Geri Donuluyor!!!!", Toast.LENGTH_SHORT).show();//Toast kullanrak kisa bir mesaj gosteriliyor.
		finish();//ikinci aktivity sonlandirilarak ilk sayfaya geçiş yapiliyor.
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
