package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.singleton.Story;

public class UpdateStoryLogic {

    private Story mStory;
    private Handle storyOwner;

    public UpdateStoryLogic(){
        mStory = Story.getStoryInstance();
        storyOwner = mStory.getStoryOwner();
    }

    public void getStory(){

    }

    public void getStoryNextPage(){}
}
