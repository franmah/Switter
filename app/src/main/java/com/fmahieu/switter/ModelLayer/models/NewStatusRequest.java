package com.fmahieu.switter.ModelLayer.models;

import java.util.List;

public class NewStatusRequest {


    public Status status;
    public List<String> tags;
    public String token;

    public NewStatusRequest(Status status, List<String> tags, String token) {
        this.status = status;
        this.tags = tags;
        this.token = token;
    }
}
