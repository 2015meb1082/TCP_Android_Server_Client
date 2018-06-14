package com.abhistart.tcpapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerBackgroundThread extends AsyncTask<ServerParameters,Void,String>{
    private String port;
    private Handler handler;
    private  Context context;
    private ServerSocket serverSocket;
    private  DataInputStream in;
    String message;

    @Override
    protected String doInBackground(ServerParameters... params) {
        port = params[0].port;
        context = params[0].context;
        message =params[0].message;
        while(true){
            try{
                Log.i("Info","Hello");
                serverSocket = new ServerSocket(Integer.parseInt(port));
                handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Waiting for client on port:"+serverSocket.getLocalPort()+" ......", Toast.LENGTH_LONG).show();
                        Server.serverArrayList.add("Waiting for client on port:"+serverSocket.getLocalPort()+" ......");
                        Server.serverArrayList.add("Server: "+message);
                        Server.serverArrayAdapter.notifyDataSetChanged();
                    }
                });



                Log.i("Info","Waiting for client on port:"+serverSocket.getLocalPort()+" ......");
                serverSocket.setSoTimeout(30000);
                final Socket server =  serverSocket.accept();

                Log.i("Info","Just connected to "+server.getRemoteSocketAddress());
                 handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Server.serverArrayList.add("Just connected to "+server.getRemoteSocketAddress());
                        Server.serverArrayAdapter.notifyDataSetChanged();


                    }
                });



                in = new DataInputStream(server.getInputStream());
                // input stream should also go to  main thread of server
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Server.serverArrayList.add("Client: "+in.readUTF());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Server.serverArrayAdapter.notifyDataSetChanged();
                    }
                });

                Log.i("Info",in.readUTF());


                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(message);
                server.close();



            }catch(SocketTimeoutException s){

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Server.serverArrayList.add("Socket Timeout Exception");
                        Server.serverArrayAdapter.notifyDataSetChanged();
                    }
                });

                Log.i("Info","Socket Timeout exception");
                break;
            }catch (IOException e){
                e.printStackTrace();
                break;
            }

        }

        return null;
    }



}
