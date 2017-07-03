package com.example.notdefteri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Sabit {
	public static final String DATABASE= "notdefteridb";
	public static final int DATABASE_VERSION=1;
	public static final String TABLO="notlar";
	public static final String KONU="konu";
	public static final String ICERIK="icerik";
	public static final String TARIH="kayittarihi";
	public static final String KEY_ID= "_id";

	}
