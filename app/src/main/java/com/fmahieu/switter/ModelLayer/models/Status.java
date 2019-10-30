package com.fmahieu.switter.ModelLayer.models;

import android.net.Uri;

import java.util.List;

public class Status {

    private Uri profilePicture;
    private String firstName;
    private String lastName;
    private Handle handle;
    private String date;
    private List<Hashtag> hashtags;
    private String text;
    private Image picture;
    private Uri video;

    public Status(Uri profilePicture, String userName, Handle handle, String date, String text,
                  Image picture, Uri videos) {
        this.profilePicture = profilePicture;
        this.firstName = userName;
        this.handle = handle;
        this.date = date;
        this.text = text;
        this.picture = picture;
        this.video = videos;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public Handle getHandle() {
        return handle;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public Image getPicture() {
        return picture;
    }

    public Uri getVideo() {
        return video;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Status{" +
                "profilePicture=" + profilePicture +
                ", firstName='" + firstName + '\'' +
                ", handle='" + handle + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
