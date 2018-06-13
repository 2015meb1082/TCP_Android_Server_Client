package com.abhistart.tcpapp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerBackgroundThread implements Runnable {
    private String port;
    private Handler handler;
    private  Context context;
    private ServerSocket serverSocket;
    private  DataInputStream in;
    public ServerBackgroundThread(String port, Context context){
        this.port = port;
        this.context = context;
    }

    @Override
    public void run() {
        while(true){
            try{
                Log.i("Info","Hello");
                 serverSocket = new ServerSocket(Integer.parseInt(port));
                handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Waiting for client on port:"+serverSocket.getLocalPort()+" ......", Toast.LENGTH_LONG).show();
                    }
                });
                Log.i("Info","Waiting for client on port:"+serverSocket.getLocalPort()+" ......");
                serverSocket.setSoTimeout(30000);
                Socket server =  serverSocket.accept();
                Log.i("Info","Just connected to "+server.getRemoteSocketAddress());
                in = new DataInputStream(server.getInputStream());
                // input stream should also go to  main thread of server
                //Log.i("Info",in.readUTF());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(context, in.readUTF(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to "+server.getLocalSocketAddress()+"\nGoodbye");
                server.close();



            }catch(SocketTimeoutException s){

                Log.i("Info","Socket Timeout exception");
                break;
            }catch (IOException e){
                e.printStackTrace();
                break;
            }

        }
    }
}
