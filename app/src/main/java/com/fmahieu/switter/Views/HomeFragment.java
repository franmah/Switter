package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
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

import com.fmahieu.switter.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "HomeFragment";

    private FragmentManager fragmentManager = getChildFragmentManager();

    private ImageView mProfilePicture;
    private TextView mLogout;
    private TextView mSearch;
    private TextView mNewTweet;

    private TextView mFeed;
    private TextView mStory;
    private TextView mFollowing;
    private  TextView mFollowers;

    private boolean showFeed = false;
    private boolean showStory = false;
    private boolean showFollowing = false;
    private  boolean showFollowers = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)  {
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
        mProfilePicture = view.findViewById(R.id.profilePicture_homeFragment_ImageView);
        mLogout = view.findViewById(R.id.logout_homeFragment);
        mSearch = view.findViewById(R.id.search_homeFragment);
        mNewTweet = view.findViewById(R.id.newTweet_homeFragment);

        mFeed = view.findViewById(R.id.feed_homeFragment_TextView);
        mStory = view.findViewById(R.id.story_homeFragment_TextView);
        mFollowing = view.findViewById(R.id.following_homeFragment_TextView);
        mFollowers = view.findViewById(R.id.followers_homeFragment_TextView);

        mLogout.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mNewTweet.setOnClickListener(this);

        mFeed.setOnClickListener(this);
        mStory.setOnClickListener(this);
        mFollowing.setOnClickListener(this);
        mFollowers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()){
            case R.id.logout_homeFragment:
                updateSuperFragment(); // will tell MainActivity to switch fragment
                break;
            case R.id.search_homeFragment:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.newTweet_homeFragment:
                intent = new Intent(getActivity(), NewStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.feed_homeFragment_TextView:
                setAllShowTabFalse();
                showFeed = true;
                getFragment();
                break;
            case R.id.story_homeFragment_TextView:
                setAllShowTabFalse();
                showStory = true;
                getFragment();
                break;
            case  R.id.following_homeFragment_TextView:
                setAllShowTabFalse();
                showFollowing = true;
                getFragment();
                break;
            case R.id.followers_homeFragment_TextView:
                setAllShowTabFalse();
                showFollowers = true;
                getFragment();
                break;

        }
    }

    public void updateSuperFragment(){
        Log.i(TAG, "requesting MainActivity to switch fragment");
        Activity mainActivityInstance = getActivity();
        if(mainActivityInstance instanceof MainActivity){
            ((MainActivity) mainActivityInstance).getFragment();
        }
    }

    private void setAllShowTabFalse(){
        showFeed = false;
        showStory = false;
        showFollowing = false;
        showFollowers = false;
    }

    private void getFragment(){
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_mainActivity_FrameLayout);

        if(fragment == null){
            // show the feed by default
            fragment = new FeedFragment();

            fragmentManager.beginTransaction().add(R.id.fragmentContainer_mainActivity_FrameLayout, fragment).commit();
        }
        else{
            if(showFeed){
                fragment = new FeedFragment();
            }
            else if(showStory){
                fragment = new StoryFragment();
            }
            else if(showFollowing){
                //fragment = new FollowingFragment();
            }
            else if(showFollowers){
                //fragment = new FollowersFragment();
            }

            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
    }
}
