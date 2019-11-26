package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class PictureLoader {
    public static final String TAG = "__PictureLoader";

    public static void loadPictureLink(Context context, String url, ImageView imageView){
        Log.i(TAG, "loading attachedPicture (url: " + url + ")");
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }
}
