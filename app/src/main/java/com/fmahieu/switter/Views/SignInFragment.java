package com.fmahieu.switter.Views;

import android.content.Intent;
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

import com.fmahieu.switter.ModelLayer.models.Image;
import com.fmahieu.switter.ModelLayer.models.LoginResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
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

        mHandleEditText = (EditText) view.findViewById(R.id.handle_signInFragment_editText);
        mPasswordEditText = (EditText) view.findViewById(R.id.password_signInFragment_editText);
        mSignInButton = (Button) view.findViewById(R.id.signInFragment_button);
        mResetPassword = view.findViewById(R.id.resetPassword_textView_signInFragment);

        // set up listeners
        mSignInButton.setOnClickListener(this);
        mResetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInFragment_button:
                Log.i(TAG, "Signing user in");
                mLoginPresenter.connectUser( mHandleEditText.getText().toString(), mPasswordEditText.getText().toString() );

                //TODO: remove
                Profile.getUserInstance().setLoggedIn(true);
                Image image = new Image(TOREMOVE);
                Profile.getUserInstance().setPicture(image);
                /////

                if(Profile.getUserInstance().isLoggedIn()) {
                    Log.i(TAG, "User has signed in");

                    Fragment loginFragment = getParentFragment();
                    if (loginFragment instanceof LoginFragment) {
                        ((LoginFragment) loginFragment).changeSuperFragment();
                    }

                }
                else{
                    makeToast("Unable to sign user in");
                }
                break;

            case R.id.resetPassword_textView_signInFragment:
                Intent intent  = new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }




    private String TOREMOVE = new String("/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABQODxIPDRQSEBIXFRQYHjIhHhwcHj0sLiQySUBMS0dA\n" +
            "    RkVQWnNiUFVtVkVGZIhlbXd7gYKBTmCNl4x9lnN+gXz/2wBDARUXFx4aHjshITt8U0ZTfHx8fHx8\n" +
            "    fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHx8fHz/wAARCBIACQADASIA\n" +
            "    AhEBAxEB/8QAGwABAQADAQEBAAAAAAAAAAAAAAECAwQFBgf/xABHEAABBAEDAwMDAgQEBAYAAQ0B\n" +
            "    AAIDEQQSITEFQVETImEUMnFCgRUjkaEGM1KxJCVDwTQ1U2Jy0Rbh8GNzgvEmREWSojZU/8QAGAEB\n" +
            "    AQEBAQAAAAAAAAAAAAAAAAECAwT/xAAhEQEBAQEAAwEAAwADAAAAAAAAAQIREiExAxMyQSJCUf/a\n" +
            "    AAwDAQACEQMRAD8A9fGYY8aNr/uAohbDwic7d1R5vUTrycaG9wdR+VvbEPrNdGuFoB9brznXqbG2\n" +
            "    r8LdLC5znODiL7IONoeyScOjdbnWF6WLEYsdjHcjmlxtfLBksY8BweatekgiKqFBzzy6Xhukkdyu\n" +
            "    XKkgdC7f39tlnPkamlwbqAO9q4zmyua17QNQsIOrGbpxYm1w0LYr/ZYoC4c7IngLXQix3XcvNy8i\n" +
            "    Rs5YW00cEIM4JZZxfqAXyAKXoMBDQCvLxsaQyepG9uknjuvVbwgIqsUHH1K9MJAvS6ysjPjujLtg\n" +
            "    K7hJ5QSWBmst5XLDJHKWtlYAHf0QdHTXj05G3sXbWV2lcrcNjCKsUbAtdX55QFy5jS90NcA7rpK5\n" +
            "    ZZHun9JgGoDgoOXIAGeHOoMa3a/K6enWY3nhhd7Vomc+JwdM0EdyF6Qa1rRp4pBaUREGuVxa2wLH\n" +
            "    dc7p2VUgc3fbbZdlAqem08tB/KDl6eKErq2c5digaGigKVQRa5nlg1Va2laMjXQ9PcoNJnYG+8UB\n" +
            "    vxawwsdjzI4gUTsmtwd7m3vRXY1wBAAoV4QZtaGihwqVVEHLmsL/AEhewduPK2uij0lun21dLRKX\n" +
            "    TZDoWEWP6rkgnnDZSHamxu00e6DuwQRFI08avb+F0lYQ6TE1zOHC6WZQFqne1rfd37LauHIP/HM9\n" +
            "    T/LLeyC1C87gb9ytX0T2te8O3G7QCujJZF6BLS2hxXlbcbU7HZq3NIMoXOdExz/uI3WaIgqqiqAo\n" +
            "    raiAtWSSISW8jcrMu0iytGQ8uAbG7dwooNeuLKx2URTTxfC24bGtYSAL8rgfguip7XBxadwvUiAD\n" +
            "    AWirQZqKqIKFUCICIogq1TziLTfdbFxZx05eK9wtnBQbhlMJo2FsZK1zg0HcrB8TDvyR3C5Xs05k\n" +
            "    QjurBKD0UQ8lEBFolkcx2wJCwZmMc4t3tB1KrVFM2QnSdxytiAqoiCoiICIogqIiAiqiCoiICiqI\n" +
            "    IqiIIqiICIiAiIgIiICIiAiqIIiIgIiqCIqiCUlKogxryqVUQa2xtaTQWaqIIeCuUxOiJ0bjwutR\n" +
            "    BxtlLdy3+quvUdl1FoPICmgDsB+yCDhVFUHI5zZM2u0YqlJnRUQTTjtassLhkSSt5esJMW4q/WTu\n" +
            "    UG7Fx2wM/wDceVvQfaPwqgi5sgguLSeF1Lja5srTIdhdG0GIaDY1fn5XXE0MZoHZcUOmSUNB3XaA\n" +
            "    RIXeUGScqog5J4njIMjKNtpa347jAS40613bFWgRRFhBjFfpM1fdW6yRRBQqpdcrAyt33Br5QbFE\n" +
            "    BtEBEUDhqqxfhBrfKzV+Fy5/pHDkoAO20+VtbEPcXckrnyoRI+JjSDZ3Qd8IqCMXdNAWawiZ6cYZ\n" +
            "    4WaAuaay8kFdK5H6tfKDS6T05ow8bONflejxsvPdGZp4tXDDa9AmySgKFVQi2OHkIMWyBwJvhLY9\n" +
            "    3YkLgj9RsbohySd1uY10eUwAEtoWg7FFSogLRJH7iQatbJn+nC9/gLmb6jmteCgxd6keVEy7Y8rv\n" +
            "    Io0uWEl82l33MFrqQRUGtyoo8nQ7TzSDy3BwjnB3cXbFb2OdIMdo/TyjjR3bVrKJ7Wk7EHsg7UWL\n" +
            "    XahY/oskBFi97WCyVgJm3uaQbqRAQRY4RAURR96TXNIPOyYpPVcXbtPAC24kTdQoFpG6ye46bIK1\n" +
            "    QSgykg7jYhB6CKMcHtsKoKERVAUVRBEJABJRR1FpCDz2t9IuDD7SVsiiMmQ2Xgs7rN0INgGvCnT9\n" +
            "    X80SXbTsg60REBVRVAUVUQFVFUBERAREQYSi4yB3XK9r9B7gBbcmYsfGxu5duVIJQ8SXuWcoMcF2\n" +
            "    uNxqqNLqWjCaBjhw/WSSt6CKEhosmgqubMJ9WCOva47lBuErbqws7vhc8mO2nAcjglXDcXwW7nVS\n" +
            "    DeiIgqqgVQFFVEBcfUHFrGAd3Lspc09STNjIsDcoOfHkDM2Rn6Ay7XVhy+rG9x7OoLmkZGJSAdyK\n" +
            "    XbDG2KMNbxygyUJ2VSkHN6msljgdjt8qvhaBqs0t3pt1A0LVe3UKQYwsEbNqsrNBsEQFz5cfqaBx\n" +
            "    p3XSuV4fJM42Q0bBBz6HtmfIOXbLrxI/Sg0k2SbXNOJI49hd8VyunGLjC3Xz3QbgiKoCqIgKKqIA\n" +
            "    VpEQFDSyWuUkN2FoOWd3/HRNNaNNmzytT5C/U5o9reVtAOS4h4Gqlu9BvpFg2tBljEPx2OaKBW1Y\n" +
            "    xtDGBo7LJAWt7mA06v3WxaZIdZJ8oMTHGdwB+ywY0MzGsH+myhgcK0misMIOObkOeSS0abQdqUiI\n" +
            "    CKogBcHXZDH0iYA06QhoXevK/wAQe9uHAPue+6QdWLD6fTYISOY7cCvI6rH9NnQxQup0jQQF7eXN\n" +
            "    Hi6Gvd2ofsvBycts/V2ZTgP5bdLW+fCDtZlS45DJxdckdl3wTxTN1MIXk5GX60LgxriSN6HCvSIp\n" +
            "    xmMLmlkYaTv3Qe4EVRBFUVQEREBERQa5n6Grn+qbadRcQI238leYXG7C5b1yu2MdnXqtma7exsst\n" +
            "    TD9xC8kOo8LITOHdY83TwekWtJqh+yx9Fh7BcbcktNb8LOPMvYgnfc2nYnjY2/St5o3XAOy1nErg\n" +
            "    VstjcppNXa2tmY4mv7q8lS3UY48Bafceyxy4i63DcrqZuNlS21vx9MeXvrxCwtPkHZbYINTgflek\n" +
            "    YG70N1kyINAoUs/xt/yNoGw+AiIuzhREREEVRBEVUQEKIVRiiqiDg628t6c1o2MklfsN14fLr7r1\n" +
            "    evSf+HjvcWaXlNFkeEF5O6xv3K8ORoBO6CLLsK7LEd1Qbafwgx4Cqm9DZUD2/vug+qQGjfjdCtWV\n" +
            "    IIcaWQkt0t2QcPTnB0mXMR9zvb/3W71ZmcsDq32WvDa+Pp7SwASO3N91TPK0XJHuNyQgsUrM2UUw\n" +
            "    tLNyF3Lh6dUkkmQxlMcKF8ruQFjJuxw8ivwsli/dpCDikjLcB0bRZcVhCx0ksQDC0M3JW10czG/c\n" +
            "    HAlZ4EvqiQFulzDRQddqUqogjjQXK+dnDmn/ALrolJDCRyuMvOq3Mv5CDKFrBKHRnjchdwNhedDL\n" +
            "    C121gnsV3QgenYJIJKDNQlZLB3Hyg5Gx6fXcd9QJHyuKJhfjNjLKfqs/1XZonBI5Vw5NUzo3MpzR\n" +
            "    e6Dt7D8IgRBCuCUSMzZJIhd7c8rtmdohkfzoba4oWyTwMla7Z+4tBryZpJIi30iCeV248zXMawai\n" +
            "    WgCz3WuKJ7XC3am910BgDrAQZIijtmuIFkCwEFBBVC82HJIgp1+qSdlugklZkBkm4cP6IO0rFUog\n" +
            "    Lz8vNfDkenQDfJXdI4MYXE0AuUyxSyNjkbd8WNigjYpHixW+9jhdoaNjW65IGmHJdG11iuD2XYgH\n" +
            "    dSlVCaCDnAY3Je/UA49yVyxwP9zARoJ3W6RsLiXWCf6rDH3nAa/2lB2xtDGBjeBwslgywTazQFx5\n" +
            "    LwTpe3YbArrOwJWGqNxsEA8IOSFkMkmkvs3VHuu8NDRQFAdlxux2/VRPjA9v3fK7eUBERBUREBQo\n" +
            "    iDB7NY0+V5sLXuyZWMcajdVlepwuCcCHJdJG4W/7kEmfLAWWQ5rqavQ");



}
