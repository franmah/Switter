package com.fmahieu.switter.ModelLayer.models;

public class LinkProfilePicture {
    /**
     * Base link for every attachedPicture uploaded in the server.
     * (LINK + handle + LINK_ENDING = full link to download the attachedPicture)
     */
    private static final String LINK = "https://switterpictures.s3-us-west-2.amazonaws.com/";

    /**
     * When uploading a attachedPicture to the server, the file should have the following string appended
     * at the end.
     */
    private static final String LINK_ENDING = "_profilePicture.txt";

    public String link;

    /**
     * Only need a handle: will add the url and the link ending.
     * @param handle
     */
    public LinkProfilePicture(String handle){
        this.link = LINK + handle + LINK_ENDING;
    }

    public String getLink() {
        return link;
    }
}
