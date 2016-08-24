package com.android.network.monitor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kince on 16-8-23.
 * 网络状态观察者
 */
public abstract class NetworkObserver implements Observer {

    private static final String TAG = "NetworkObserver";

    @Override
    public void update(Observable observable, Object o) {
        this.onNetworkStateChanged(((NetAction) o));
    }

    public abstract void onNetworkStateChanged(NetAction action);

    public static class NetAction {
        private NetworkType type;

        public NetAction( NetworkType type) {
            super();
            this.type = type;
        }

        public NetworkType getType() {
            return type;
        }

        public void setType(NetworkType type) {
            this.type = type;
        }
    }

}
