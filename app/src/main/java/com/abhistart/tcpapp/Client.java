package com.abhistart.tcpapp;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends AppCompatActivity {

   static ArrayList<String>  clientArrayList;
   static   ArrayAdapter<String> clientArrayAdapter;

    private String ipAddress;
    private String port;
    EditText clientmessege;

    private DataInputStream in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        ListView clientListView  =findViewById(R.id.clientListView);

         clientmessege   = findViewById(R.id.clientMessegeEditText);
        Button clientSendButton = findViewById(R.id.clientSendButton);
        clientArrayList = new ArrayList<>();
        clientArrayAdapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,clientArrayList);
        clientListView.setAdapter(clientArrayAdapter);



        clientSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ipEditText = findViewById(R.id.ipEditText);
                EditText portEditText1 = findViewById(R.id.portEditText1);
                 ipAddress  = ipEditText.getText().toString();
                 port  = portEditText1.getText().toString();
                String  message = clientmessege.getText().toString();
                 MyParameters myParameters = new MyParameters(ipAddress,port,message,Client.this);
                ClientBackgroundThread clientBackgroundThread = new ClientBackgroundThread();
                clientBackgroundThread.execute(myParameters);


            }
        });


    }
}
