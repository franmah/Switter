package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.models.Profile;
import com.fmahieu.switter.Presenters.SignUpPresenter;
import com.fmahieu.switter.R;

// TODO: cleanup the layout file (replace outside linear layout by scroll)

public class SignUpInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "__SignUpInfoActivity";
    private static final String EMAIL_FROM_SIGNUP = "com.fmahieu.switter.Views.SignupInfoActivity.email_from_signup";
    private static final String PASSWORD_FROM_SIGNUP = "com.fmahieu.switter.Views.SignupInfoActivity.password_from_signup";
    private static final String SIGNUP_RESULT = "com.fmahieu.switter.Views.SignupInfoActivity.sendSignUpResult";

    // used to access storage to upload picture
    private static final int READ_REQUEST_CODE = 42;

    private SignUpPresenter signUpPresenter = new SignUpPresenter();


    private String email;
    private String password;

    private ImageView mProfilePicture;
    private TextView mEditPhoto;
    private EditText mFirstName;
    private  EditText mLastName;
    private EditText mHandle;
    private Button mContinue;


    public static Intent newIntent(Context packageContext, String email, String password){
        Intent intent = new Intent(packageContext, SignUpInfoActivity.class);
        intent.putExtra(EMAIL_FROM_SIGNUP, email);
        intent.putExtra(PASSWORD_FROM_SIGNUP, password);
        return intent;
    }

    // retrieve data from the signUp operation

    public static boolean wasUserSignedUp(Intent result) {
        // TODO: remove hardcoded value (true) by result from signing up the user
        return result.getBooleanExtra(SIGNUP_RESULT, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "activity started");

        // retrieve info from intent
        email = getIntent().getStringExtra(EMAIL_FROM_SIGNUP);
        password = getIntent().getStringExtra(PASSWORD_FROM_SIGNUP);

        setContentView(R.layout.signup_info_activity);
        setUpViews();

    }

    private void setUpViews(){
        Log.i(TAG, "setting up views");

        mProfilePicture = findViewById(R.id.profilePicture_signUpInfoActivity_imageView);
        mEditPhoto = findViewById(R.id.editPhoto_signUpInfoActivity_EditText);
        mFirstName = findViewById(R.id.firstName_signUpInfoActivity_editText);
        mLastName = findViewById(R.id.lastName_signUpInfoActivity_editText);
        mHandle = findViewById(R.id.handle_signUpInfoActivity_editText);
        mContinue = findViewById(R.id.continue_signUpInfoActivity_button);

        mEditPhoto.setOnClickListener(this);
        mContinue.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "a click has been detected");

        switch (v.getId()){
            case R.id.editPhoto_signUpInfoActivity_EditText:
                selectPictureInStorage();
                break;
            case R.id.continue_signUpInfoActivity_button:
                // TODO: start async function that calls signupPresenter and then return result
                signUpPresenter.signUserUp();
                setSignUpResult(true);
                break;
        }

    }

    private void setSignUpResult(boolean isUserSignedUp) {
        Log.i(TAG, "setting the result");
        Intent data = new Intent();
        data.putExtra(SIGNUP_RESULT, isUserSignedUp);
        setResult(RESULT_OK, data);

        // kill the activity to return to the previous fragment and show home fragment
        Log.i(TAG, "about to return (finish())");
        finish();
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
                mProfilePicture.setImageURI(uri);

                // TODO: remove:
                Profile profile = Profile.getUserInstance();
                profile.setPicture(uri);
            }
        }
    }

    /* TODO: remove if not used (if used: should be processed in another thread.)
    private void getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        mProfilePicture.setImageBitmap(image);
    }
    */
}
