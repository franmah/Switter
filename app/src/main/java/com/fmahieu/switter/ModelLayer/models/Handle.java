package com.fmahieu.switter.ModelLayer.models;

import java.util.Objects;

public class Handle {

    private String handle;

    public Handle(String handle){
        this.handle = handle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Handle)) return false;
        Handle handle1 = (Handle) o;
        return Objects.equals(getHandleString(), handle1.getHandleString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHandleString());
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getHandleString(){
        return this.handle;
    }

}
