package com.fmahieu.switter.ModelLayer.models.httpModel;

public class JsonHolder {

    private static JsonHolder mJsonHolder;

    public static JsonHolder getInstance(){
        if(mJsonHolder == null){
            mJsonHolder = new JsonHolder();
        }
        return mJsonHolder;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
