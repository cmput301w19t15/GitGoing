package com.example.cmput301w19t15;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConvertPhoto {
    public static Bitmap convert(String base64){
        byte[] decoded = Base64.decode(
                base64.substring(base64.indexOf(",")  + 1),
                Base64.DEFAULT
        );
        Bitmap newBitmap = BitmapFactory.decodeByteArray(decoded,0,decoded.length);
        return newBitmap;
    }
    public static String convert(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        String newBase64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        return newBase64;
    }
}
