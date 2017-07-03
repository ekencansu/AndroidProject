package com.example.cansu.voicetext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.app.AlertDialog;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {


    public ImageButton voice_button;
    public EditText mutline_txt;
    public Button send_btn;
    public Intent intent;
    public static final int request_code_voice = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voice_button = (ImageButton)findViewById(R.id.voice_button);
        mutline_txt = (EditText)findViewById(R.id.voice_txt);
        send_btn = (Button)findViewById(R.id.btn_send);

        mutline_txt.setEnabled(false);

        voice_button.setOnClickListener(new OnClickListener() { // image button a tıklamayı aktif hale getirme olayı

            @Override
            public void onClick(View v) {

                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // intent i oluşturuldu sesi tanıyabilmesi için
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                try{
                    startActivityForResult(intent, request_code_voice);  // activityi başlatıldı belirlenen sabit değer ile birlikte
                }catch(ActivityNotFoundException e)
                {
                    // activity bulunamadığı zaman hatayı göstermek için alert dialog kullanıldı.
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Sorry your phone does not support this system!!!")
                            .setTitle("VoicetoText")
                            .setPositiveButton("OKEY", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }


            }
        });

        send_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Share();

            }
        });




    }
    protected void Share() {


        String txt = mutline_txt.getText().toString();

        if(txt.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Sharing can not be empty!! Please use Speech to Text", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent share_intent = new Intent(android.content.Intent.ACTION_SEND); // intenti oluşturuldu
            share_intent.setType("text/plain");
            share_intent.putExtra(android.content.Intent.EXTRA_TEXT, mutline_txt.getText().toString()); // mesaj içeriği olarak, söylenan söz gönderilecek
            startActivity(Intent.createChooser(share_intent, "Choose for share"));  // paylaşmak istenilen platform seçiliyor
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case request_code_voice: {

                if (resultCode == RESULT_OK && data != null)
                {
                    // intent boş olmadığında ve sonuç tamam olduğu anda konuşma alınp listenin içine atıldı.
                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    mutline_txt.setText(speech.get(0));
                }
                break;
            }

        }
    }





}
