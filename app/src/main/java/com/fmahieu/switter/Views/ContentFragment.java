package com.fmahieu.switter.Views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fmahieu.switter.R;


/**
 * The ContentFragment holds the menu to switch between the feed, story, following and followers
 * It switches between which recycler fragment to show.
 * It also updates the feed, story and user object, this way when switching between fragments (feed, story..
 * the first page is already loaded in the client.
 *
 * Content fragment is used in HomeFragment, ProfileActivity and UserActivity
 */

public class ContentFragment extends Fragment implements View.OnClickListener {
    private static final String HANDLE_TO_DISPLAY =
            "com.fmahieu.switter.views.contentFragment.handleToDisplay";
    private final String TAG = "__ContentFragment";

    private String handleToDisplay; // handle passed to the recyclers to know who's feed to show.

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

        View view = inflater.inflate(R.layout.content_fragment, container, false);

        // get Argument
        handleToDisplay = this.getArguments().getString(HANDLE_TO_DISPLAY);
        Log.i(TAG, "Showing content for " + handleToDisplay);

        setUpWidgets(view);
        getFragment();

        return view;
    }


    private void setUpWidgets(View view){

        mFeed = view.findViewById(R.id.feed_contentFragment_TextView);
        mStory = view.findViewById(R.id.story_contentFragment_TextView);
        mFollowing = view.findViewById(R.id.following_contentFragment_TextView);
        mFollowers = view.findViewById(R.id.followers_contentFragment_TextView);

        mFeed.setOnClickListener(this);
        mStory.setOnClickListener(this);
        mFollowing.setOnClickListener(this);
        mFollowers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setAllShowTabFalse();
        switch(v.getId()){
            case R.id.feed_contentFragment_TextView:
                showFeed = true;
                getFragment();
                break;
            case R.id.story_contentFragment_TextView:
                showStory = true;
                getFragment();
                break;
            case  R.id.following_contentFragment_TextView:
                showFollowing = true;
                getFragment();
                break;
            case R.id.followers_contentFragment_TextView:
                showFollowers = true;
                getFragment();
                break;

        }
    }

    private void setAllShowTabFalse(){
        showFeed = false;
        showStory = false;
        showFollowing = false;
        showFollowers = false;
    }

    private void getFragment(){
        Log.i( TAG, "changing fragment" );

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_contentFragment_frameLayout);

        if(fragment == null){
            // show the feed by default
            Bundle bundle = StatusRecyclerFragment.createDisplayFeedBundle(handleToDisplay);
            fragment = new StatusRecyclerFragment();
            fragment.setArguments(bundle);

            fragmentManager.beginTransaction().add(R.id.fragmentContainer_contentFragment_frameLayout, fragment).commit();
        }
        else{
            Bundle bundle = null;

            if(showFeed){
                // tell StatusRecyclerFragment to use feed instance
                bundle = StatusRecyclerFragment.createDisplayFeedBundle(handleToDisplay);
                fragment = new StatusRecyclerFragment();
                fragment.setArguments(bundle);

            }
            else if(showStory){
                bundle = StatusRecyclerFragment.createDisplayStoryBundle(handleToDisplay);
                fragment = new StatusRecyclerFragment();
            }
            else if(showFollowing){
                // let the recycler know it has to show following followUsers
                bundle = FollowRecyclerFragment.createBundle(true, handleToDisplay);
                fragment = new FollowRecyclerFragment();
            }
            else if(showFollowers){
                bundle = FollowRecyclerFragment.createBundle(false, handleToDisplay);
                fragment = new FollowRecyclerFragment();

            }

            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_contentFragment_frameLayout, fragment).commit();
        }
    }



    public static Bundle createContentFragmentBundle(String handle){
        Bundle bundle = new Bundle();
        bundle.putString( HANDLE_TO_DISPLAY, handle );
        return bundle;
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
