package com.abhistart.tcpapp;

import android.content.Context;

class MyParameters {

     public String message;
     public String port;
     public String ipAddress;
   public  Context context;

     MyParameters(String ipAddress,String port,String message,Context context){
        this.ipAddress =ipAddress;
        this.port = port;
        this.message = message;
        this.context = context;
    }


}
