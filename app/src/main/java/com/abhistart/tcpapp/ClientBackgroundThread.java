package com.abhistart.tcpapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientBackgroundThread extends AsyncTask<Void,Void,Void> {

   private String ipAddress;
    private String port;


    public   ClientBackgroundThread(String ipAddress,String port){
        this.ipAddress = ipAddress;
        this.port  = port;

    }


    @Override
    protected Void doInBackground(Void... voids) {
        if(!ipAddress.equals("")&&!port.equals("")){
//            Toast.makeText(Client.this, "Ip is: "+ipAddress, Toast.LENGTH_SHORT).show();
//            Toast.makeText(Client.this, "Port is: "+port, Toast.LENGTH_SHORT).show();
            Log.i("Info","Connecting to server with ip: "+ipAddress);
            try {

                InetAddress inetAddress =InetAddress.getByName(ipAddress);
                Socket socket = new Socket(inetAddress, Integer.parseInt(port));
                //Toast.makeText(Client.this, "Just connected to "+socket.getRemoteSocketAddress(), Toast.LENGTH_SHORT).show();
                Log.i("Info","Just connected to "+socket.getRemoteSocketAddress());
                OutputStream outToServer = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF("Hello from client with address: "+socket.getLocalSocketAddress());
                InputStream inFromServer = socket.getInputStream();
                DataInputStream in  = new DataInputStream(inFromServer);
                // Toast.makeText(Client.this, "Server Says: "+in.readUTF(), Toast.LENGTH_SHORT).show();
                Log.i("Info","Server Says: "+in.readUTF());
                socket.close();



            } catch (IOException e) {
                e.printStackTrace();
            }


        }
//        else{
//
//
//            //Toast.makeText(Client.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
//        }
        return null;
    }
}
