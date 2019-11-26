package com.fmahieu.switter.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.models.StatusContentResult;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.singleton.StatusFocus;
import com.fmahieu.switter.Presenters.StatusRecyclerPresenter;
import com.fmahieu.switter.R;

import java.util.ArrayList;
import java.util.List;


/**
 * The statusRecyclerFragment display statuses from either a feed, story or hashtag fragment
 * It can (through its presenter) get the next page (depending on the settings in the feed/story object)
 **/


public class StatusRecyclerFragment extends Fragment {
    private final String TAG = "__StatusRecyclerFragment";
    private static final String TYPE_TO_DISPLAY =
            "com.fmahieu.switter.views.StatusRecyclerFragment.typeToDisplay";
    private static final String CONTENT_OWNER =
            "com.fmahieu.switter.views.StatusRecyclerFragment.contentOwner";


    private StatusFocus statusFocus;

    //private String lastkey = "begin"; // "begin" is the keyword to let the database know that it should return the first page
    private String lastkey = "";
    private String contentOwner;

    private static final int FEED_TYPE = 0;
    private static final int STORY_TYPE = 1;
    private static final int HASHTAG_TYPE = 2;
    private int displayType = 0;

    private StatusRecyclerPresenter mStatusRecyclerPresenter;

    private RecyclerView mRecycler;
    private StatusRecyclerAdapter mStatusAdapter;

    //private List<Status> statues;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.status_recycler_fragment, container, false);

        displayType = this.getArguments().getInt( TYPE_TO_DISPLAY );
        contentOwner = this.getArguments().getString(CONTENT_OWNER);

        mStatusRecyclerPresenter = new StatusRecyclerPresenter();
        //statusFocus = StatusFocus.getStatusFocusInstance();
        setUpWidgets(view);

        return view;
    }

    private void setUpWidgets(View v) {
        mRecycler = v.findViewById(R.id.recycler_StatusRecyclerFragment);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStatusAdapter = new StatusRecyclerAdapter();
        mRecycler.setAdapter(mStatusAdapter);
    }

    // start a request to the server for more content
    private void startLoadingContentRequest(){
        Log.i(TAG, "Starting AsyncTask");
        makeToast("Loading more content");
        new ContentRequester().execute();
    }

    /**
     * Called once the async task has returned with a result.
     * @param result
     */
    private void loadContentRequest(StatusContentResult result){

        if(result.getError() != null){
            makeToast(result.getError());
        }
        else if(result.getStatuses() == null){
            makeToast("no more content to show");
        }
        else if(result.getStatuses().size() == 0){
            makeToast("no more content to show");
            lastkey = result.getLastKey();
        }
        else{
            mStatusAdapter.loadMoreContent(result.getStatuses());
            lastkey = result.getLastKey();
        }
    }



    private class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusHolder> {
        private List<Status> statues = new ArrayList<>();
        private boolean isLoadingAllowed;

        // the recycler will start getting more content when reaching the size - LOAD_OFFSET's status
        private final int LOAD_OFFSET = 1;


        public StatusRecyclerAdapter() {
            Log.i(TAG, "StatusRecyclerAdapter()");

            // force load the first page
            startLoadingContentRequest();
            isLoadingAllowed = false;

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
            mRecycler.getLayoutManager();

            // Listener to know when to load more content
            mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                        int numStatuses = linearLayoutManager.getItemCount();
                        int lastVisibleStatus = linearLayoutManager.findLastVisibleItemPosition();

                        // if lastKey is null there is no more content to show, don't allow useless
                        // communication with the server
                        if (numStatuses - LOAD_OFFSET <= (lastVisibleStatus) && lastkey != null) {
                            if(isLoadingAllowed) {
                                isLoadingAllowed = false; // prevent multiple loading
                                startLoadingContentRequest();
                            }
                        }
                }
            });
        }

        @NonNull
        @Override
        public StatusHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StatusHolder(layoutInflater, viewGroup, getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull StatusHolder statusRecyclerHolder, int i) {
            statusRecyclerHolder.bind(statues.get(i));
        }

        @Override
        public int getItemCount() {
            return statues.size();
        }


        public void loadMoreContent(final List<Status> newItems){

            if(newItems != null && newItems.size() != 0){
                statues.addAll(newItems);
                mStatusAdapter.notifyDataSetChanged();
                isLoadingAllowed = true;
            }
            else{
                makeToast("something went wrong (sorry)");
            }

        }
    }

    /**
     * BUNDLES TO PASS DATA BETWEEN ACTIVITIES
     */

    public static Bundle createDisplayFeedBundle(String handle){
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_OWNER, handle);
        bundle.putInt( TYPE_TO_DISPLAY, FEED_TYPE );
        return bundle;
    }

    public static Bundle createDisplayStoryBundle(String handle){
        Bundle bundle = new Bundle();
        bundle.putInt( TYPE_TO_DISPLAY, STORY_TYPE );
        bundle.putString( CONTENT_OWNER, handle );
        return bundle;
    }

    public static Bundle createDisplayHashtagFeedBundle(String hashtagToPass){
        Bundle bundle = new Bundle();
        bundle.putInt( TYPE_TO_DISPLAY, HASHTAG_TYPE );
        bundle.putString( CONTENT_OWNER, hashtagToPass );
        return bundle;
    }

    private void makeToast(String message){
        if(message == null){
            Log.e(TAG, "trying to make toast with a null message");
        }
        Log.i(TAG, "making toast: " + message);
        if(getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


    private class ContentRequester extends AsyncTask<Void, Void, StatusContentResult> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected StatusContentResult doInBackground(Void... params){
            Log.i(TAG, "fetchingMoreContent, starting request: owner: " + contentOwner
                                + ", lastKey: " + (lastkey + 1));

            StatusContentResult response = null;

            switch (displayType){
                case FEED_TYPE:
                    response = mStatusRecyclerPresenter.getFeed(contentOwner, lastkey);
                    break;
                case STORY_TYPE:
                    response = mStatusRecyclerPresenter.getStory(contentOwner, lastkey);
                    break;
                case HASHTAG_TYPE:
                    // the content owner is the hashtag to display
                    response = mStatusRecyclerPresenter.getHashtagFeed(contentOwner, lastkey);
                    break;
            }

            return response;
        }

        @Override
        protected void onPostExecute(StatusContentResult response){
            // Tell the RecyclerFragment that the content can be loaded.
            loadContentRequest(response);
        }
    }





}

