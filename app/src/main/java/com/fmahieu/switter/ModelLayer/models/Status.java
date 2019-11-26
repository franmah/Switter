package com.fmahieu.switter.ModelLayer.models;

public class Status {

    public LinkProfilePicture profilePicture;
    public String firstName;
    public String lastName;
    public String handle;
    public String timestamp;
    public String text;
    public LinkAttachmentImage attachedPicture;
    public boolean isImage;

    public Status(LinkProfilePicture profilePicture, String firstName, String lastName, String handle,
                  String date, String text, LinkAttachmentImage picture) {
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.timestamp = date;
        this.text = text;
        this.attachedPicture = picture;
    }

    public void setIsImage(boolean cond){
        isImage = cond;
    }

    public LinkProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getHandle() {
        return handle;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public LinkAttachmentImage getAttachmentPicture() {
        return attachedPicture;
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
                ", timestamp='" + timestamp + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
