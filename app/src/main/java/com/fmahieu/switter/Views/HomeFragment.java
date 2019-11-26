package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoader;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoaderTask;
import com.fmahieu.switter.ModelLayer.models.MessageResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.Presenters.HomePresenter;
import com.fmahieu.switter.R;

/**
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "__HomeFragment";

    private HomePresenter mHomePresenter = new HomePresenter();

    private ImageView mProfilePicture;
    private TextView mProfileTextView;
    private TextView mLogout;
    private TextView mSearch;
    private TextView mNewTweet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        setUpViews(view);
        getFragment();

        return view;
    }

    private void setUpViews(View view){
        mProfilePicture = view.findViewById(R.id.profilePicture_homeFragment_ImageView);
        mProfileTextView = view.findViewById(R.id.profile_textView_homeFragment);
        mLogout = view.findViewById(R.id.logout_homeFragment);
        mSearch = view.findViewById(R.id.search_homeFragment);
        mNewTweet = view.findViewById(R.id.newTweet_homeFragment);

        Log.i(TAG, "loading attachedPicture, profile link: " + Profile.getUserInstance().getProfilePictureLink().getLink());
        //PictureLoader.loadPictureLink(
        //        getContext(), Profile.getUserInstance().getProfilePictureLink().getLink(), mProfilePicture);
        new PictureLoaderTask(mProfilePicture, Profile.getUserInstance().getProfilePictureLink().getLink()).execute();

        mProfileTextView.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mNewTweet.setOnClickListener(this);
        mProfilePicture.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()){
            case R.id.logout_homeFragment:
                new LogOutUserTask().execute();
                break;
            case R.id.search_homeFragment:
                //intent = new Intent(getActivity(), SearchActivity.class);
                //startActivity(intent);
                break;
            case R.id.newTweet_homeFragment:
                intent = new Intent(getActivity(), NewStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.profilePicture_homeFragment_ImageView:
                intent = new Intent(getActivity(), UpdateProfilePictureActivity.class);
                startActivity(intent);
                break;
            case R.id.profile_textView_homeFragment:
                intent = UserActivity.newHandleIntent(getContext(), Profile.getUserInstance().getHandle());
                startActivity(intent);
        }
    }

    public void updateSuperFragment(){
        // is user logout MainActivity will switch to LoginFragment
        Log.i(TAG, "requesting MainActivity to switch fragment");

        Activity mainActivityInstance = getActivity();
        if(mainActivityInstance instanceof MainActivity){
            ((MainActivity) mainActivityInstance).getFragment();
        }
    }

    private void getFragment(){
        // get contentFragment
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_homeFragment_FrameLayout);

        if(fragment == null){
            fragment = new ContentFragment();
            Bundle bundle = ContentFragment.createContentFragmentBundle(Profile.getUserInstance().getHandle());
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void onLogOutUserResult(MessageResult result)   {
        if(result.getError() == null){
            updateSuperFragment();
        }
        else{
            makeToast(result.getError());
        }
    }

    private class LogOutUserTask extends AsyncTask<Void, Void, MessageResult> {

        @Override
        protected MessageResult doInBackground(Void... params){
            Log.i(TAG, "logging user out");
            return mHomePresenter.logOutUser();
        }

        @Override
        protected void onPostExecute(MessageResult result){
            onLogOutUserResult(result);
        }
    }
}
