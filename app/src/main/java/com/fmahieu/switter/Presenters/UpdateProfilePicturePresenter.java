package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.ProfileLogic;
import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;
import com.fmahieu.switter.ModelLayer.models.MessageResult;

public class UpdateProfilePicturePresenter {
    private final String TAG = "__UpdateProfilePicturePresenter";

    private ProfileLogic mUpdateProfileLogic;

    public UpdateProfilePicturePresenter(){
        mUpdateProfileLogic = new ProfileLogic();
    }

    public void getUpdatedProfile(){
        // retrieve updated data for profile
    }

    /**
     * update current profile attachedPicture and add attachedPicture to the user's list
     */
    public MessageResult updateCurrentProfilePicture(EncodedProfilePicture image){
        return mUpdateProfileLogic.setNewProfilePicture(image.getEncodedImage(), true);
    }

    /**
     * add a attachedPicture to the current user's profile attachedPicture list (doesn't update profile attachedPicture)
     */
    public MessageResult addPictureToProfileList(EncodedProfilePicture image){
        return mUpdateProfileLogic.setNewProfilePicture(image.getEncodedImage(), false);

    }

}



