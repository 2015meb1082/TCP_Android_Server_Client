package com.abhistart.tcpapp;

import android.content.Context;
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

public class ClientBackgroundThread extends AsyncTask<MyParameters,Void,String> {

    private String ipAddress;
    private String port;
    private  Handler handler;
    private Context context;
    private DataInputStream in;
    String message;

    @Override
    protected String doInBackground(MyParameters... params) {
        ipAddress = params[0].ipAddress;
        port = params[0].port;
        message = params[0].message;
        context = params[0].context;
        if(!ipAddress.equals("")&&!port.equals("")){

            Log.i("Info","Connecting to server with ip: "+ipAddress+" port:"+port+ "Message: "+message);

            try {
                handler = new Handler(context.getMainLooper());
                InetAddress inetAddress =InetAddress.getByName(ipAddress);

                Socket socket = new Socket(inetAddress, Integer.parseInt(port));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                          Client.clientArrayList.add("Connected to server");
                          Client.clientArrayAdapter.notifyDataSetChanged();
                    }
                });

                //Toast.makeText(Client.this, "Just connected to "+socket.getRemoteSocketAddress(), Toast.LENGTH_SHORT).show();
                Log.i("Info","Just connected to "+socket.getRemoteSocketAddress());
                OutputStream outToServer = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Client.clientArrayList.add("Client: "+message);
                        Client.clientArrayAdapter.notifyDataSetChanged();
                    }
                });
                out.writeUTF(message);
                //  out.writeUTF(message);
                InputStream inFromServer = socket.getInputStream();
                in  = new DataInputStream(inFromServer);
                // Toast.makeText(Client.this, "Server Says: "+in.readUTF(), Toast.LENGTH_SHORT).show();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Client.clientArrayList.add("Server: "+in.readUTF());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Client.clientArrayAdapter.notifyDataSetChanged();
                    }
                });
                Log.i("Info","Server Says: "+in.readUTF());

                socket.close();



            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }
}
