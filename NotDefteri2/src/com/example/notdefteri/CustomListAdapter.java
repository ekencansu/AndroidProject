package com.example.notdefteri;
//Bu kısımda veritabanına kaydedilen veriler alınarak listeler halınde gosteriliyor.
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class CustomListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Not> notlar;
	private NotDefteriDatabase dba;

	public CustomListAdapter(Context context) {
	// Her defasında tekrardan sorulmasından kaçınmak için LayoutInflate’i bellege alınıyor.
	mInflater = LayoutInflater.from(context);
	notlar = new ArrayList<Not>();

	//Veritabanından tüm notları alıyor ve notlar isimli ArrayList’e atanıyor.
	dba = new NotDefteriDatabase(context);
	dba.ac();
	notlar=dba.tumNotlar();
	dba.kapat();
	}

	public int getCount() {
	return notlar.size();
	}

	public long getItemId(int position) {
	return position;
	}

	public Not getItem(int i) {
	return notlar.get(i);
	}

	public ArrayList<Not> notListesi() {
	return notlar;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

	/* ViewHolder’ı diger gorsellerin referansını tutmak için kullanılıyor.
	* Bu sayede belirli tekniklerle her defasında findViewById() çağrımından kaçınılıyor.
	*/
	ViewHolder holder;
	/*
	* convertView null degilse tekrardan inflate yapmadan etkin bir şekilde çagırılıyor,
	* eger null ise viewholder’daki referanslar ilkleniyor.
	*
	*/
	if (convertView == null) {
	//adapterin kullanıldığı durumlarda notsatiri.xml dosyasının yapısı kullanılacak.
	convertView = mInflater.inflate(R.layout.notsatiri, null);

	//viewholder oluştur ve değerleri bağla.
	holder = new ViewHolder();
	holder.mKonu = (TextView) convertView.findViewById(R.id.konuText);
	holder.mTarih = (TextView) convertView.findViewById(R.id.tarihText);
	} else {

	//var olan viewholder’ı etkin bir şekilde geri çağırılıyor.
	holder = (ViewHolder) convertView.getTag();
	}

	/*
	* veritabanındaki her değeri custom olarak belirlenen ve 2 adet textview’den olusan
	* layouttaki textvew’lere atanıyor.
	*/

	holder.mTarih.setText(notlar.get(position).kayittarihi);
	holder.mKonu.setText(notlar.get(position).konu);

	
	convertView.setTag(holder);

	return convertView;
	}

	//TextView tipinde iki deger tutuluyor.
	public class ViewHolder {
	TextView mKonu;
	TextView mTarih;
	}

}
