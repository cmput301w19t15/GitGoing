package com.example.cmput301w19t15;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * this class is to convert the image type
 * either from base64 to bitmap
 * or from bitmap to base64
 * @see AddBookInfo
 */
public class ConvertPhoto {
    /**
     * this metohod is to convert a String(base64 image) to bitmap
     * @param base64 String - an image format
     * @return bitmap object, that was converted from base64
     */
    public static Bitmap convert(String base64){
        byte[] decoded = Base64.decode(
                base64.substring(base64.indexOf(",")  + 1),
                Base64.DEFAULT
        );
        Bitmap newBitmap = BitmapFactory.decodeByteArray(decoded,0,decoded.length);
        return newBitmap;
    }

    /**
     * this method is to convert a bitmap to base64 format(string)
     * @param bitmap a bitmap obect, a imgnae format
     * @return a base64 image, which is a strig
     */
    public static String convert(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        String newBase64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        return newBase64;
    }
}
