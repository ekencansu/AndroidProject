package com.example.dosyakaydetme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	Button kaydet;//Degisken tanımları(Layouttan cagiracagimiz ogeler tanimlandi)
	Button oku;
	EditText text1;
	EditText text2;
    Context context= this;//Context nesnesi tanimi. AlertDialog'un hangi sinif tarafindan kullanilacagini
    //belirtilir.Bu nedenle "this" degeri atanır.
    
	SharedPreferences pref;//preference için nesne tanımı.
	SharedPreferences.Editor editor; //preferences'e değer eklemek çıkarmak için nesne tanımı.
	
	//final String Kayıt= "kayıt";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Java dosyamız çalıştırıldığında activity_main layoutu çağırıldı
		
		text1=(EditText) findViewById(R.id.editText1);//"activiy_main" icideki button ve editTextler
		//icindeki degerler degiskenlere atandi.
		//Yani oluşturulan değişkenler layouttaki öğelerle entegre edildi
		text2=(EditText) findViewById(R.id.editText2);
		oku=(Button) findViewById(R.id.button2);
		kaydet=(Button) findViewById(R.id.button1);
		
		
		
		kaydet.setOnClickListener(new View.OnClickListener(){//veya kaydet.setOnClickListener(this);
			@Override
			public void onClick(final View v) {
				switch (v.getId()) {
				case R.id.button1://eger button1'e basilirsa kaydetText() metodu cagirilir.
					
					AlertDialog.Builder ad=new AlertDialog.Builder(context);
					// AlertDialog.Builder ı başlatma
					//"ad" adinda AlertDialog.Builder nesnesi olusturuldu.
			 
					ad.setTitle("Alert Dialog");
					ad.setMessage("Veri Kaydedilsin mi?");
					// diyalog mesajı ve başlığı
					ad.setCancelable(false);//Cancel edilme özelligi kaldiriliyor.
					ad.setPositiveButton("Evet", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							kaydetText();
						}
							
							});
						
					
					ad.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
							
							});
			
				
					AlertDialog alertDialog=ad.create();//Yukaridaki olaylari gerceklestirmesi icin kullanilir.
					//Yani dialog olusturulur.
					alertDialog.show();//Dialog gosterilir.
				
					//kaydetText();
					break;
				}
				
			}
				
		});
	     oku.setOnClickListener(new View.OnClickListener(){//veya oku.setOnClickListener(this);
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.button2:
					okuText();
					break;
					default:
						break;
				}
			}

	     });
	}
				
		

	void kaydetText(){
		SharedPreferences pref=getSharedPreferences("Kayıt",MODE_PRIVATE);
		//Dosyanin icerigine ulasiyor. Yani "Kayit" isminde verileri kaydedecegimiz bir dosya olusturur
		//Eger var olan dosya varsa bir degiskene kaydetmemize yarar.
		//"MODE_PRIVATE" dosyaya sadece bizim erismemizi saglar.
		
		SharedPreferences.Editor editor = pref.edit();//Duzenlemek icin dosyayi aciyoruz.
		//veya editor = pref.edit();
		editor.putString("Kayıt",text1.getText().toString());//Dosyaya veri alım islemini gerceklestirir.
		//veya
		//String KayıtText=(text1.getText().toString());
		//editor.putString("Kayıt",KayıtText);
		editor.commit();
		 Toast.makeText(this, "Veri Kaydedildi", Toast.LENGTH_SHORT).show();
		
		
	}
	
	void okuText(){
		SharedPreferences pref=getSharedPreferences("Kayıt",MODE_PRIVATE);
		//Dosyanın icerigine ulasıyor. "Editor" kullanilmiyor cunku sadece okuma islemi yapiliyor.
		String kayıt = pref.getString("Kayıt", "");
		//"Kayıt" değerini çekmek için kullanılır."putString()" metodu ile önceden deger atanmissa
		//o degeri, atanmamissa "null" dondurur.
		text2.setText(kayıt);
		 Toast.makeText(this, "Veri Okundu", Toast.LENGTH_SHORT).show();
	}




	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}









	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_first, menu);
		return true;
	}*/

	/*@Override
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
//}
