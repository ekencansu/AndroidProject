package com.example.cansu.booksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> book_list;
    String book_names[];
    int book_ids[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    public void onResume()
    {

        super.onResume();
        Database db = new Database(getApplicationContext()); // Db bağlantısı oluşturuyor. İlk seferde database oluşturulur.
        book_list = db.books();//kitap listesini alınır
        if(book_list.size()==0){//kitap listesi boşsa
            Toast.makeText(getApplicationContext(), "Book not added yet.\nAdd + Above Button", Toast.LENGTH_LONG).show();
        }else{
            book_names = new String[book_list.size()]; // kitap adlarını tutucamız string arrayi olusturuldu
            book_ids = new int[book_list.size()]; // kitap id lerini tutucamız string arrayi olusturuldu.
            for(int i=0;i<book_list.size();i++){
                book_names[i] = book_list.get(i).get("book_name");
                //kitap_liste.get(0) bize arraylist içindeki ilk hashmap arrayini döner. Yani tablomuzdaki ilk satır değerlerini
                //kitap_liste.get(0).get("kitap_adi") //bize arraylist içindeki ilk hashmap arrayin anahtarı kitap_adi olan value döner

                book_ids[i] = Integer.parseInt(book_list.get(i).get("id"));
                //Yukarıdaki ile aynı tek farkı değerleri integer a çevirir
            }
            //Kitapları Listelet ve bu listeye listener atar
            lv = (ListView) findViewById(R.id.list_view);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.kitap_adi, book_names);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    //Listedeki her hangibir yere tıklandıgında tıklanan satırın sırasını alıyoruz.
                    //Bu sıra id arraydeki sırayla aynı oldugundan tıklanan satırda bulunan kitapın id sini alıyor ve kitap detaya gönderiyoruz.
                    Intent intent = new Intent(getApplicationContext(), BookDetail.class);
                    intent.putExtra("id", (int)book_ids[arg2]);
                    startActivity(intent);

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.ekle:
                BookAdd();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void BookAdd() {
        Intent i = new Intent(MainActivity.this, AddBook.class);
        startActivity(i);
    }

}
