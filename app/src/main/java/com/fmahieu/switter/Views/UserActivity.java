package com.fmahieu.switter.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ModelLayer.models.singleton.UserUnique;
import com.fmahieu.switter.Presenters.UserActivityPresenter;
import com.fmahieu.switter.R;

/**
 * Show a user's profile (Current user and any other user)
 * will update singletons (Feed, Story, Following/ers, profile (or UserUnique))
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "__UserActivity";
    private static final String USER_HANDLE =
            "com.fmahieu.switter.views.UserActivity.userHandle";

    private UserActivityPresenter mUserActivityPresenter = new UserActivityPresenter();
    private UserUnique user;
    private Profile profile;

    private boolean userIsProfile;
    private boolean doesProfileFollowUser; // is the user followed by current profile

    private ImageView mProfilePic;
    private TextView mUserName;
    private TextView mHandle;
    private TextView mNumFollowers;
    private TextView mNumFollowing;
    private Button mFollowButton;

    public static Intent newIntent(Context context, String handle){
        Intent intent = new Intent( context, UserActivity.class );
        intent.putExtra( USER_HANDLE, handle );
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        Log.i(TAG, "Starting activity");


        // get handle of user to display
        String userHandle = getIntent().getStringExtra(USER_HANDLE);
        setTitle(userHandle);

        // TODO: remove this: will always show the user profile
        userHandle = Profile.getUserInstance().getHandle().getHandleString();

        // set owners of singletons (and update either Profile or UserUnique)
        mUserActivityPresenter.updateUserInfo( userHandle );

        // check if user to show is current profile
        setUpUserToShow(userHandle);

        setUpWidgets();
        getFragment();

    }

    // set up either profile or another user
    private void setUpUserToShow(String userHandle){
        userIsProfile = false;
        doesProfileFollowUser = false;

        if(userHandle.equals(Profile.getUserInstance().getHandle().getHandleString())){
            userIsProfile = true;
            // update profile to show current information
            mUserActivityPresenter.updateUserInfo(Profile.getUserInstance().getHandle().getHandleString());
            profile = Profile.getUserInstance();
        }
        else{
            mUserActivityPresenter.updateUserInfo(userHandle);
            user = UserUnique.getUserInstance();
            doesProfileFollowUser = mUserActivityPresenter.isProfileFollowingUser(user.getHandle());
        }
    }

    private void setUpWidgets(){
        mProfilePic = findViewById(R.id.profilePic_imageView_userActivity);
        mUserName = findViewById(R.id.userName_textView_userActivity);
        mHandle = findViewById(R.id.handle_textView_userActivity);
        mNumFollowers = findViewById(R.id.followersNum_textView_userActivity);
        mNumFollowing = findViewById(R.id.followingNum_textView_userActivity);
        mFollowButton = findViewById(R.id.followButton_button_userActivity);

        if(userIsProfile){
            // hide follow button
            mFollowButton.setVisibility(View.GONE);

            mProfilePic.setImageBitmap(profile.getPicture().getBitmapImage());
            mUserName.setText(new String(profile.getFirstName() + " " + profile.getLastName()));
            mHandle.setText(profile.getHandle().getHandleString());
            mNumFollowers.setText( String.valueOf(profile.getNumFollowers()) );
            mNumFollowing.setText( String.valueOf(profile.getNumFollowing()) );
        }
        else{

            // set text on button
            if(doesProfileFollowUser){
                mFollowButton.setText(R.string.unfollowString_button_userActivity);
            }
            else{
                mFollowButton.setText( R.string.followString_button_userActivity );
            }

            mFollowButton.setOnClickListener( this );

            mProfilePic.setImageURI( user.getPicture());
            mUserName.setText( new String(user.getFirstName() + " " + profile.getLastName()) );
            mHandle.setText( user.getHandle().getHandleString());
            mNumFollowers.setText( String.valueOf(user.getNumFollowers()) );
            mNumFollowing.setText( String.valueOf(user.getNumFollowing()) );
        }
    }

    @Override
    public void onClick(View v) {
        if(doesProfileFollowUser){
            mFollowButton.setText( R.string.followString_button_userActivity);
            mUserActivityPresenter.unFollowUser();
        }
        else{
            mFollowButton.setText( R.string.unfollowString_button_userActivity );
            mUserActivityPresenter.followUser();
        }

    }

    private void getFragment(){
        // owners of the feed, story.. to show is in each singleton.

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_frameLayout_userActivity);

        if( fragment == null ){
            fragment = new ContentFragment();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_frameLayout_userActivity, fragment ).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_frameLayout_userActivity, fragment ).commit();
        }
    }
}
