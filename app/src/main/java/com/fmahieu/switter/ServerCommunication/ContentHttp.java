package com.fmahieu.switter.ServerCommunication;

public class ContentHttp {

    private final String TAG = "__ContentHttp";
    private final String PAGE_SIZE = "5";

    ConnectionHttp mConnectionHttp = new ConnectionHttp();

    // Exceptions are caught in the app logic
    public String getFeedFromServer(String handle, String lastKey, String token) throws Exception  {

        String url = "feed/" + handle + "?lastKey=" + lastKey +
                "&pageSize=" + PAGE_SIZE + "&token=" + token;
        return sendRequest(url);
    }

    public String getStoryFromServer(String handle, String lastKey, String token) throws Exception {
        String url = "story/" + handle + "?lastKey=" + lastKey +
                "&pageSize=" + PAGE_SIZE + "&token=" + token;
        return sendRequest(url);
    }

    public String getHashtagFeedFromServer(String tag, String lastKey, String token) throws Exception{
        tag = tag.substring(1);
        String url = "hashtag?lastKey=" + lastKey + "&tag=" + tag +
                "&pageSize=" + PAGE_SIZE;

        //String newUrl = "hashtag/" + tag + "?lastKey=" + lastKey + "&pageSize=" + PAGE_SIZE;
        return sendRequest(url);
    }


    public String getFollowingFromServer(String handle, String lastKey, String token)throws Exception{
        String url = "following/" + handle + "?lastKey=" + lastKey +
                "&pageSize=" + PAGE_SIZE + "&token=" + token;
        return sendRequest(url);
    }

    public String getFollowersFromServer(String handle, String lastKey, String token) throws Exception{
        String url = "followers/" + handle + "?lastKey=" + lastKey +
                "&pageSize=" + PAGE_SIZE + "&token=" + token;
        return sendRequest(url);
    }

    private String sendRequest(String url) throws Exception {
        //url = "story/@myHandle?lastKey=&pageSize=10&token=1c053d77-e57d-46eb-a8b7-bc47b43fb0c4";
        return mConnectionHttp.sendRequest(url, "GET", null, null);
    }


}
