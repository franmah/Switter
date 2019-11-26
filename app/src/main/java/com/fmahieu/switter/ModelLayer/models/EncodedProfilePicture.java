package com.fmahieu.switter.ModelLayer.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EncodedProfilePicture {

    String encodedImage;

    public EncodedProfilePicture(){}

    public EncodedProfilePicture(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    /**
     * Turn bitmap image into a string
     * @return
     */
    private String encodeImage(Bitmap bitmap) {

        // give your image file url in mCurrentPhotoPath
        //Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap decodeBase64AndSetImage() {

        // Incase you're storing into aws or other places where we have extension stored in the starting.
        String imageDataBytes = encodedImage.substring(encodedImage.indexOf(",")+1);

        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));

        return BitmapFactory.decodeStream(stream);
    }

    /** GETTERS AND SETTERS **/

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public void setBitmapImage(Bitmap bitmapImage){
        if(bitmapImage == null){
            this.encodedImage = null;
        }
        else{

            encodedImage = encodeImage(bitmapImage);
        }

    }

    public Bitmap getBitmapImage(){
        return decodeBase64AndSetImage();
    }

}
