package com.abhistart.tcpapp;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        ListView clientListView  =findViewById(R.id.clientListView);
        EditText clientmessege  = findViewById(R.id.clientMessegeEditText);
        Button clientSendButton = findViewById(R.id.clientSendButton);
        ArrayList<String>  clientArrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,clientArrayList);
        clientListView.setAdapter(arrayAdapter);

        clientSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ipEditText = findViewById(R.id.ipEditText);
                EditText portEditText1 = findViewById(R.id.portEditText1);
                String ipAddress  = ipEditText.getText().toString();
                String port  = portEditText1.getText().toString();

                ClientBackgroundThread task = new ClientBackgroundThread(ipAddress,port);
                task.execute();
             //   Thread clientThread = new Thread(clientBackgroundThread);
              //  clientThread.start();

            }
        });


    }
}
