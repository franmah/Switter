package com.fmahieu.switter.ModelLayer.models;

public class ProfilePictureRequest {


    public String handle;
    public String encodedImage;
    public String token;
    public boolean updateCurrentPicture;


    public ProfilePictureRequest(String handle, String encodedImage, String token, boolean updateCurrentPicture) {
        this.handle = handle;
        this.encodedImage = encodedImage;
        this.token = token;
        this.updateCurrentPicture = updateCurrentPicture;
    }
}
