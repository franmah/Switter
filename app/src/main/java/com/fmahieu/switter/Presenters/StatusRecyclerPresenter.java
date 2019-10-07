package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateFeedLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateHashtagLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateStoryLogic;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.singleton.StatusFocus;

public class StatusRecyclerPresenter {

    private StatusFocus mStatusFocus;

    private UpdateFeedLogic mUpdateFeedLogic;
    private UpdateStoryLogic mUpdateStoryLogic;
    private UpdateHashtagLogic mUpdateHashtagLogic;

    public StatusRecyclerPresenter(){
        mStatusFocus = StatusFocus.getStatusFocusInstance();
        mUpdateStoryLogic = new UpdateStoryLogic();
        mUpdateFeedLogic = new UpdateFeedLogic();
        mUpdateHashtagLogic = new UpdateHashtagLogic();
    }

    public void setStatusFocus(Status status){
        mStatusFocus.setStatus(status);
    }

    public void getFeedNextPage(){
        mUpdateFeedLogic.getFeedNextPage();

    }

    public void getStoryNextPage(){
        mUpdateStoryLogic.getStoryNextPage();

    }

    public void getHashtagNextPage(){
        mUpdateHashtagLogic.getHashtagFeedNextPage();

    }
}
