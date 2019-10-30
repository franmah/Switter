package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.Presenters.UpdateProfilePicturePresenter;
import com.fmahieu.switter.R;

public class UpdateProfilePictureActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "__UpdateProfilePictureActivity";

    // used to access storage to upload picture
    private static final int READ_REQUEST_CODE = 42;

    Profile mProfile;
    UpdateProfilePicturePresenter mUpdateProfilePicturePresenter;

    private ImageView profilePicture;
    private CheckBox updatePictureCheckBox;
    private TextView selectPictureTextView;
    private Button mSendButton;

    private boolean isTextBoxChecked = false;
    private Uri picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_picture_activity);

        // setup new window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height *.6));

        mUpdateProfilePicturePresenter = new UpdateProfilePicturePresenter();
        mUpdateProfilePicturePresenter.getUpdatedProfile();

        mProfile = Profile.getUserInstance();

        setUpWidgets();
    }

    private void setUpWidgets(){

        profilePicture = findViewById( R.id.profilePicture_imageView_updateProfilePictureActivity );
        updatePictureCheckBox = findViewById( R.id.updatePicture_checkBox_updateProfilePictureActivity );
        selectPictureTextView = findViewById( R.id.selectNewPicture_TextView_updateProfilePictureActivity );
        mSendButton = findViewById( R.id.sendButton_updateProfilePictureActivity );

        profilePicture.setImageBitmap(mProfile.getPicture().getBitmapImage());

        selectPictureTextView.setOnClickListener( this );
        updatePictureCheckBox.setOnClickListener( this );
        mSendButton.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if( id == R.id.selectNewPicture_TextView_updateProfilePictureActivity ){
            selectPictureInStorage();
        }
        else if( id == R.id.updatePicture_checkBox_updateProfilePictureActivity ){
            isTextBoxChecked = ((CheckBox) v).isChecked();
        }
        else if( id == R.id.sendButton_updateProfilePictureActivity ) {
            if ( picturePath != null ) {
                if( isTextBoxChecked ) {
                    mUpdateProfilePicturePresenter.updateCurrentProfilePicture( picturePath );
                }
                else{
                    mUpdateProfilePicturePresenter.addPictureToProfileList( picturePath );
                }
                finish();
            }
            else{
                makeToast( "Select a new picture" );
            }
        }

    }


    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    /** GET PICTURE **/
    private void selectPictureInStorage(){
        Log.i(TAG, "selecting picture from storage");

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*"); // * means any type of picture
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
                profilePicture.setImageURI(uri);
                picturePath = uri;
            }
        }
    }

}
