package com.fmahieu.switter.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fmahieu.switter.Presenters.LoginPresenter;
import com.fmahieu.switter.R;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "SignInFragment";

    private LoginPresenter mLoginPresenter = new LoginPresenter();

    private EditText mHandleEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;
    private TextView mResetPassword;

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

        mHandleEditText = view.findViewById(R.id.handle_signInFragment_editText);
        mPasswordEditText = view.findViewById(R.id.password_signInFragment_editText);
        mSignInButton = view.findViewById(R.id.signInFragment_button);
        mResetPassword = view.findViewById(R.id.resetPassword_textView_signInFragment);

        mSignInButton.setOnClickListener(this);
        mResetPassword.setOnClickListener(this);
    }

    /**
     *  Called by the async task once the server has returned a result
     *  if the result is null then everything worked, else contain an error message
     */
    private void onServerResult(String result){
        if(result != null){
            makeToast(result);
            mSignInButton.setClickable(true);
        }
        else{
            // tell parent fragment to change fragment
            Fragment loginFragment = getParentFragment();
            if (loginFragment instanceof LoginFragment) {
                ((LoginFragment) loginFragment).changeSuperFragment();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInFragment_button:
                Log.i(TAG, "Signing user in");
                makeToast("Logging...");
                if( checkInput() ) {
                    mSignInButton.setClickable(false); //block button to prevent multiple http request
                    new SignInRequestToServer().execute();
                }
                break;

            case R.id.resetPassword_textView_signInFragment:
                Intent intent  = new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean checkInput() {
        String checkHandle = mHandleEditText.getText().toString();
        boolean result = false;

        if(checkHandle.charAt(0) != '@'){
            makeToast("handle should start with @");
        }
        else if(checkHandle.length() < 2){
            makeToast("handle too short");
        }
        else if(mPasswordEditText.getText().toString().length() < 2){
            makeToast("password should be at least 2 characters");
        }
        else{
            result = true;
        }

        return result;
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private class SignInRequestToServer extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPostExecute(String result) {
            onServerResult(result);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return mLoginPresenter.connectUser(mHandleEditText.getText().toString(),
                                        mPasswordEditText.getText().toString());
        }
    }

}
