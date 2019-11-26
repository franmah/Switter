package com.fmahieu.switter.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.TxtFileLoaderTask;
import com.fmahieu.switter.R;

import org.w3c.dom.Text;

public class TxtViewActivity  extends AppCompatActivity {

    private static final String LINK_TXT = "the_link" ;
    private TextView view;

    public static Intent newIntent(Context context, String link){
        Intent intent = new Intent(context, TxtViewActivity.class);
        intent.putExtra(LINK_TXT, link);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txt_view_activity);

        view = findViewById(R.id.mainText_txtViewActivity);

        // get link
        String link = getIntent().getStringExtra(LINK_TXT);
        new TxtFileLoaderTask(view, link).execute();

    }
}
