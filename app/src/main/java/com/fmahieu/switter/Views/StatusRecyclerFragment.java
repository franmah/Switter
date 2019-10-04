package com.fmahieu.switter.Views;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.models.Feed;
import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.Profile;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.StatusFocus;
import com.fmahieu.switter.ModelLayer.models.Story;
import com.fmahieu.switter.Presenters.FeedPresenter;
import com.fmahieu.switter.R;

import java.util.ArrayList;
import java.util.List;


/**
 * The statusRecyclerFragment display statuses from either a feed, story or hashtag fragment
 * It can (through its presenter) get the next page (depending on the settings in the feed/story object)
 **/


public class StatusRecyclerFragment extends Fragment {
    private final String TAG = "__FeedFragment";
    private static final String SHOW_FEED =
            "com.fmahieu.switter.views.StatusRecyclerFragment.showFeed";

    private FeedPresenter feedPresenter = new FeedPresenter();
    private Feed feed;
    private Story story;
    private StatusFocus statusFocus;

    private boolean displayFeed;

    private RecyclerView mFeedRecycler;
    private StatusRecyclerAdapter mStatusRecyclerAdapter;

    public static Bundle createBundle(boolean shouldShowFeed){
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FEED, shouldShowFeed);
        return bundle;
    }

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
        displayFeed = this.getArguments().getBoolean(SHOW_FEED); // if false then the recycler will show a story

        feed = Feed.getFeedInstance();
        story = Story.getStoryInstance();
        statusFocus = StatusFocus.getUserInstance();


        ///////////////////////////////////////
        // TODO: REMOVE:
        // generate fake status

        Status status = new Status(Profile.getUserInstance().getPicture(), "test", new Handle("@handle"),
                "today", "This is a test", Profile.getUserInstance().getPicture(), null);
        List<Status> tmp_test = new ArrayList<>();
        tmp_test.add(status);
        feed.setStatuses(tmp_test);
        story.setStatuses(tmp_test);
        // end of remove
        /////////////////////////////////////////////////

        setUpWidgets(view);

        return view;
    }

    private void setUpWidgets(View v) {
        mFeedRecycler = v.findViewById(R.id.recycler_feedFragment);
        mFeedRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStatusRecyclerAdapter = new StatusRecyclerAdapter();
        mFeedRecycler.setAdapter(mStatusRecyclerAdapter);
    }


    private class StatusRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Status status;

        //private LinearLayout mLinearLayout;

        private ImageView profilePic;
        private TextView profileName;
        private TextView handle;
        private TextView date;
        private TextView text;
        private ImageView attachmentPicture;

        public StatusRecyclerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.status_holder, parent, false));
            Log.i(TAG, "Creating holder");

            // set up widgets
            //mLinearLayout = itemView.findViewById(R.id.linearLayout_statusHolder);
            //mLinearLayout.setOnClickListener(this);

            profilePic = itemView.findViewById(R.id.profilePicture_imageView_statusHolder);
            profileName = itemView.findViewById(R.id.profileName_textView_statusHolder);
            handle = itemView.findViewById(R.id.handle_TextView_statusHolder);
            date = itemView.findViewById(R.id.date_textView_statusHolder);
            text = itemView.findViewById(R.id.text_TextView_statusHolder);
            attachmentPicture = itemView.findViewById(R.id.pictureAttachment_statusHolder);

            // TODO: test:
            text.setOnClickListener(this);

        }

        public void bind(Status status) {
            this.status = status;

            profilePic.setImageURI(status.getPicture());
            profileName.setText(status.getFirstName());
            handle.setText(status.getHandle().getHandleString());
            date.setText(status.getDate());
            text.setText(status.getText());
            if (status.getPicture() != null) attachmentPicture.setImageURI(status.getPicture());

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.text_TextView_statusHolder) {
                // start StatusFActivity
                statusFocus.setStatus(status); //statusFocus instance used by StatusActivity to know which status to show
                Intent intent = new Intent(getActivity(), StatusActivity.class);
                startActivity(intent);
            }
            else{
                // go to UserActivity or ProfileActivity
                if(status.getHandle().equals(Profile.getUserInstance().getHandle())){
                    // if the status'user is profile -> go to ProfileActivity
                }
            }
        }
    }


    private class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerHolder> {
        private List<Status> statues;

        public StatusRecyclerAdapter() {
            Log.i(TAG, "StatusRecyclerAdapter()");
            if(displayFeed) {
                statues = feed.getStatuses();
            }
            else{
                statues = story.getStatuses();
            }
        }

        @NonNull
        @Override
        public StatusRecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StatusRecyclerHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusRecyclerHolder statusRecyclerHolder, int i) {
            statusRecyclerHolder.bind(statues.get(i));
        }

        @Override
        public int getItemCount() {
            Log.i(TAG, "Number of statuses");
            if(statues == null){
                return 0;
            }
            else{
                return statues.size();
            }
        }
    }

}

