package com.example.cmput301w19t15;

import android.app.Activity;
import android.content.Context;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
public class NotificationTest extends Activity {
    @Test
    public void NotifyOwner(){
        Notification notification = new Notification();
        notification.notifyOwner(this);
    }

    @Test
    public void NotifyBorrower(){

    }
}
