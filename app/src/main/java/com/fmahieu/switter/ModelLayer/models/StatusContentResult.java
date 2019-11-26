package com.fmahieu.switter.ModelLayer.models;

import java.util.List;

public class StatusContentResult {

    public  List<Status> statuses;
    public String lastKey;
    public String error;
    public boolean isImage;

    public StatusContentResult(List<Status> statuses, String errorMessage){
        this.statuses = statuses;
        this.error = errorMessage;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public String getError() {
        return error;
    }

    public String getLastKey() {
        return lastKey;
    }
}
