package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.Presenters.NewStatusPresenter;
import com.fmahieu.switter.R;

public class NewStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "__NewStatusActivity";


    // used to access storage to upload picture
    private static final int READ_REQUEST_CODE = 42;
    private static boolean isImage = false;

    private Profile mProfile;
    private NewStatusPresenter mNewStatusPresenter;

    private ImageView mProfilePic;
    private TextView mHandle;
    private EditText mMessage;
    private VideoView mVideoAttachment;
    private TextView mEditVideo;
    private ImageView mImageAttachment;
    private TextView mEditImage;
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
        mVideoAttachment = findViewById(R.id.videoHolder_videoView_newStatusActivity);
        mEditVideo = findViewById(R.id.videoEdit_textView_newStatusActivity);
        mImageAttachment = findViewById(R.id.imageHolder_imageView_newStatusActivity);
        mEditImage = findViewById(R.id.imageEdit_textView_newStatusActivity);
        mSendButton = findViewById(R.id.sendButton_newStatusActivity);

        mProfilePic.setImageURI(mProfile.getPicture());
        mHandle.setText(mProfile.getHandle().getHandleString());

        mEditVideo.setOnClickListener(this);
        mEditVideo.setOnClickListener(this);
        mEditImage.setOnClickListener(this);
        mSendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if( id == R.id.imageEdit_textView_newStatusActivity ){
            isImage = true;
            selectAttachmentInStorage("image/*");
        }
        else if( id == R.id.videoEdit_textView_newStatusActivity){
            isImage = false;
            selectAttachmentInStorage("video/*");
        }
        else if( id == R.id.sendButton_newStatusActivity ){
            mNewStatusPresenter.sendNewStatus();
            finish();
        }
    }

    /** GET PICTURE **/
    private void selectAttachmentInStorage(String attachmentType){
        Log.i(TAG, "selecting picture from storage");

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(attachmentType); // * means any type of picture
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());

                if( isImage ){
                 mImageAttachment.setImageURI(uri);
                }
                else{
                    mVideoAttachment.setVideoURI(uri);
                }
            }
        }
    }
}
