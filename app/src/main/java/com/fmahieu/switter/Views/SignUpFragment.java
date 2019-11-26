package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.R;

public class SignUpFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final String TAG = "__SignUpFragment";
    private static final int SIGNUP_RESPONSE_CODE = 12;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Switch mShowPasswordSwitch;
    private Button mSignUpButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        setUpViews(view);
        return view;
    }

    private void setUpViews(View view){
        Log.i(TAG, "setting up views");

        mEmailEditText = view.findViewById(R.id.email_signUpFragment_editText);
        mPasswordEditText =  view.findViewById(R.id.password_signUpFragment_editText);
        mShowPasswordSwitch =  view.findViewById(R.id.showPassword_signUpFragment_switch);
        mSignUpButton = view.findViewById(R.id.signUp_signUpFragment_button);

        mShowPasswordSwitch.setOnCheckedChangeListener(this);
        mSignUpButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(checkInput()) {
            // move to signUpInfoActivity and pass the email and password
            Intent intent = SignUpInfoActivity.newIntent(getActivity(),
                    mEmailEditText.getText().toString(),
                    mPasswordEditText.getText().toString());
            startActivityForResult(intent, SIGNUP_RESPONSE_CODE);
        }
    }

    private boolean checkInput(){
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        boolean result = false;

        // <= 4 because should contain at least "@." and two more characters
        if(email.length() <= 4 && !email.contains("@")){
            makeToast("enter a valid email");
        }
        else if(password.length() < 2){
            makeToast("password should be at least 2 characters for more security");
        }
        else{
            result = true;
        }

        return result;
    }

    /**
     * Switch between hidden shown view of the password when the user change the switch widget
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            mPasswordEditText.setTransformationMethod(null);
        }
        else{
            mPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "received a result from SignUpInfoActivity");

        if (resultCode != Activity.RESULT_OK) {
            Log.i(TAG, "onActivityResult: received an activity result with a coded != OK");
            makeToast("Something went wrong");
        }
        else if (requestCode == SIGNUP_RESPONSE_CODE) {
            if (data == null) {
                Log.i(TAG, "onActivityResult: data from result came back null");
                makeToast("Something went wrong");
            }

            boolean isUserSignedUp = SignUpInfoActivity.wasUserSignedUp(data);

            if(isUserSignedUp){
                Log.i(TAG, "user has successfully signed in");

                // tell LoginFragment to let MainActivity know the user has logged in
                Fragment loginFragment = getParentFragment();
                if(loginFragment instanceof LoginFragment){
                    ((LoginFragment) loginFragment).changeSuperFragment();
                }
            }
            else{
                makeToast("Unable to sign up");
            }
        }
    }

}