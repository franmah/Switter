package com.fmahieu.switter.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.models.singleton.Feed;
import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.singleton.HashtagFeed;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.singleton.StatusFocus;
import com.fmahieu.switter.ModelLayer.models.singleton.Story;
import com.fmahieu.switter.Presenters.FeedPresenter;
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
    private static final String SHOW_FEED =
            "com.fmahieu.switter.views.StatusRecyclerFragment.showFeed";
    private static final String SHOW_HASHTAG_FEED =
            "com.fmahieu.switter.views.StatusRecyclerFragment.showHashtagFeed";

    private FeedPresenter feedPresenter = new FeedPresenter();
    private Feed feed;
    private Story story;
    private HashtagFeed hashtagFeed;
    private StatusFocus statusFocus;

    private boolean displayFeed;
    private boolean displayHashtagFeed;

    private StatusRecyclerPresenter mStatusRecyclerPresenter;

    private RecyclerView mFeedRecycler;
    private StatusRecyclerAdapter mStatusRecyclerAdapter;

    public static Bundle createDisplayFeedBundle(boolean shouldShowFeed){
        Bundle bundle = new Bundle();
        bundle.putBoolean( SHOW_FEED, shouldShowFeed );
        bundle.putBoolean( SHOW_HASHTAG_FEED, false );
        return bundle;
    }

    public static Bundle createDisplayHashtagFeedBundle(boolean shouldShowHashtagFeed){
        Bundle bundle = new Bundle();
        bundle.putBoolean( SHOW_HASHTAG_FEED, shouldShowHashtagFeed );
        bundle.putBoolean( SHOW_FEED, false ); // don't show the feed.
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

        displayFeed = this.getArguments().getBoolean( SHOW_FEED );
        displayHashtagFeed = this.getArguments().getBoolean( SHOW_HASHTAG_FEED );

        mStatusRecyclerPresenter = new StatusRecyclerPresenter();

        // Each singleton should have been updated by the activity/fragment calling this recycler
        feed = Feed.getFeedInstance();
        story = Story.getStoryInstance();
        hashtagFeed = HashtagFeed.getInstance();
        statusFocus = StatusFocus.getStatusFocusInstance();


        ///////////////////////////////////////
        // TODO: REMOVE:
        // generate fake status
        /*

        Status status = new Status(Profile.getUserInstance().getPicture(), "test", new Handle("@handle"),
                "today", "test @someHandle #test #awesome @user another ", Profile.getUserInstance().getPicture(), null);
        List<Status> tmp_test = new ArrayList<>();
        tmp_test.add(status);
        feed.setStatuses(tmp_test);

        status = new Status(Profile.getUserInstance().getPicture(), "STORY", new Handle("@STORY"),
                "today", "STORY", Profile.getUserInstance().getPicture(), null);
        List<Status> tmp_test1 = new ArrayList<>();
        tmp_test1.add(status);
        story.setStatuses(tmp_test1);

        status = new Status(Profile.getUserInstance().getPicture(), "HASHTAG", new Handle("@hashtag"),
                "today", "HASHTAG!!!!!!!", Profile.getUserInstance().getPicture(), null);
        List<Status> tmp_test2 = new ArrayList<>();
        tmp_test2.add(status);
        hashtagFeed.setStatuses(tmp_test2);
        */
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

        private LinearLayout mLinearLayout;

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

            profilePic = itemView.findViewById(R.id.profilePicture_imageView_statusHolder);
            profileName = itemView.findViewById(R.id.profileName_textView_statusHolder);
            handle = itemView.findViewById(R.id.handle_TextView_statusHolder);
            date = itemView.findViewById(R.id.date_textView_statusHolder);
            text = itemView.findViewById(R.id.text_TextView_statusHolder);
            attachmentPicture = itemView.findViewById(R.id.pictureAttachment_statusHolder);

            // TODO: test:
            text.setOnClickListener(this);
            profileName.setOnClickListener(this);
            handle.setOnClickListener(this);
            profilePic.setOnClickListener(this);


            mLinearLayout = itemView.findViewById(R.id.linearLayout_statusHolder);
            mLinearLayout.setOnClickListener(this);

        }

        public void bind(Status status) {
            this.status = status;

            profilePic.setImageBitmap(status.getPicture().getBitmapImage());
            profileName.setText(new String(status.getFirstName()+ status.getLastName()));
            handle.setText(status.getHandle().getHandleString());
            date.setText(status.getDate());
            //text.setText(status.getText());
            if (status.getPicture() != null) attachmentPicture.setImageBitmap(status.getPicture().getBitmapImage());
            setSpanFromMessage(status.getText());

        }

        // find hashtags and handles and set them clickable
        private void setSpanFromMessage(final String message){
            List<Integer> handles = new ArrayList<>();
            List<Integer> hashtags = new ArrayList<>();

            // parse string to find handles and hashtags
            for(int i = 0; i < message.length(); i++){

                if(message.charAt(i) == '@'){
                    handles.add(i); // first index of the handle
                    while(message.charAt(i) != ' ') {
                        i++;
                        if( i == message.length() ){ break; }
                    }
                    handles.add(i); // end index of the handle

                }
                else if( message.charAt(i) == '#'){

                    hashtags.add(i);
                    while ( message.charAt(i) != ' ' ){
                        i++;
                        if( i == message.length() ){ break; }
                    }
                    hashtags.add(i) ;
                }

            }

            SpannableString spannableString = new SpannableString(message);

            // set span fo handles
            for( int i = 0; i < handles.size() - 1; i += 2 ){

                final int first = handles.get(i);
                final int last = handles.get(i + 1);

                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                        String tmpHandle = message.substring(first, last);
                        // go to UserActivity
                        Intent intent = UserActivity.newIntent(getContext(), tmpHandle);
                        startActivity(intent);

                    }
                }, first, last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            // set span for hashtags
            for( int i = 0; i < hashtags.size() - 1; i += 2 ){
                final int first = hashtags.get( i );
                final int last = hashtags.get( i + 1  );
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                        String tmpHashtag = message.substring( first, last );
                        Intent intent = HashtagActivity.newIntent( getContext(), tmpHashtag );
                        startActivity(intent);

                    }
                }, first, last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            text.setMovementMethod(LinkMovementMethod.getInstance());
            text.setText(spannableString);
        }


        @Override
        public void onClick(View v) {
            int id = v.getId();

            if( id == R.id.profilePicture_imageView_statusHolder ||
                id == R.id.profileName_textView_statusHolder ||
                id == R.id.handle_TextView_statusHolder ) {

                // go to UserActivity if profile pic, name or handle is touched
                Intent intent = UserActivity.newIntent(getActivity(), status.getHandle().getHandleString());
                startActivity(intent);
            }
            else{
                // TODO: modify to set everything else clickable
                // start StatusActivity
                 mStatusRecyclerPresenter.setStatusFocus(status); //statusFocus instance used by StatusActivity to know which status to show
                Intent intent = new Intent(getActivity(), StatusActivity.class);
                startActivity(intent);
            }
        }
    }

    private class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerHolder> {
        private List<Status> statues;

        public StatusRecyclerAdapter() {
            Log.i(TAG, "StatusRecyclerAdapter()");

            if( displayFeed ) {
                statues = feed.getStatuses();
            }
            else if ( displayHashtagFeed ){
                statues = hashtagFeed.getStatuses();
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

