package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.StatusContentLogic;
import com.fmahieu.switter.ModelLayer.models.Status;

public class StatusActityPresenter {
    StatusContentLogic mStatusContentLogic = new StatusContentLogic();

    public String getJsonFromStatus(Status status){
        return mStatusContentLogic.getJsonFromStatus(status);
    }

    public Status getStatusFromJson(String jsonStatus) {
        return mStatusContentLogic.getStatusFromJson(jsonStatus);
    }
}
