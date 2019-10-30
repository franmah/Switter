package com.fmahieu.switter.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.models.LoginResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.Presenters.LoginPresenter;
import com.fmahieu.switter.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "__ResetPasswordActivity";


    private EditText mHandleEdit;
    private Button mResetPasswordButton;
    private EditText mNewPasswordEdit;
    private EditText mConfirmationCodeEdit;
    private Button mSendConfrimationButton;

    private LoginPresenter mLoginPresenter = new LoginPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "activity started");

        // setup new window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height *.6));

        setContentView(R.layout.reset_password_activity);
        setupWidgets();


    }

    private void setupWidgets(){
        mHandleEdit = findViewById(R.id.usernameEditText_resetPasswordActivity);
        mResetPasswordButton = findViewById(R.id.resetButton_resetPasswordActivity);
        mNewPasswordEdit = findViewById(R.id.confirmationPasswordEditText_resetPasswordActivity);
        mConfirmationCodeEdit = findViewById(R.id.confirmationCodeEditText_resetPasswordActivity);
        mSendConfrimationButton = findViewById(R.id.confirmationButton_resetPasswordActivity);

        mResetPasswordButton.setOnClickListener(this);
        mSendConfrimationButton.setOnClickListener(this);

        mSendConfrimationButton.setClickable(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.resetButton_resetPasswordActivity:
                mLoginPresenter.forgotPassword(mHandleEdit.getText().toString());

                if(Profile.getUserInstance().needToConfirmSignUp()){
                    makeToast("confirmation code sent by email");
                    mSendConfrimationButton.setClickable(true);
                }
                else{
                    makeToast("unable to reset password");
                }

                break;

            case R.id.confirmationButton_resetPasswordActivity:
                mLoginPresenter.resetPassword(mNewPasswordEdit.getText().toString(),
                                                        mConfirmationCodeEdit.getText().toString());

                if(!Profile.getUserInstance().needToConfirmSignUp()){
                    makeToast("password has been changed");
                    finish();
                }
                else{
                    makeToast("Unable to verify code");
                }

                break;
        }
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
