package com.abhistart.tcpapp;

import android.content.Context;

public class ServerParameters {

    public String port;
    public Context context;
    public String message;

    ServerParameters(Context context,String port,String message){
        this.context =context;
        this.port = port;
        this.message = message;
    }
}
