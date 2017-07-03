package com.example.cansu.guessnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    int randomNumber;

    public void displayResult(String result){

        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();//mesaj gösterme
    }

    public void guess(View view){//Sayi üretme metodu

        EditText numberEditText = (EditText) findViewById(R.id.numberEditTxt);

        int guessNumber = Integer.parseInt(numberEditText.getText().toString());

        if (guessNumber > randomNumber){
            displayResult("Lower!");
        }else if (guessNumber < randomNumber){
            displayResult("Higher");
        }else{
            displayResult("That's right. Try again!");

            Random rand = new Random();

            randomNumber = rand.nextInt(25) + 1;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rand = new Random();

        randomNumber = rand.nextInt(25) + 1;

    }
}
