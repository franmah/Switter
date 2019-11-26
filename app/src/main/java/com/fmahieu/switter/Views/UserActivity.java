package com.fmahieu.switter.Views;

// TODO:
// - first for User:
// 1) get a user from the server, it should return a UserResponse with an error attribute.
//
// - Profile
// 1) should it download the most recent version of the profile?
//
// - presenter
// 1) clean up the presenter.


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoader;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoaderTask;
import com.fmahieu.switter.ModelLayer.models.MessageResult;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.UserResult;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
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
    private static final String USER_OBJECT =
            "com.fmahieu.switter.views.UserActivity.userObject";

    private UserActivityPresenter mUserActivityPresenter = new UserActivityPresenter();
    private User user;
    private Profile profile = Profile.getUserInstance(); // TODO: remove the getUserInstace (should be done in one of those functions below)
    private String handleToDisplay;

    private boolean userIsProfile;
    private boolean doesProfileFollowUser; // is the user followed by current profile

    private ImageView mProfilePic;
    private TextView mUserName;
    private TextView mHandle;
    private Button mFollowButton;

    public static Intent newHandleIntent(Context context, String handle){
        Intent intent = new Intent( context, UserActivity.class );
        intent.putExtra( USER_HANDLE, handle );
        return intent;
    }

    public static Intent newUserIntent(Context context, User user){
        UserActivityPresenter userActivityPresenter = new UserActivityPresenter();
        String userJson = userActivityPresenter.getJsonFromUser(user);
        Intent intent = new Intent( context, UserActivity.class);
        intent.putExtra(USER_OBJECT, userJson);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        Log.i(TAG, "Starting activity");

        retrieveUser();
    }

    // TODO: move some of that stuff in the presenter
    // set up either profile or another user
    private void retrieveUser(){

        // check if the user was passed using a handle rather than a User object
        userIsProfile = false;
        String userHandle = getIntent().getStringExtra(USER_HANDLE);

        if(userHandle != null){
            if(!userHandle.equals(Profile.getUserInstance().getHandle())) {
                // TODO: remove, should never be used (from now on user should be downloaded in StatusHolder
                // Get user from server and update UserUnique instance
                // the UserRetriever will go directly to the setUpUserToShow() function
                makeToast("Getting user...");
                handleToDisplay = userHandle;
                //new UserRetrieverTask().execute();
            }
            else{
                userIsProfile = true;
                setUpUserToShow();
            }
        }
        else{ // A user object has been passed to the activity

            String userJson = getIntent().getStringExtra(USER_OBJECT);
            user = mUserActivityPresenter.getUserFromJson(userJson);

            // Is the use the current profile?
            if(user.getHandle().equals(Profile.getUserInstance().getHandle())){
                userIsProfile = true;
            }

            setUpUserToShow();
        }
    }

    private void setUpUserToShow(){
        if(userIsProfile){
            profile = Profile.getUserInstance();
            setTitle(profile.getHandle());
            handleToDisplay = profile.getHandle();
        }
        else{
            // If user doesn't exists or an error came back from the server
            if(user == null){
                finish();
            }

            setTitle(user.getHandle());

            // check if the profile is following the user displayed
            Log.i(TAG, "checking for if following");
            new isFollowingTask().execute();
            handleToDisplay = user.getHandle();
        }

        setUpWidgets();
        getFragment();
    }

    private void setUpWidgets(){
        mProfilePic = findViewById(R.id.profilePic_imageView_userActivity);
        mUserName = findViewById(R.id.userName_textView_userActivity);
        mHandle = findViewById(R.id.handle_textView_userActivity);
        mFollowButton = findViewById(R.id.followButton_button_userActivity);

        if(userIsProfile){

            // hide follow button
            mFollowButton.setVisibility(View.GONE);

            //PictureLoader.loadPictureLink(this, profile.getProfilePictureLink().getLink(), mProfilePic);
            new PictureLoaderTask(mProfilePic, profile.getProfilePictureLink().getLink()).execute();
            mUserName.setText(new String(profile.getFirstName() + " " + profile.getLastName()));
            mHandle.setText(profile.getHandle());
        }
        else{
            // Note: the follow/unfollow button is setup in isFollowingServerResult()
            new PictureLoaderTask(mProfilePic, user.getProfilePicture().getLink()).execute();
            mUserName.setText( new String(user.getFirstName() + " " + user.getLastName()) );
            mHandle.setText( user.getHandle());
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "starting request to follow user");
        new FollowUserTask().execute();
    }

    private void getFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_frameLayout_userActivity);

        if( fragment == null ){
            fragment = new ContentFragment();
            Bundle bundle = ContentFragment.createContentFragmentBundle(handleToDisplay);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_frameLayout_userActivity, fragment ).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_frameLayout_userActivity, fragment ).commit();
        }
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void onUserResult(UserResult response) {
        if(response == null){
            Log.e(TAG, "onUserResult: response came back null");
            makeToast("something went wrong");
            user = null;
            finish();
        }
        else if(response.getError() != null){
            Log.e(TAG, "OnUserResult: server result came back with an error: " + response.getError());
            makeToast(response.getError());
            user = null;
            finish();
        }
        else{
            user = response.getUser();
            setUpUserToShow();
        }

    }

    private void onFollowResult(MessageResult response){
        if(response == null){
            makeToast("Unable to (un)follow user");
        }
        else if(response.getError() != null) {
            makeToast(response.getError());
        }
        else{
            if(doesProfileFollowUser){
                mFollowButton.setText( R.string.followString_button_userActivity);
                doesProfileFollowUser = false;
            }
            else{
                mFollowButton.setText( R.string.unfollowString_button_userActivity );
                doesProfileFollowUser = true;
            }
        }
    }

    private void isFollowingServerResult(String response, boolean isFollowing){
        if(response == null) {
            if (isFollowing) {
                mFollowButton.setText(R.string.unfollowString_button_userActivity);
                doesProfileFollowUser = true;
            } else {
                mFollowButton.setText(R.string.followString_button_userActivity);
                doesProfileFollowUser = false;
            }

            mFollowButton.setOnClickListener(this);
        }
        else{
            makeToast(response);
        }
    }

   /* private class UserRetrieverTask extends AsyncTask<Void, Void, UserResult> {
        @Override
        protected UserResult doInBackground(Void... params){
            Log.i(TAG, "Starting AsyncTask");
            return mUserActivityPresenter.getUserFromServer(handleToDisplay);
        }

        @Override
        protected void onPostExecute(UserResult response){
            // Tell the RecyclerFragment that the content can be loaded.
            onUserResult(response);
        }
    }*/

    private class FollowUserTask extends AsyncTask<Void, Void, MessageResult> {

        @Override
        protected MessageResult doInBackground(Void... params){
            Log.i(TAG, "requesting a follow");
            if(doesProfileFollowUser){
                return mUserActivityPresenter.unFollowUser(user.getHandle());
            }
            else{
                return mUserActivityPresenter.followUser(user.getHandle());
            }
        }

        @Override
        protected void onPostExecute(MessageResult response){
            onFollowResult(response);
        }
    }

    private class isFollowingTask extends AsyncTask<Void, Void, IsFollowingResult> {

        @Override
        protected IsFollowingResult doInBackground(Void... params){
            return mUserActivityPresenter.isProfileFollowingUser(user.getHandle());
        }

        @Override
        protected void onPostExecute(IsFollowingResult result){
            isFollowingServerResult(result.getError(), result.isFollowing());
        }
    }
}
