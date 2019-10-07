package com.fmahieu.switter.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.singleton.StatusFocus;
import com.fmahieu.switter.R;

/**
 * Show a status the user clicked on
 */
public class StatusActivity extends AppCompatActivity {

    private StatusFocus mStatusFocus;
    private Status status;

    private ImageView profilePic;
    private TextView profileName;
    private TextView handle;
    private TextView date;
    private TextView text;
    private ImageView attachmentPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_activity);
        setTitle("Status");

        mStatusFocus = StatusFocus.getStatusFocusInstance();
        status = mStatusFocus.getStatus();

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
        profilePic.setImageURI(status.getProfilePicture() );
        profileName.setText( status.getFirstName() );
        handle.setText( status.getHandle().getHandleString() );
        date.setText( status.getDate() );
        text.setText( status.getText() );
        if ( status.getPicture() != null ) attachmentPicture.setImageURI( status.getPicture() );
    }
}
