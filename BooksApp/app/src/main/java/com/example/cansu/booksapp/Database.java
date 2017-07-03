package com.example.cansu.booksapp;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqllite_database";//database adı

    private static final String TABLE_NAME = "book_list";
    private static String BOOK_NAME = "book_name";
    private static String BOOK_ID = "id";
    private static String BOOK_AUTOR = "autor";
    private static String BOOK_PRINT_YEAR = "year";
    private static String BOOK_PRİCE = "price";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyor.Bu methodu  çağırılmıyor. Databese de obje oluşturulduğunda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BOOK_NAME + " TEXT,"
                + BOOK_AUTOR + " TEXT,"
                + BOOK_PRINT_YEAR + " TEXT,"
                + BOOK_PRİCE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }




    public void bookDelete(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, BOOK_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void bookAdd(String book_name, String book_autor,String book_print_year,String book_price) {
        //kitapEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book_name);
        values.put(BOOK_AUTOR , book_autor);
        values.put(BOOK_PRINT_YEAR, book_print_year);
        values.put(BOOK_PRİCE, book_price);

        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }


    public HashMap<String, String> bookDetail(int id){
        //Databeseden id si belli olan row u çekmek için.
        //Bu methodda sadece tek row değerleri alınır.

        //HashMap bir çift boyutlu arraydir.anahtar-değer ikililerini bir arada tutmak için tasarlanmıştır.
        //mesala map.put("x","300"); mesala burda anahtar x değeri 300.

        HashMap<String,String> book= new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            book.put(BOOK_NAME, cursor.getString(1));
            book.put(BOOK_AUTOR, cursor.getString(2));
            book.put(BOOK_PRINT_YEAR, cursor.getString(3));
            book.put(BOOK_PRİCE, cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return book
        return book;
    }

    public  ArrayList<HashMap<String, String>> books(){

        //Bu methodda ise tablodaki tüm değerleri alıyor
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyecek
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyor(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> booklist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                booklist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return kitap liste
        return booklist;
    }

    public void bookEdit(String book_name, String book_uthor, String book_print_year, String book_price, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoR(update)
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book_name);
        values.put(BOOK_AUTOR, book_uthor);
        values.put(BOOK_PRINT_YEAR, book_print_year);
        values.put(BOOK_PRİCE, book_price);

        // updating row
        db.update(TABLE_NAME, values, BOOK_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

   /* public int getRowCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }*/

    // yUKARIDAKİ  method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
    //Login uygulamasında kullanılır



    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }



}
