package com.android.network.monitor;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kince on 16-8-23.
 * 网络状态被观察者
 */
public class NetworkObservable extends Observable {

    private Context context;

    public NetworkObservable(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void addObserver(Observer observer) {
        try {
            super.addObserver(observer);
            // 刚注册就更新一下网络状况
            NetworkType networkType = NetworkManager.getNetworkType(context);
            WifiSignalLevel wifiSignalLevel = NetworkManager.getWifiSignalLevel(context);
            observer.update(this, new NetworkAction(networkType, wifiSignalLevel));
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
