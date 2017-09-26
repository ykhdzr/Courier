package com.ykhdzr.courier;

import android.support.annotation.NonNull;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by ykhdzr on 9/15/17.
 */

public abstract class Packet<T> {

    private BehaviorSubject<T> behaviorSubject = BehaviorSubject.create();

    private T data;

    private String tag;

    public Packet(String tag) {
        this.tag = tag;
    }

    public Packet(String tag, T data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return provideClass().getSimpleName() + tag;
    }

    @NonNull
    public abstract Class provideClass();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void accept() {
        behaviorSubject.onNext(data);
    }

    public BehaviorSubject<T> getBehaviorSubject() {
        return behaviorSubject;
    }

}
