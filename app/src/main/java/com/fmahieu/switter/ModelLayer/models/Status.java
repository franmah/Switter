package com.fmahieu.switter.ModelLayer.models;

import android.net.Uri;

import java.io.Serializable;

public class Status implements Serializable {

    private Uri profilePicture;
    private String firstName;
    private String lastName;
    private Handle handle;
    private String date;
    private String text;
    private Uri picture;
    private Uri video;

    public Status(Uri profilePicture, String userName, Handle handle, String date, String text,
                  Uri pictures, Uri videos) {
        this.profilePicture = profilePicture;
        this.firstName = userName;
        this.handle = handle;
        this.date = date;
        this.text = text;
        this.picture = pictures;
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

    public Uri getPicture() {
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
                ", picture=" + picture +
                ", video=" + video +
                '}';
    }
}
