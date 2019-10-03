package com.fmahieu.switter.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmahieu.switter.Presenters.FeedPresenter;
import com.fmahieu.switter.R;

public class FeedFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "FeedFragment";

    private FeedPresenter FeedPresenter = new FeedPresenter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        return view;
    }

    @Override
    public void onClick(View v) {
        // feedPresenter.getStatus();
        // start statusactivity
    }
}
