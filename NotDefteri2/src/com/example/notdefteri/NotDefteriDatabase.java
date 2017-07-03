package com.example.notdefteri;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
//Bu kısımda veritabanınadan okuma ve veritabanına yazma islemleri gerceklesir.
public class NotDefteriDatabase {
	private SQLiteDatabase db;
	private final Context context;
	private final NotDefteriDBHelper dbhelper;

	//constructer
	public NotDefteriDatabase(Context c) {//constructor
	context = c;
	//Dphelper nesnes ile yeni veritabanı oluşturuluyor.
	dbhelper = new NotDefteriDBHelper(context, Sabit.DATABASE, null,
	Sabit.DATABASE_VERSION);
	}

	/*
	* Veritabanını operasyonlara kapatmak
	* için kullanılan method.
	*/
	public void kapat() {
	db.close();
	}
	/*
	* Veritabanını yazma ve okuma için açılan method
	* **!**
	* ->yazmak için aç, yazma operasyonu değilse exception ver ve catch bloğunda okumak için aç
	*/
	public void ac() throws SQLiteException {
	try {
	db = dbhelper.getWritableDatabase();//Yazılabilir bir veritabanı sunar. update,delete ve insert islemlerini gerceklestirmek icin ortam sunar.
	} catch (SQLiteException ex) {
	Log.v("Open database exception caught", ex.getMessage());
	db = dbhelper.getReadableDatabase();//Okunabilir bir veritabanı sunar. Select islemlerini gerceklestirmek için ortam sunar.
	}
	}

	/*
	* Veritabanına not eklediğimiz method.
	* insert Yapısı:
	* —-db.insert(String table, String nullColumnHack, ContentValues icerikDegerleri)
	*/
	public long notEkle(String konu, String icerik) {
	try {
	ContentValues yeniDegerler = new ContentValues();

	yeniDegerler.put(Sabit.KONU, konu);
	yeniDegerler.put(Sabit.ICERIK, icerik);
	yeniDegerler.put(Sabit.TARIH,
	java.lang.System.currentTimeMillis());
	return db.insert(Sabit.TABLO, null, yeniDegerler);

	} catch (SQLiteException ex) {

	Log.v("Veritabanina ekleme isleminde hata tespit edildi !",
	ex.getMessage());
	return -1;
	}
	}

	/*
	* Seçilen bir notu güncellemek için kullandığımız method
	* Update yapısı:
	* ——update(String table, ContentValues icerikDegerleri, String whereCumlecigi,
	* String[] whereArgumanlari)
	*/

	public void notGuncelle(int id, String konu, String icerik) {

	ContentValues guncelDegerler = new ContentValues();
	String[] idArray = { String.valueOf(id) };

	guncelDegerler.put(Sabit.KONU, konu);
	guncelDegerler.put(Sabit.ICERIK, icerik);
	guncelDegerler
	.put(Sabit.TARIH, java.lang.System.currentTimeMillis());
	db.update(Sabit.TABLO, guncelDegerler, Sabit.KEY_ID + "=?",
	idArray);
	}

	/*
	* Veritabanından tüm notları keyID azalan sırada getirmek için
	* kullandığımız method.
	* Son eklenen notun ilk sırada gelmesi için.(Aynı işlem için Tarih bazında da sıralayabilirdik!)
	*
	* query yapısı :
	* —db.query(String table, String[] columns, String selection, String[] selectionArgs,
	* String groupBy, String having, String orderBy)
	*/

	public Cursor tumNotlariGetir() {

	Cursor c = db.query(Sabit.TABLO, null, null, null, null, null,
	Sabit.KEY_ID + " desc");

	return c;
	}

	public ArrayList<Not> tumNotlar() {
	ArrayList<Not> notlar=new ArrayList<Not>();

	Cursor c = tumNotlariGetir();
	//Curson tipinde gelen notlar teker teker dolaşılıyor.
	if (c.moveToFirst()) {
	do {
	int id1 = c.getInt(c.getColumnIndex(Sabit.KEY_ID));
	String konu = c.getString(c.getColumnIndex(Sabit.KONU));

	String icerik = c.getString(c.getColumnIndex(Sabit.ICERIK));
	DateFormat dateFormat = DateFormat.getDateTimeInstance();
	String tarih = dateFormat.format(new Date(c.getLong(c
	.getColumnIndex(Sabit.TARIH))).getTime());
	Not gecici = new Not(id1, konu, icerik, tarih);
	//Veritabanındaki tüm notları birer birer ArrayList’e kaydediliyor.
	notlar.add(gecici);

	} while (c.moveToNext());
	}
	return notlar;
	}

	/*
	* Seçilen bir notu silmek için kullanılan method.
	*Delete Yapısı :
	* ——delete(String table, String whereCumlecigi, String[] whereArgumanları)
	*/

	public void idIleNotSil(int id) {

	db.delete(Sabit.TABLO, Sabit.KEY_ID + "=" + id, null);

	}

}
