package com.example.notdefteri;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
//bu kısımda veritabanı olusturma ve guncelleme islemleri yapılıyor.

public class NotDefteriDBHelper extends SQLiteOpenHelper{
	
	public NotDefteriDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
	}

	private static final String CREATE_TABLE = "create table "
			+ "notlar"+ "("+ Sabit.KEY_ID
			+" integer primary key autoincrement, " + Sabit.KONU
			+ " text not null, " + Sabit.ICERIK +  "text not null,"
			+ Sabit.TARIH + " long);";

			

			@Override
			public void onCreate(SQLiteDatabase db) {//Veritabanı ilk oluşturulurken cagilir. Tablolar,viewler vs bu ksımda olusturulur.
			Log.v("NotDefteriDBHelper OnCreate", "Tablolar oluşturuyor…");
			try {
			// db.execSQL(“drop table if exists”+Sabitler.TABLO);
			db.execSQL(CREATE_TABLE);
			} catch (SQLiteException ex) {
			Log.v("Tablo olusturma hatasi tespit edildi", ex.getMessage());

			}
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				//Veritabanında herhangi bir degisiklik yapıldıgında cagırılır.
			Log.w("Upgrade islemi","Tum verile silinecek !");
			/*
			* Yenisi geldiğinde eski tablodaki tüm veriler silinecek ve
			* tablo yeniden oluşturulacak.
			*/
			db.execSQL("drop table if exists " + Sabit.TABLO);
			onCreate(db);
			}
	

}
