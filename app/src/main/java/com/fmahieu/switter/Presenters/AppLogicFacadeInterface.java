package com.fmahieu.switter.Presenters;

public interface AppLogicFacadeInterface {


    /** LoginLogic **/
    public void connectUser();
    public void signUpUser();
    public void logUserOut();

    /** UpdateProfileLogic **/
    public void setNewProfilePicture(boolean replaceProfilePicture);
    public void followUser();
    public void unfollowUser();
    public void updateProfile();

    /** UpdateUniqueUserLogic **/
    public void updaateUniqueUserInfo();

    /** NewStatusLogic **/
    public void sendNewStatus();

    /** UpdateFeedLogic **/
    public void getFeed();
    public void getFeedNextPage();

    /** UpdateStoryLogic **/
    public void getStory();
    public void getStoryNextPage();

    /** UpdateHashtagFeedLogic **/
    public void getHashtagFeed();
    public void getHashtagFeedNextPage();

    /** UpdateFollowingLogic **/
    public void getFollowing();
    public void getFollowingNextPage();

    /** UpdateFollowersLogic **/
    public void getFollowers();
    public void getFollowersNextPage();


}
