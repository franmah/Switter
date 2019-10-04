package com.fmahieu.switter.ModelLayer.models;


/**
 * Status to focus on in Status Activity
 */
public class StatusFocus {

    private static StatusFocus sStatusFocus;

    public static StatusFocus getUserInstance() {
        if (sStatusFocus == null){
            sStatusFocus = new StatusFocus();
        }
        return sStatusFocus;
    }

    private StatusFocus(){
        sStatusFocus = null;
    }

    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }
}
