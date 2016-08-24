package com.android.network.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kince on 16-8-24.
 */
public class NetMonitor extends BroadcastReceiver {

    private static final String TAG = "NetMonitor";
    private static NetMonitor instance;
    private NetObservable observable;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notifyNetState(context);
    }

    public static NetMonitor getInstance() {
        if (instance == null) {
            synchronized (NetMonitor.class) {
                if (instance == null) {
                    instance = new NetMonitor();
                }
            }
        }
        return instance;
    }

    public void initialized(Context context) {
        mContext = context;
        this.observable = new NetObservable(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(this, intentFilter);
    }

    /**
     * 注册一个网络状态观察者
     * @param observer
     */
    public void registerNetworkObserver(NetObserver observer) {
        this.observable.addObserver(observer);
    }

    /**
     * 反注册一个网络状态观察者
     * @param observer
     */
    public void unregisterNetworkObserver(NetObserver observer) {
        mContext .unregisterReceiver(this);
        this.observable.deleteObserver(observer);
    }

    private void notifyNetState(Context context) {
        try {
            NetworkInfo networkInfo = Network.getCurrentActiveNetwork(context);
            if (networkInfo != null) {
                if (!networkInfo.isAvailable()) {
                    this.observable.notifyObservers(new NetObserver.NetAction(false, false, Network.getSubType(context)));
                    return;
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    this.observable.notifyObservers(new NetObserver.NetAction(true, true, Network.getSubType(context)));
                    return;
                }

                this.observable.notifyObservers(new NetObserver.NetAction(true, false, Network.getSubType(context)));
                return;
            }

            this.observable.notifyObservers(new NetObserver.NetAction(false, false, Network.getSubType(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
