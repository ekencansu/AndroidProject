package com.example.notdefteri;
//uygulamada kaydedilen notları sıralayan bölümdür.
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NotlariListeleActivity extends ListActivity {//ListView kullanılarak kydedilen notlar gosterildigi icin
	//ListActivity projeye dahil ediliyor.
	private ArrayList<Not> notlar;
	private NotDefteriDatabase dba;//NotDefteriDatabase tipinde degisken tanımlanıyor.
	private CustomListAdapter cla;//CustomListAdapter tipinde degisken tanımlanıyor.
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.listele);

	dba = new NotDefteriDatabase(this);
	dba.ac();//ac() fonk. cagırılıyor.

	
	cla = new CustomListAdapter(this);//Adapter ayarlanıyor.
	setListAdapter(cla);
	notlar = cla.notListesi();

	//Eğer tabloda hiç not bulunmuyorsa, not ekleme sayfasına yönlendirme yapılıyor.
	if (notlar.size() == 0) {
	yeniNot();
	}

	//Tıklamalar
	kisaUzunTiklama();

	}

	public void kisaUzunTiklama() {

	getListView().setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position,
	long id) {

	Context context = getApplicationContext();

	int duration = Toast.LENGTH_SHORT;

	Toast toast = Toast.makeText(context,
	notlar.get(position).icerik.toString(), duration);
	toast.show();
	}
	});

	/*
	*
	* Listedeki itemlere uzun süreli tıklama:
	* Ekrana alert dialog içinde, not ekle, güncelle ve sil seçenekleri gelir
	*
	*/

	getListView().setOnItemLongClickListener(
	new AdapterView.OnItemLongClickListener() {
	@Override
	public boolean onItemLongClick(AdapterView<?> av, View v,
	int pos, long id) {
	final int ps = pos;

	final CharSequence[] items = { "Yeni Not Ekle",
	" Notu Düzenle", "Notu Sil" };

	AlertDialog.Builder builder = new AlertDialog.Builder(
	NotlariListeleActivity.this);
	builder.setTitle(notlar.get(ps).konu.toString());
	builder.setItems(items,
	new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog,
	int item) {

	switch (item) {
	case 0:
	yeniNot();
	break;
	case 1:
	notGuncelle(ps);
	break;
	case 2:
	notSil(ps);
	break;
	default:
	break;
	}
	}
	});

	AlertDialog alert = builder.create();
	alert.show();

	return false;

	}
	});
	}

	/*
	* Not ekleme activity sınıfına yönlendiren method
	*/

	public void yeniNot() {
	Intent i = new Intent(NotlariListeleActivity.this,
	NotEkleActivity.class);
	startActivity(i);
	finish();

	}

	/*
	* Verileri alıp, not güncelleme activity sınıfına
	* yönlendiren method.
	*(Veriler putextra ve benzerleriyle birlikte isim,değer ikilisi olarak
	*diğer aktivity sınıflarına rahatlıkla geçirilebilir.)
	*/
	public void notGuncelle(int ps) {
	Intent i = new Intent(NotlariListeleActivity.this,
	GuncelleActivity.class);
	i.putExtra("ID", notlar.get(ps)._id);
	i.putExtra("KONU", notlar.get(ps).konu);
	i.putExtra("ICERIK", notlar.get(ps).icerik);

	startActivity(i);
	finish();
	}

	/*
	* Not silmek için kullandığımız method.
	* Alert dialog içerisinde kullanıcıya notu silmek eyleminden emin olup
	* olmadığı sorulur. Emin ise not silinir değilse işlem iptal edilir.
	*/

	public void notSil(int p) {
	final int ps = p;

	AlertDialog.Builder builder = new AlertDialog.Builder(//Not silmek istendiginde bir uyarı penceresi cıkarak kullanıcıdan secim yapılması isteniyor.
	NotlariListeleActivity.this);
	builder.setMessage(
	notlar.get(ps).konu
	+ " konulu notu silmek istediğinizden emin misiniz ?")
	.setCancelable(true)
	.setPositiveButton("Evet",
	new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int id) {
	dba.idIleNotSil(notlar.get(ps)._id);

	int duration = Toast.LENGTH_SHORT;
	//Not silindikten sonra silindi olarak bildirir.
	Toast toast = Toast.makeText(getApplicationContext(),
	notlar.get(ps).konu.toString()
	+ "silinmiştir !",
	duration);
	toast.show();
	//Not listesini yenile
	yenile();
	}
	})
	.setNegativeButton("Hayır",
	new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int id) {
	dialog.cancel();
	}
	});
	AlertDialog alert = builder.create();
	alert.show();

	}
	/*
	* Bir not silindikten sonra tekrardan adapter atayarak veritabanından gelen verilerle
	* not listesini yeniliyoruz.
	*/
	public void yenile()
	{
	cla = null;
	cla = new CustomListAdapter(getApplicationContext());
	setListAdapter(cla);
	if ((notlar = cla.notListesi()).size() == 0)
	yeniNot();

	}

}
