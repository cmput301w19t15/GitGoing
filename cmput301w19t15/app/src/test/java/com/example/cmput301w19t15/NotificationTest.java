package com.example.cmput301w19t15;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
public class NotificationTest extends Activity {
    @Test
    public void NotifyOwner(){
        String type = "request";
        Notification notification = new Notification(type);
        notification.notifyOwner(this);
        NotificationCompat.Builder newNotification = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle(type)
                .setContentText("request");
        assertEquals(newNotification,notification);
    }

    @Test
    public void NotifyBorrower(){
        String type = "accept";
        Notification notification = new Notification(type);
        NotificationCompat.Builder newNotification = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle(type)
                .setContentText("accept");
        assertEquals(notification,newNotification);
    }
}
