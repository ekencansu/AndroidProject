package com.example.notdefteri;
//Bu kısımda not ekleme islemleri gerceklesir. Edittext den not baslıgı ve konu ıcerıgı alınarak veriler kaydedilir.
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotEkleActivity extends Activity {
	EditText konuET, icerikET;
	Button ekleBT;
	NotDefteriDatabase dba;

	@Override
	public void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.ekleguncelle);

	konuET = (EditText) findViewById(R.id.konuText);
	icerikET = (EditText) findViewById(R.id.icerikText);
	ekleBT = (Button) findViewById(R.id.opButton);
	
	//ekleguncelle.xml dosyası java sınıfına baglama islemi yapılıyor.

	ekleBT.setOnClickListener(new OnClickListener() {//ekle butonuna tıklandıgında yapılacak islemler gosteriliyor.
	public void onClick(View v) {
	try {
	dba = new NotDefteriDatabase(NotEkleActivity.this);
	dba.ac();

	/*
	* Not ekleme alanları boş ise uyarı ver değilse notu ekle
	*/

	if(konuET.getText().length()!=0 && icerikET.getText().length()!=0){
	notEkle();
	}
	else{
	int duration = Toast.LENGTH_SHORT;

	Toast toast = Toast.makeText(getApplicationContext(),
	"Konu ve İçerik Giriniz !",
	duration);
	toast.show();
	}

	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	});

	}

	/*
	* EditTextlerden aldığı verileri notEkle methoduna gönderen method
	*/

	public void notEkle() {

	dba.notEkle(konuET.getText().toString(), icerikET.getText()
	.toString());
	dba.kapat();
	konuET.setText("");
	icerikET.setText("");

	Intent i = new Intent(NotEkleActivity.this, NotlariListeleActivity.class);

	startActivity(i);
	finish();
	}

}
