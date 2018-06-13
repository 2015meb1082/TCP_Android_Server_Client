package com.abhistart.tcpapp;

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


    static ListView serverListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

         serverListView  = findViewById(R.id.serverListView);
        Button serverSendButton  = findViewById(R.id.serverSendButton);
        EditText serverEditText = findViewById(R.id.serverMessegeEditText);
        ArrayList<String> serverArrayList =new ArrayList<>() ;
        ArrayAdapter<String> serverArrayAdapter  = new ArrayAdapter<String>(Server.this,android.R.layout.simple_list_item_1,serverArrayList);
        serverListView.setAdapter(serverArrayAdapter);

        serverSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    EditText portEditText=findViewById(R.id.portEditText);
                    String port  = portEditText.getText().toString();
                    if(!port.equals("")){
                        Toast.makeText(Server.this, "Port is: "+port, Toast.LENGTH_SHORT).show();
                        ServerBackgroundThread runnable  = new ServerBackgroundThread(port,Server.this);
                        Thread newThread = new Thread(runnable);
                        newThread.start();
                    }else{
                        Toast.makeText(Server.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }

            }
        });


    }

}
