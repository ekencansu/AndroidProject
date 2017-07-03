package com.example.cansu.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    public ListView msgView;
    public ArrayAdapter<String> msgList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgView = (ListView) findViewById(R.id.listView);

        msgList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        msgView.setAdapter(msgList);


        Button btnSend = (Button) findViewById(R.id.btn_Send);

        receiveMsg();
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final EditText txtEdit = (EditText) findViewById(R.id.text_inputText);


                sendMessageToServer(txtEdit.getText().toString());
                msgView.smoothScrollToPosition(msgList.getCount() - 1);

            }
        });


    }


    public void sendMessageToServer(String str) {

        final String str1=str;
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                String host="10.0.2.2";

                PrintWriter out;
                try {
                    Socket socket = new Socket(host, 8008);
                    out = new PrintWriter(socket.getOutputStream());


                    out.println(str1);
                    Log.d("", "hello");
                    out.flush();
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.d("", "hello222");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.d("", "hello4333");
                }

            }
        }).start();
    }



    public void receiveMsg()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                //final  String host="opuntia.cs.utep.edu";
                final String host="10.0.2.2";
                //final String host="localhost";
                Socket socket = null ;
                BufferedReader in = null;
                try {
                    socket = new Socket(host,8008);
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                while(true)
                {
                    String msg = null;
                    try {
                        msg = in.readLine();
                        Log.d("","MSGGG:  "+ msg);


                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if(msg == null)
                    {
                        break;
                    }
                    else
                    {
                        displayMsg(msg);
                    }
                }

            }
        }).start();


    }

    public void displayMsg(String msg)
    {
        final String mssg=msg;
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                msgList.add(mssg);
                msgView.setAdapter(msgList);
                msgView.smoothScrollToPosition(msgList.getCount() - 1);
                Log.d("","HI!!!!");
            }
        });

    }




}

