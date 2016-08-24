package com.android.network.monitor;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kince on 16-8-23.
 * 网络状态被观察者
 */
public class NetworkObservable extends Observable {

    private static final String TAG = "NetworkObservable";
    private Context context;

    public NetworkObservable(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void addObserver(Observer observer) {
        try {
            super.addObserver(observer);
            observer.update(this, new NetworkObserver.NetAction(NetworkManager.getNetworkType(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObservers(Object data) {
        try {
            this.setChanged();
            super.notifyObservers(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
