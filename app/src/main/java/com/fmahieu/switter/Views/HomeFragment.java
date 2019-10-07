package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.Presenters.HomePresenter;
import com.fmahieu.switter.R;

/**
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "__HomeFragment";

    private HomePresenter mHomePresenter = new HomePresenter();

    private Uri profilePicture;

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


        // Update and get needed user profile
        mHomePresenter.updateProfile();
        profilePicture = mHomePresenter.getProfilePicture();

        setUpViews(view);
        setUpModelInstances();
        getFragment();

        return view;
    }

    private void setUpViews(View view){
        mProfilePicture = view.findViewById(R.id.profilePicture_homeFragment_ImageView);
        mProfileTextView = view.findViewById(R.id.profile_textView_homeFragment);
        mLogout = view.findViewById(R.id.logout_homeFragment);
        mSearch = view.findViewById(R.id.search_homeFragment);
        mNewTweet = view.findViewById(R.id.newTweet_homeFragment);

        mProfilePicture.setImageURI(profilePicture);

        mProfileTextView.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mNewTweet.setOnClickListener(this);
        mProfilePicture.setOnClickListener(this);

    }

    /**
     * Setup singletons owners
     */
    private void setUpModelInstances(){
        // set the owner (as profile) for each singleton.
        // the ContentFragment then can know which data to retrieve.
        mHomePresenter.setUpFeedOwner();
        mHomePresenter.setUpStoryOwner();
        mHomePresenter.setUpFollowingOwner();
        mHomePresenter.setUpFollowersOwner();
    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()){
            case R.id.logout_homeFragment:
                mHomePresenter.logOutUser();
                updateSuperFragment(); // will tell MainActivity to switch to login fragment
                break;
            case R.id.search_homeFragment:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
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
                intent = UserActivity.newIntent(getContext(), Profile.getUserInstance().getHandle().getHandleString());
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
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
    }
}
