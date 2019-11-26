package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;
import com.fmahieu.switter.Presenters.LoginPresenter;
import com.fmahieu.switter.R;

// TODO: cleanup the layout file (replace outside linear layout by scroll)

public class SignUpInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "__SignUpInfoActivity";
    private static final String EMAIL_FROM_SIGNUP = "com.fmahieu.switter.Views.SignupInfoActivity.email_from_signup";
    private static final String PASSWORD_FROM_SIGNUP = "com.fmahieu.switter.Views.SignupInfoActivity.password_from_signup";
    private static final String SIGNUP_RESULT = "com.fmahieu.switter.Views.SignupInfoActivity.sendSignUpResult";

    // used to access storage to upload attachedPicture
    private static final int READ_REQUEST_CODE = 42;

    private LoginPresenter mLoginPresenter = new LoginPresenter();

    boolean hasUserLoggedIn = false;

    // elements passed by SignUpFragment
    private String email;
    private String password;

    // Picture selected by user
    private EncodedProfilePicture profilePicture;

    private ImageView mProfilePicture;
    private TextView mEditPhoto;
    private EditText mFirstName;
    private  EditText mLastName;
    private EditText mHandle;
    private Button mContinue;
    private EditText mCodeEditText;
    private Button mCodeButton;


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
        mCodeEditText = findViewById(R.id.codeConfirm_signUpInfoActivity);
        mCodeButton = findViewById(R.id.codeConfirmButton_signUpInfoActivity);

        mCodeButton.setOnClickListener(this);
        mEditPhoto.setOnClickListener(this);
        mContinue.setOnClickListener(this);
    }

    /**
     * Called once the server (the async task) has returned a result
     */
    private void onServerResult(String result){
        if(result == null){
            setSignUpResult(true);

        }
        else {
            makeToast(result);
            mContinue.setClickable(true);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editPhoto_signUpInfoActivity_EditText:
                selectPictureInStorage();
                break;

            case R.id.continue_signUpInfoActivity_button:
                if(checkBasicInput()){
                    mContinue.setClickable(false);
                    new SignUpRequestToServer().execute();
                }
                break;

            case R.id.codeConfirmButton_signUpInfoActivity:
                /*
                // confirm and connect user
                mLoginPresenter.confirmCode(mCodeEditText.getText().toString(), mHandle.getText().toString(),
                        password);
                if(Profile.getUserInstance().isLoggedIn()){
                    setSignUpResult(hasUserLoggedIn);
                }
                else{
                    makeToast("Unable to confirm code");
                }
                */
                break;
        }
    }

    /**
     * check if user input format is right
     * (only checks for attributes such as length, format...)
     * @return true if user input is long enough and has the right format, else false
     */
    private boolean checkBasicInput(){
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String handle = mHandle.getText().toString();

        boolean result = false;

        if(firstName.length() < 1 || lastName.length() < 1 || handle.length() < 1){
            makeToast("every field needs to be filled");
        }
        else if(handle.charAt(0) != '@'){
            makeToast("handle should start with '@'");
        }
        else if (profilePicture == null){
            makeToast("please select a profile attachedPicture");
        }
        else{
            result = true;
        }

        return result;
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * tells SignUpFragment that the user has successfully signed up/in
     * @param isUserSignedUp
     */
    private void setSignUpResult(boolean isUserSignedUp) {
        Log.i(TAG, "setting the result");
        Intent data = new Intent();
        data.putExtra(SIGNUP_RESULT, isUserSignedUp);
        setResult(RESULT_OK, data);

        // kill the activity to return to the previous fragment and show home fragment
        Log.i(TAG, "about to return");
        finish();
    }

    /** GET PICTURE FROM STORAGE **/

    private void selectPictureInStorage(){
        Log.i(TAG, "selecting attachedPicture from storage");

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*"); // * means any type of attachedPicture
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
                Log.i(TAG, "Uri: " + uri.getPath());

                // show the image that has been selected
                mProfilePicture.setImageURI(uri);

                // convert uri to image
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    profilePicture = new EncodedProfilePicture();
                    profilePicture.setBitmapImage(bitmap);
                }
                catch (Exception e){
                    Log.e(TAG, "Error while converting uri to image",  e);
                    makeToast("something went wrong");
                    profilePicture = null;
                }
            }
        }
    }

    private class SignUpRequestToServer extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            onServerResult(result);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return mLoginPresenter.signUserUp(profilePicture, mFirstName.getText().toString(),
                                                mLastName.getText().toString(),
                                                mHandle.getText().toString(),
                                                password, email);
        }
    }

}
