package com.android.network.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kince on 16-8-24.
 */
public class NetworkMonitor extends BroadcastReceiver {

    private static final String TAG = "NetworkMonitor";

    private NetworkObservable observable;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notifyNetState(context);
    }

    public NetworkMonitor(Context context) {
        this.observable = new NetworkObservable(context);
    }

    /**
     * 注册一个网络状态观察者
     *
     * @param observer
     */
    public void registerNetworkObserver(NetworkObserver observer) {
        this.observable.addObserver(observer);
    }

    /**
     * 反注册一个网络状态观察者
     *
     * @param observer
     */
    public void unregisterNetworkObserver(NetworkObserver observer) {
        this.observable.deleteObserver(observer);
    }

    private void notifyNetState(Context context) {
        try {
            NetworkType networkType = NetworkManager.getNetworkType(context);
            WifiSignalLevel wifiSignalLevel = NetworkManager.getWifiSignalLevel(context);
            this.observable.notifyObservers(new NetworkObserver.NetAction(networkType, wifiSignalLevel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
