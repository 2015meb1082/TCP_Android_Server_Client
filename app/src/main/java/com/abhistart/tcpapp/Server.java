package com.abhistart.tcpapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Server extends AppCompatActivity {


   ListView serverListView;
   static ArrayList<String> serverArrayList;
   static  ArrayAdapter<String> serverArrayAdapter;
    Button serverSendButton;
    EditText serverEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);


        serverListView  = findViewById(R.id.serverListView);
        serverSendButton  = findViewById(R.id.serverSendButton);
         serverEditText = findViewById(R.id.serverMessegeEditText);
        serverArrayList =new ArrayList<>() ;
        serverArrayAdapter = new ArrayAdapter<String>(Server.this,android.R.layout.simple_list_item_1,serverArrayList);
        serverListView.setAdapter(serverArrayAdapter);


        serverSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    EditText portEditText=findViewById(R.id.portEditText);
                    String port  = portEditText.getText().toString();
                    String message= serverEditText.getText().toString();
                    if(!port.equals("")){
                        Toast.makeText(Server.this, "Port is: "+port, Toast.LENGTH_SHORT).show();
                        ServerParameters serverParameters = new ServerParameters(Server.this,port,message);
                        ServerBackgroundThread serverBackgroundThread  = new ServerBackgroundThread();
                         serverBackgroundThread.execute(serverParameters);


                    }else{
                        Toast.makeText(Server.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }

            }
        });


    }

}
