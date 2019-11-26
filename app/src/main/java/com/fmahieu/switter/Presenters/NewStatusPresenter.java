package com.fmahieu.switter.Presenters;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.NewStatusLogic;
import com.fmahieu.switter.ModelLayer.models.LinkAttachmentImage;
import com.fmahieu.switter.ModelLayer.models.LinkProfilePicture;
import com.fmahieu.switter.ModelLayer.models.NewStatusRequest;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewStatusPresenter {
    private final String TAG = "__NewStatusPresenter";

    private NewStatusLogic mNewStatusLogic;

    public NewStatusPresenter(){
        mNewStatusLogic = new NewStatusLogic();
    }

    public void sendNewStatus(String attchmentImageLink, String message){
        Log.i("__TESTING", "presenter: link:" + attchmentImageLink);
        Profile profile = Profile.getUserInstance();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LinkAttachmentImage imageAttachment = new LinkAttachmentImage(attchmentImageLink);


        Status status = new Status(new LinkProfilePicture(profile.getHandle()),
                                    profile.getFirstName(), profile.getLastName(),
                                    profile.getHandle(), timestamp.toString(),
                                    message, imageAttachment
                                    );

        List<String> hashtags = parseHashtags(message);
        for(int i = 0; i < hashtags.size(); i++){
            Log.i("__TAGS__", hashtags.get(i));
        }
        Log.i(TAG, "sending new status to logic");
        mNewStatusLogic.sendNewStatus(new NewStatusRequest(status, hashtags, profile.getAuthToken()));

    }
    private String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    private List<String> parseHashtags(String text){

        List<String> tags = new ArrayList<>();
        StringBuilder tag;
        int size = text.length();

        for( int i = 0; i < size; i++ ){
            if( text.charAt(i) == '#' ){
                tag = new StringBuilder();
                while ( i < size && text.charAt(i) != ' '  ){
                    tag.append(text.charAt(i));
                    i++;
                }
                tags.add(tag.toString());
            }
        }

        return tags;
    }
}
