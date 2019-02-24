package com.example.cmput301w19t15;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;


public class Notification  {
    private Context context;
    private String username;
    private Book book;
    private String type;
    public Notification(String type){

    }

    public void notifyBorrower(Context context){
        this.context = context;

        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle(type)
                .setContentText("accept");


    }
    public void notifyOwner(Context context){
        this.context = context;
        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentTitle(type)
                        .setContentText("request");


    }
}
