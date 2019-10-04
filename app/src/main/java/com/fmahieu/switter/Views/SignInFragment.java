package com.fmahieu.switter.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fmahieu.switter.Presenters.SignInPresenter;
import com.fmahieu.switter.R;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "SignInFragment";

    private SignInPresenter signInPresenter = new SignInPresenter();

    private EditText mHandleEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.signin_fragment, container, false);

        setUpViews(view);

        return view;
    }

    private void setUpViews(View view){
        Log.i(TAG, "setting up views");

        mHandleEditText = (EditText) view.findViewById(R.id.handle_signInFragment_editText);
        mPasswordEditText = (EditText) view.findViewById(R.id.password_signInFragment_editText);
        mSignInButton = (Button) view.findViewById(R.id.signInFragment_button);

        // set up listeners
        mSignInButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO: pass input to SignInPresenter and check if the user is connected
        signInPresenter.connectUser();

        Fragment loginFragment = getParentFragment();
        if(loginFragment instanceof LoginFragment){
            ((LoginFragment) loginFragment).changeSuperFragment();
        }


    }

    private void checkHandleInput(CharSequence text){
        if(text.length() == 0){
            makeToast("too short");
        }
        else{
            if(text.charAt(0) != '@'){
                makeToast("Handles start with '@'");
            }
            else if(text.length() < 2){
                makeToast("too short");
            }
        }
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }






}
