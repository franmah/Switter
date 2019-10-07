package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
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

        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

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
        // move to signUpInfoActivity and pass the email and password
        Intent intent = SignUpInfoActivity.newIntent(getActivity(),
                                                    mEmailEditText.getText().toString(),
                                                    mPasswordEditText.getText().toString());
        startActivityForResult(intent, SIGNUP_RESPONSE_CODE);

    }

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
            // TODO: show meaningfull error
            makeToast("Error");
        }

        if (requestCode == SIGNUP_RESPONSE_CODE) {
            if (data == null) {
                // TODO show meaningful error
                makeToast("Error");
            }

            boolean isUserSignedUp = SignUpInfoActivity.wasUserSignedUp(data);
            if(isUserSignedUp){
                Log.i(TAG, "user has successfully signed in");
                // the user has successfully logged in
                // tell LoginFragment to let MainActivity know the user has logged in
                Fragment loginFragment = getParentFragment();
                if(loginFragment instanceof LoginFragment){
                    ((LoginFragment) loginFragment).changeSuperFragment();
                }
            }
            else{
                //TODO show meaningful error message;
                makeToast("error");
            }
        }
    }

    private void logUserIn(boolean isUserSignedUp){
        if(isUserSignedUp){
            // TODO: find a way to call change fragment inside LoginFragment
        }
    }


}