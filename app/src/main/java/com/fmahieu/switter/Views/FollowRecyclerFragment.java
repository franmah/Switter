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

import com.fmahieu.switter.ModelLayer.models.FollowResult;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.Presenters.FollowRecyclerPresenter;
import com.fmahieu.switter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Show list of followers and following for any user
 */
public class FollowRecyclerFragment extends Fragment {
    private final String TAG = "__FollowRecyclerFragment";

    private static final String SHOW_FOLLOWING =
            "com.fmahieu.switter.Views.followRecyclerFragment.showFollowing";
    private static final String HANDLE_TO_DISPLAY =
            "com.fmahieu.switter.Views.followRecyclerFragment.handleToDisplay";

    private RecyclerView followRecycler;
    private FollowRecyclerAdapter followAdapter;

    private boolean displayFollowing;

    private String followOwner;
    private String lastKey = "";

    private FollowRecyclerPresenter mFollowRecyclerPresenter = new FollowRecyclerPresenter();


    public static Bundle createBundle(boolean shouldShowFollowing, String handleToPass){
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FOLLOWING, shouldShowFollowing);
        bundle.putString(HANDLE_TO_DISPLAY, handleToPass);
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.follow_recycler_fragment, container, false);

        displayFollowing = this.getArguments().getBoolean(SHOW_FOLLOWING);
        followOwner = this.getArguments().getString(HANDLE_TO_DISPLAY);

        setUpWidgets(view);

        return view;
    }

    private void setUpWidgets( View view ){
        followRecycler = view.findViewById( R.id.recycler_followRecyclerFragment );
        followRecycler.setLayoutManager( new LinearLayoutManager(getActivity()) );
        followAdapter = new FollowRecyclerAdapter();
        followRecycler.setAdapter( followAdapter );
    }

    private void startLoadingContentRequest(){
        Log.i(TAG, "Starting AsyncTask");
        makeToast("Loading more content");
        new FollowContentRequester().execute();
    }

    private void loadContentRequest(FollowResult result){
        if(result.getErrorMessage() !=  null){
            Log.i(TAG, "Result came back with an error");
            makeToast(result.getErrorMessage());
        }
        else if(result.getUsers() == null){
            Log.i(TAG, "Result came back null");
            makeToast("no more content to show");
        }
        else if(result.getUsers().size() == 0){
            Log.i(TAG, "Result came back with an empty list");
            makeToast("no more content to show");
        }
        else{
            followAdapter.loadMoreContent(result.getUsers());
            lastKey = result.getLastKey();
        }
    }


    private class FollowRecyclerAdapter extends RecyclerView.Adapter<FollowHolder>{
        boolean isLoadingAllowed;
        private List<User> users = new ArrayList<>();

        private final int LOAD_OFFSET = 1;

        public FollowRecyclerAdapter(){

            // force load the first page
            startLoadingContentRequest();
            isLoadingAllowed = false;

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
                    followRecycler.getLayoutManager();
            followRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int numUsers = linearLayoutManager.getItemCount();
                    int lastVisibleUser = linearLayoutManager.findLastVisibleItemPosition();
                    if(numUsers - LOAD_OFFSET <= (lastVisibleUser) && lastKey != null){
                        if(isLoadingAllowed) {
                            isLoadingAllowed = false;
                            startLoadingContentRequest();
                        }
                    }
                }
            });
        }

        @NonNull
        @Override
        public FollowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FollowHolder(layoutInflater, viewGroup, getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull FollowHolder followRecyclerHolder, int i) {
            followRecyclerHolder.bind(users.get(i));

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public void loadMoreContent(final List<User> newUsers){
            if(newUsers != null && newUsers.size() != 0){
                users.addAll(newUsers);
                followAdapter.notifyDataSetChanged();
                isLoadingAllowed = true;
            }
            else{
                makeToast("Something went wrong (sorry)");
            }

        }


    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    private class FollowContentRequester extends AsyncTask<Void, Void, FollowResult> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected FollowResult doInBackground(Void... params){
            Log.i(TAG, "fetchingMoreContent, starting request: owner: " + followOwner
                    + ", lastKey: " + lastKey);

            FollowResult response = null;

            if(displayFollowing){
                response = mFollowRecyclerPresenter.getFollowingNextPage(followOwner, lastKey);
            }
            else{
                response = mFollowRecyclerPresenter.getFollowersNextPage(followOwner, lastKey);
            }

            return response;
        }

        @Override
        protected void onPostExecute(FollowResult response){
            loadContentRequest(response);
        }
    }



}
