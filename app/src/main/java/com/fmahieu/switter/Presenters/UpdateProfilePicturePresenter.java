package com.fmahieu.switter.Presenters;

import android.net.Uri;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateProfileLogic;

public class UpdateProfilePicturePresenter {

    private UpdateProfileLogic mUpdateProfileLogic;

    public UpdateProfilePicturePresenter(){
        mUpdateProfileLogic = new UpdateProfileLogic();
    }

    public void getUpdatedProfile(){
        // retrieve updated data for profile
    }

    /**
     * update current profile picture and add picture to the user's list
     */
    public void updateCurrentProfilePicture(Uri picturePath){
        mUpdateProfileLogic.SetNewProfilePicture(true);
    }

    /**
     * add a picture to the current user's profile picture list (doesn't update profile picture)
     */
    public void addPictureToProfileList(Uri picturePath){
        mUpdateProfileLogic.SetNewProfilePicture(false);

    }

}



