package com.fmahieu.switter.ModelLayer.ApplicationLogic;
import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;
import com.fmahieu.switter.ModelLayer.models.MessageResult;
import com.fmahieu.switter.ModelLayer.models.ProfilePictureRequest;
import com.fmahieu.switter.ModelLayer.models.SetFollowUserRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;
import com.fmahieu.switter.ServerCommunication.HttpFacade;

public class ProfileLogic {
    private final String TAG = "__ProfileLogic";

    HttpFacadeInterface mHttpFacade = new HttpFacade();
    DataParser parser = new JsonParser();

    public ProfileLogic(){}

    /**
     *
     * @param replaceProfilePicture: if false will only add the attachedPicture to the user's pic list.
     */
    public MessageResult setNewProfilePicture(String encodedImage, boolean replaceProfilePicture){
        Profile profile = Profile.getUserInstance();
        try {
            String jsonResult = mHttpFacade.updateProfilePicture(new ProfilePictureRequest(profile.getHandle(),
                    encodedImage, profile.getAuthToken(), replaceProfilePicture));
            MessageResult result = (MessageResult) parser.fromJson(jsonResult, MessageResult.class);
            if(result == null){
                return new MessageResult(null, "error while trying to receiving new attachedPicture");
            }


            return result;
        }
        catch (Exception e){
            Log.e(TAG, "error when changing profile pic:", e);
            return new MessageResult(null, "error while trying to send new attachedPicture");
        }
    }

    public MessageResult followUser(String handle){
        return sendFollowRequest(handle, true);
    }

    public MessageResult unfollowUser(String handle){
        return sendFollowRequest(handle, false);
    }

    private MessageResult sendFollowRequest(String handle, boolean follow){
        try{

            String jsonResult = mHttpFacade.followUser( new SetFollowUserRequest(Profile.getUserInstance().getHandle(),
                    handle, follow, Profile.getUserInstance().getAuthToken()));

            return (MessageResult) parser.fromJson(jsonResult, MessageResult.class);
        }
        catch (Exception e){
            Log.e(TAG, "sendFollowRequest: error", e);
            return new MessageResult(null, "something went wrong");
        }
    }

    public IsFollowingResult isProfileFollowingUser(String handle) {
        try{

            String jsonResult = mHttpFacade.isProfileFollowingUser(new IsFollowingRequest(
                    Profile.getUserInstance().getHandle(),
                    handle, Profile.getUserInstance().getAuthToken()));
            IsFollowingResult result = (IsFollowingResult) parser.fromJson(jsonResult, IsFollowingResult.class);

            if(result == null){
                Log.e(TAG, "isProfileFollowingUser: result came back null");
                return new IsFollowingResult(false, "something went wrong");
            }
            else{
                return  result;
            }
        }
        catch (Exception e){
            Log.e(TAG, "isProfileFollowingUser: error: ",e );
            return new IsFollowingResult(false, "network error");
        }

    }
}
