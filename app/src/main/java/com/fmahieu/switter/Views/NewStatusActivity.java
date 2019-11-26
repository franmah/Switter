package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoader;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoaderTask;
import com.fmahieu.switter.ModelLayer.models.httpModel.LogOutRequest;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.Presenters.NewStatusPresenter;
import com.fmahieu.switter.R;

public class NewStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "__NewStatusActivity";

    private static boolean isImage = false;

    private Profile mProfile;
    private NewStatusPresenter mNewStatusPresenter;


    private ImageView mProfilePic;
    private TextView mHandle;
    private EditText mMessage;
    private EditText mEditImage;
    private EditText mEditTxtLink;
    private Button mSendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_status_activity );

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height *.6));

        mProfile = Profile.getUserInstance();
        mNewStatusPresenter = new NewStatusPresenter();

        setUpWidgets();

    }

    private void setUpWidgets(){
        mProfilePic = findViewById(R.id.profilePic_ImageView_newStatusActivity);
        mHandle = findViewById(R.id.handle_textView_newStatusActivity);
        mMessage = findViewById(R.id.message_editText_newStatusActivity);
        mEditImage = findViewById(R.id.imageEdit_textView_newStatusActivity);
        mEditTxtLink = findViewById(R.id.txtLink_editText_newStatusActivity);
        mSendButton = findViewById(R.id.sendButton_newStatusActivity);

        //PictureLoader.loadPictureLink(this, mProfile.getProfilePictureLink().getLink(), mProfilePic);
        new PictureLoaderTask(mProfilePic, mProfile.getProfilePictureLink().getLink()).execute();
        mHandle.setText(mProfile.getHandle());

        mEditImage.setOnClickListener(this);
        mEditTxtLink.setOnClickListener(this);
        mSendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if( id == R.id.imageEdit_textView_newStatusActivity ){
            isImage = true;
            Log.i("__TESTING____", "is image set to true");
        }
        else if( id == R.id.txtLink_editText_newStatusActivity){
            Log.i("__TESTING____", "is image set to false");
            isImage = false;
        }
        else if( id == R.id.sendButton_newStatusActivity ){
            // send request on simple new thread
            Log.i(TAG, "sending new status on another thread...");
            String attachmentLink = null;
            if(isImage){
                attachmentLink = mEditImage.getText().toString();
                Log.i("__TESING", "in newStatusAcitivty: link: " + mEditImage.getText().toString());
            }
            else{
                attachmentLink = mEditTxtLink.getText().toString();
            }

            final String link = attachmentLink;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mNewStatusPresenter.sendNewStatus(link,
                            mMessage.getText().toString());
                }
            }).start();
            finish();
        }
    }



    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
