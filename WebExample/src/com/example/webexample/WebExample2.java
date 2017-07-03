package com.example.webexample;


import com.example.webexample.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;

public class WebExample2 extends Activity{
	Context context=this;

	private WebView web;//WebView tipinde degisken tanımı yapılıyor.
	
	
	//@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webexample2);

		web = (WebView)findViewById(R.id.webView1);//webexample2.xml dosyasındaki webView1 java koduna baglanıyor.
		//web.getSettings().setJavaScriptEnabled(true);
		
		final AlertDialog.Builder ad=new AlertDialog.Builder(context);
		//AlertDialog.Builder başlatılıyor.
		//"ad" adinda AlertDialog.Builder nesnesi olusturuluyor.
 
		ad.setTitle("Alert Dialog");
		ad.setMessage("diyetkolik.com sayfasına gitmek ister misiniz?");
		//diyalog mesajı ve başlıgı
		ad.setCancelable(false);//Cancel edilme özelligi kaldiriliyor.
		
		ad.setPositiveButton("Evet", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				web.getSettings().setJavaScriptEnabled(true);//javascript kodlarının calıstırılması saglanıyor. yani javascript'in acılması saglanıyor.
				
				web.loadUrl("http://www.diyetkolik.com");//Web sayfası adresi WebView tipindeki web degiskenine yuklenerek
				//web sitesine yonlendirme islemi gerceklestiriliyor.
				
				final ProgressDialog mprogressDialog = ProgressDialog.show(context, "Diyetkolik",
						"Sayfa Yükleniyor...", true);
				//Kullanıcıya yapılan işlemi uyarı mesajı olarak gostermek icin ProgressDialog olusturuluyor.


		 
				web.setWebViewClient(new WebViewClient() {
		 
					//Sayfa yuklenirken bir hata olusursa kullanıcıyı uyarılıyor.
					public void onReceivedError(WebView view, int errorCode,//Bu metod kullanıcıya bir hata kodu donduruyor.
							String description, String failingUrl) {
						
						Toast.makeText(getApplicationContext(), "Sayfa Yüklenemedi!",
								Toast.LENGTH_SHORT).show();
					}
		 
					
					//Sayfanın yuklenme islemi bittiginde progressDialog'u kapatılıyor.
					@Override
					public void onPageFinished(WebView view, String url) {//Sayfa yuklendigi zaman calısıyor.
						super.onPageFinished(view, url);
						
						if (mprogressDialog.isShowing())//mprogressDialog acık olup olmadıgı kontrolediliyor.
							mprogressDialog.dismiss();//Eger acık ise kapatılıyor. Yani sayfa tamamen yuklendiyse kapatılıyor.
					}
				
			
				
				});
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
		
		
	}
}
	
	

	