package com.fmahieu.switter.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fmahieu.switter.ModelLayer.models.Hashtag;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.Presenters.HashtagActivityPresenter;
import com.fmahieu.switter.R;

public class HashtagActivity extends AppCompatActivity {
    private final String TAG = "__HashtagActivity";
    private static final String GET_HANDLE =
            "com.fmahieu.switter.views.hashtagActivity";

    private HashtagActivityPresenter mHashtagActivityPresenter;

    public static Intent newIntent(Context context, String hashtagToPass){
        Intent intent = new Intent(context, HashtagActivity.class);
        intent.putExtra(GET_HANDLE, hashtagToPass);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hashtag_activity);

        String hashtagToShow = getIntent().getStringExtra(GET_HANDLE);


        setTitle(new String("Hashtag - " + hashtagToShow));

        // update singleton HashtagFeed
        mHashtagActivityPresenter = new HashtagActivityPresenter();
        mHashtagActivityPresenter.updateHashtagFeed(hashtagToShow);

        getFragment();

    }

    private void getFragment(){
        // get status recycler
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_hashtagActivity);

        if(fragment == null){
            fragment = new StatusRecyclerFragment();
            Bundle bundle = StatusRecyclerFragment.createDisplayHashtagFeedBundle(true);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_hashtagActivity, fragment).commit();
        }
        else{
            Bundle bundle = StatusRecyclerFragment.createDisplayHashtagFeedBundle(true);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_hashtagActivity, fragment).commit();
        }
    }


}
