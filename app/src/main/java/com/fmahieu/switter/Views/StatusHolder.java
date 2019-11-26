package com.fmahieu.switter.Views;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoader;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoaderTask;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.TxtFileLoaderTask;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.UserResult;
import com.fmahieu.switter.Presenters.UserActivityPresenter;
import com.fmahieu.switter.R;

import java.util.ArrayList;
import java.util.List;

public class StatusHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Context context;
    private Status status;
    private User statusUser;

    private LinearLayout mLinearLayout;

    private ImageView profilePic;
    private TextView profileName;
    private TextView handle;
    private TextView date;
    private TextView text;
    private ImageView attachmentPicture;
    private TextView txtAttachment;

    public StatusHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.status_holder, parent, false));
        //Log.i(TAG, "Creating holder");

        // set up widgets
        this.context = context;

        profilePic = itemView.findViewById(R.id.profilePicture_imageView_statusHolder);
        profileName = itemView.findViewById(R.id.profileName_textView_statusHolder);
        handle = itemView.findViewById(R.id.handle_TextView_statusHolder);
        date = itemView.findViewById(R.id.date_textView_statusHolder);
        text = itemView.findViewById(R.id.text_TextView_statusHolder);
        attachmentPicture = itemView.findViewById(R.id.pictureAttachment_statusHolder);
        txtAttachment = itemView.findViewById(R.id.txtLink_textView_statusHolder);

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

        //PictureLoader.loadPictureLink(context, status.getProfilePicture().getLink(), profilePic);
        new PictureLoaderTask(profilePic, status.getProfilePicture().getLink()).execute();
        profileName.setText(new String(status.getFirstName()+ " " + status.getLastName()));
        handle.setText(status.getHandle());
        date.setText(status.getTimestamp());

        if (status.getAttachmentPicture() != null) {
            if(isTxtFile(status.getAttachmentPicture().getLink())) {
                txtAttachment.setText(status.getAttachmentPicture().getLink());
                txtAttachment.setOnClickListener(this);
            }
            else {
                PictureLoader.loadPictureLink(context, status.getAttachmentPicture().getLink(), attachmentPicture);
            }
        }
        setSpanFromMessage(status.getText());

        statusUser = new User(status.getFirstName(), status.getLastName(), status.getHandle(),
                status.getProfilePicture());

    }

    private boolean isTxtFile(String link){
        if(link.contains(".txt")){
            return true;
        }
        else{
            return false;
        }
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
                    //Intent intent = UserActivity.newHandleIntent(context, tmpHandle);
                    //context.startActivity( intent );

                    new UserRetrieverTask(tmpHandle).execute();

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
                    Intent intent = HashtagActivity.newIntent( context, tmpHashtag );
                   context.startActivity( intent );

                }
            }, first, last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(spannableString);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.txtLink_textView_statusHolder){
            Intent intent = TxtViewActivity.newIntent(context, status.getAttachmentPicture().getLink());
            context.startActivity(intent);
        }
        else if( id == R.id.profilePicture_imageView_statusHolder ||
                id == R.id.profileName_textView_statusHolder ||
                id == R.id.handle_TextView_statusHolder ) {

            // go to UserActivity if profile pic, name or handle is touched
            //Intent intent = UserActivity.newHandleIntent(context, statusUser.getHandle());
            //context.startActivity( intent );

            // download user first then switch to UserActivity
            new UserRetrieverTask(statusUser.getHandle()).execute();
        }
        else if( id == R.id.text_TextView_statusHolder){
            // TODO: doesn't do anything right now:
            // if user click on a mention of a user that doesn't exist
            // it will start a StatusFocus activity (which we don't want)
        }
        else{
            // TODO: modify to set everything else clickable
            // start StatusActivity
            Intent intent = StatusActivity.newIntent(context, status);
            context.startActivity( intent );
        }
    }

    private void onUserResult(UserResult result){
        if( result == null ){
            Toast.makeText(context, "user doesn't exist", Toast.LENGTH_SHORT).show();
        }
        else if( result.getError() != null ){
            Toast.makeText(context, result.getError(), Toast.LENGTH_SHORT ).show();
        }
        else{
            Intent intent = UserActivity.newUserIntent(context, result.getUser());
            context.startActivity( intent );
        }
    }

    private class UserRetrieverTask extends AsyncTask<Void, Void, UserResult> {
        String handle;

        UserRetrieverTask(String handle){
            this.handle = handle;
        }

        @Override
        protected UserResult doInBackground(Void... params){
            //Log.i(TAG, "Starting AsyncTask");
            return new UserActivityPresenter().getUserFromServer(handle);
        }

        @Override
        protected void onPostExecute(UserResult response){
            // Tell the RecyclerFragment that the content can be loaded.
            onUserResult(response);
        }
    }
}