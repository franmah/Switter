package com.fmahieu.switter.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.singleton.Followers;
import com.fmahieu.switter.ModelLayer.models.singleton.Following;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
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

    private RecyclerView followRecycler;
    private FollowRecyclerAdapter followAdapter;

    private boolean displayFollowing;

    private Following mFollowing;
    private Followers mFollowers;


    public static Bundle createBundle(boolean shouldShowFollowing){
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FOLLOWING, shouldShowFollowing);
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

        // TODO: REMOVE FAKE DATA:
        // will only show profile
        Profile profile = Profile.getUserInstance();
        User user = new User("Mister", "following", new Handle("@following"), profile.getPicture());
        List<User> tmpUserList = new ArrayList<>();
        tmpUserList.add(user);
        Following.getFollowingInstance().setUsers(tmpUserList);

        User user2 = new User("Mister", "Follower", new Handle("@follower"), profile.getPicture());
        List<User> tmpUserList2 = new ArrayList<>();
        tmpUserList2.add(user2);
        Followers.getFollowersInstance().setUsers(tmpUserList2);
        // END OF DATA TO REMOVE


        mFollowing = Following.getFollowingInstance();
        mFollowers = Followers.getFollowersInstance();

        setUpWidgets(view);

        return view;
    }

    private void setUpWidgets( View view ){
        followRecycler = view.findViewById( R.id.recycler_followRecyclerFragment );
        followRecycler.setLayoutManager( new LinearLayoutManager(getActivity()) );
        followAdapter = new FollowRecyclerAdapter();
        followRecycler.setAdapter( followAdapter );
    }


    private class FollowRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private User user;

        private LinearLayout holderLayout;
        private ImageView profilePicture;
        private TextView userName;
        private TextView handle;

        public FollowRecyclerHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.follow_holder, parent, false));

            holderLayout = itemView.findViewById(R.id.linearLayout_followHolder);
            profilePicture = itemView.findViewById(R.id.profilePicture_imageView_followHolder);
            userName = itemView.findViewById(R.id.userName_textView_followHolder);
            handle = itemView.findViewById(R.id.handle_textView_followHolder);

            holderLayout.setOnClickListener(this);
        }

        public void bind(User user){
            this.user = user;
            profilePicture.setImageURI( user.getProfilePicture() );
            userName.setText( new String(user.getFirstName() + user.getLastName()) );
            handle.setText( user.getHandle().getHandleString() );
        }

        @Override
        public void onClick(View v) {
            Intent intent = UserActivity.newIntent(getContext(), user.getHandle().getHandleString());
            startActivity(intent);

        }
    }

    private class FollowRecyclerAdapter extends RecyclerView.Adapter<FollowRecyclerHolder>{
        private List<User> users;

        public FollowRecyclerAdapter(){
            if(displayFollowing){
                users = mFollowing.getUsers();
            }
            else{
                users = mFollowers.getUsers();
            }
        }

        @NonNull
        @Override
        public FollowRecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FollowRecyclerFragment.FollowRecyclerHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowRecyclerFragment.FollowRecyclerHolder followRecyclerHolder, int i) {
            followRecyclerHolder.bind(users.get(i));

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

    }
}
