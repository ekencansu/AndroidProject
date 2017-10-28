package com.example.cansu.havadurumu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.cansu.havadurumu.R;
import com.example.cansu.havadurumu.fragment.MainActivityFragment;
import com.example.cansu.havadurumu.preferences.Prefs;
import com.example.cansu.havadurumu.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Prefs preferences;
    MainActivityFragment mf;
    @BindView(R.id.toolbar) Toolbar toolbar;
    NotificationManagerCompat mManager;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("Activity" , MainActivity.class.getSimpleName());


        preferences = new Prefs(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);//kaynakları projeye ekler ve kod tekrarından kurtarır.
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        handler = new Handler();

        mf = new MainActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mode" , intent.getIntExtra(Constants.MODE , 0));
        mf.setArguments(bundle);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, mf)
                .commit();

    }

}
