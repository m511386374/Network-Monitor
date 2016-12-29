package com.android.network.monitor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kince on 16-8-23.
 * 网络状态观察者
 */
public abstract class NetworkObserver implements Observer {

    @Override
    public void update(Observable observable, Object o) {
        this.onNetworkStateChanged(((NetworkAction) o));
    }

    public abstract void onNetworkStateChanged(NetworkAction action);

}
