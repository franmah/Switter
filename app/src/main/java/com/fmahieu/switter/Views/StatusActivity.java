package com.fmahieu.switter.Views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoader;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.PictureLoaderTask;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.UserResult;
import com.fmahieu.switter.Presenters.StatusActityPresenter;
import com.fmahieu.switter.Presenters.UserActivityPresenter;
import com.fmahieu.switter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Show a status the user clicked on
 */
public class StatusActivity extends AppCompatActivity {

    private final String TAG = "__StatusActivity";
    private static final String SERIALIZED_STATUS =
            "com.fmahieu.switter.views.statusActivity.serializedStatus";

    private Status status;

    private ImageView profilePic;
    private TextView profileName;
    private TextView handle;
    private TextView date;
    private TextView text;
    private ImageView attachmentPicture;

    public static Intent newIntent(Context context, Status statusToPass){
        StatusActityPresenter presenter = new StatusActityPresenter();
        String jsonStatus = presenter.getJsonFromStatus(statusToPass);
        Intent intent = new Intent(context, StatusActivity.class);
        intent.putExtra( SERIALIZED_STATUS, jsonStatus );
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_activity);
        setTitle("Status");

        StatusActityPresenter presenter = new StatusActityPresenter();
        String jsonStatus = getIntent().getStringExtra( SERIALIZED_STATUS );
        status = presenter.getStatusFromJson(jsonStatus);

        setUpWidgets();
        displayStatus();
    }

    private void setUpWidgets(){

        profilePic = findViewById(R.id.profilePicture_imageView_statusActivity);
        profileName = findViewById(R.id.profileName_textView_statusActivity);
        handle = findViewById(R.id.handle_TextView_statusActivity);
        date = findViewById(R.id.date_textView_statusActivity);
        text = findViewById(R.id.text_TextView_statusActivity);
        attachmentPicture = findViewById(R.id.pictureAttachment_statusActivity);
    }

    private void displayStatus(){
        //PictureLoader.loadPictureLink(this, status.getProfilePicture().getLink(), profilePic);
        new PictureLoaderTask(profilePic, status.getProfilePicture().getLink()).execute();
        profileName.setText( status.getFirstName() );
        handle.setText( status.getHandle() );
        date.setText( status.getTimestamp() );
        //text.setText( status.getText() );
        setSpannable( status.getText(), this );

        if ( status.getAttachmentPicture() != null ) {
            PictureLoader.loadPictureLink(
                    this, status.getAttachmentPicture().getLink(), attachmentPicture);
        }
    }

    private void setSpannable(final String message, final Context context){
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

        // set span for handles (mentions)
        for( int i = 0; i < handles.size() - 1; i += 2 ){

            final int first = handles.get(i);
            final int last = handles.get(i + 1);

            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    String tmpHandle = message.substring(first, last);
                    // go to UserActivity
                    //Intent intent = UserActivity.newHandleIntent(context, tmpHandle);
                    //startActivity(intent);
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
                    startActivity( intent );
                }
            }, first, last, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(spannableString);
    }

    private void onUserResult(UserResult result){
        if( result == null ){
            Toast.makeText(this, "user doesn't exist", Toast.LENGTH_SHORT).show();
        }
        else if( result.getError() != null ){
            Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT ).show();
        }
        else{
            Intent intent = UserActivity.newUserIntent(this, result.getUser());
            this.startActivity( intent );
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
