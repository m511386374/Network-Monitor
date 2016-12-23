package com.android.network.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by kince on 16-8-24.
 */
public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";

    private NetworkObservable mObservable;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notifyNetState(context, intent);
    }

    public NetworkReceiver(NetworkObservable observable) {
        this.mObservable = observable;
    }

    /**
     * 注册一个网络状态观察者
     *
     * @param observer
     */
    public void registerNetworkObserver(NetworkObserver observer) {
        synchronized (mObservable) {
            this.mObservable.addObserver(observer);
        }
    }

    /**
     * 反注册一个网络状态观察者
     *
     * @param observer
     */
    public void unregisterNetworkObserver(NetworkObserver observer) {
        synchronized (mObservable) {
            this.mObservable.deleteObserver(observer);
        }
    }

    private void notifyNetState(Context context, Intent intent) {
        if (null == intent) {
            return;
        }
        if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            return;
        }

        try {
            NetworkType networkType = NetworkManager.getNetworkType(context);
            WifiSignalLevel wifiSignalLevel = NetworkManager.getWifiSignalLevel(context);
            this.mObservable.notifyObservers(new NetworkAction(networkType, wifiSignalLevel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
