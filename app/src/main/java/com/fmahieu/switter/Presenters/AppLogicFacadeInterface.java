package com.fmahieu.switter.Presenters;

public interface AppLogicFacadeInterface {


    /** LoginLogic **/
    public void connectUser();
    public void signUpUser();
    public void logUserOut();

    /** ProfileLogic **/
    public void setNewProfilePicture(boolean replaceProfilePicture);
    public void followUser();
    public void unfollowUser();
    public void updateProfile();

    /** UniqueUserLogic **/
    public void updateUniqueUserInfo();

    /** NewStatusLogic **/
    public void sendNewStatus();

    /** StatusContentLogic **/
    public void getFeed();
    public void getFeedNextPage();


    /** UpdateHashtagFeedLogic **/
    public void getHashtagFeed();
    public void getHashtagFeedNextPage();


    /** FollowLogic **/
    public void getFollowers();
    public void getFollowersNextPage();


}
