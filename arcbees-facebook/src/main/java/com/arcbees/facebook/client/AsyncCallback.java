package com.arcbees.facebook.client;

public interface AsyncCallback<T> {
    void onSuccess(T result);
    
    void onFailure(T result);
}
