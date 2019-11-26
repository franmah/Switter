package com.fmahieu.switter.Views;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoaderTask;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.R;

public class FollowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private User user;
    private Context context;

    private LinearLayout holderLayout;
    private ImageView profilePicture;
    private TextView userName;
    private TextView handle;

    public FollowHolder(LayoutInflater inflater, ViewGroup parent, Context context){
        super(inflater.inflate(R.layout.follow_holder, parent, false));

        this.context = context;
        holderLayout = itemView.findViewById(R.id.linearLayout_followHolder);
        profilePicture = itemView.findViewById(R.id.profilePicture_imageView_followHolder);
        userName = itemView.findViewById(R.id.userName_textView_followHolder);
        handle = itemView.findViewById(R.id.handle_textView_followHolder);

        holderLayout.setOnClickListener(this);
    }

    public void bind(User user){
        this.user = user;
        //PictureLoader.loadPictureLink(context, user.getProfilePicture().getLink(), profilePicture);
        Log.i("__TESTING: ", "loading picture for: " + user.getHandle() + ", link: " + user.getProfilePicture().getLink());
        new PictureLoaderTask(profilePicture, user.getProfilePicture().getLink()).execute();
        userName.setText( new String(user.getFirstName() + " " + user.getLastName()) );
        handle.setText( user.getHandle() );
    }

    @Override
    public void onClick(View v) {
        Intent intent = UserActivity.newHandleIntent(context, user.getHandle());
        // TEST:
        intent = UserActivity.newUserIntent(context, user);
        context.startActivity(intent);

    }
}