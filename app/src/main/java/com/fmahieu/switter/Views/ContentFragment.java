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

import com.fmahieu.switter.Presenters.ContentPresenter;
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
    private final String TAG = "__ContentFragment";

    private ContentPresenter mContentPresenter = new ContentPresenter();

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

        setUpWidgets(view);

        // TODO: in another thread:
        // Update each singleton: will request first page to display
        updateContent();

        getFragment();

        return view;
    }

    private void updateContent(){
        // Using each singletons' owner, ContentPresenter will fetch the data for the right user.
        mContentPresenter.updateFeed();
        mContentPresenter.updateStory();
        mContentPresenter.updateFollowing();
        mContentPresenter.updateFollowers();
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
        switch(v.getId()){
            case R.id.feed_contentFragment_TextView:
                setAllShowTabFalse();
                showFeed = true;
                getFragment();
                break;
            case R.id.story_contentFragment_TextView:
                setAllShowTabFalse();
                showStory = true;
                getFragment();
                break;
            case  R.id.following_contentFragment_TextView:
                setAllShowTabFalse();
                showFollowing = true;
                getFragment();
                break;
            case R.id.followers_contentFragment_TextView:
                setAllShowTabFalse();
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
            Bundle bundle = StatusRecyclerFragment.createDisplayFeedBundle(true);
            fragment = new StatusRecyclerFragment();
            fragment.setArguments(bundle);

            fragmentManager.beginTransaction().add(R.id.fragmentContainer_contentFragment_frameLayout, fragment).commit();
        }
        else{
            Bundle bundle = null;

            if(showFeed){
                // tell StatusRecyclerFragment to use feed instance
                bundle = StatusRecyclerFragment.createDisplayFeedBundle(true);
                fragment = new StatusRecyclerFragment();
                fragment.setArguments(bundle);

            }
            else if(showStory){
                bundle = StatusRecyclerFragment.createDisplayFeedBundle(false);
                fragment = new StatusRecyclerFragment();
            }
            else if(showFollowing){
                // let the recycler know it has to show following users
                bundle = FollowRecyclerFragment.createBundle(true);
                fragment = new FollowRecyclerFragment();
            }
            else if(showFollowers){
                bundle = FollowRecyclerFragment.createBundle(false);
                fragment = new FollowRecyclerFragment();

            }

            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_contentFragment_frameLayout, fragment).commit();
        }
    }


}
