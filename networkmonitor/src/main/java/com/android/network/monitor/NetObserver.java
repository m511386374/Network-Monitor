package com.android.network.monitor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kince on 16-8-23.
 * 网络状态观察者
 */
public abstract class NetObserver implements Observer {

    private static final String TAG = "NetObserver";

    @Override
    public void update(Observable observable, Object o) {
        this.onNetworkStateChanged(((NetAction) o));
    }

    public abstract void onNetworkStateChanged(NetAction action);

    public static class NetAction {
        private boolean isAvailable;
        private boolean isWifi;
        private Network.Type type;

        public NetAction(boolean isAvailable, boolean isWifi, Network.Type type) {
            super();
            this.isAvailable = isAvailable;
            this.isWifi = isWifi;
            this.type = type;
        }

        public boolean isAvailable() {
            return this.isAvailable;
        }

        public Network.Type getType() {
            return type;
        }

        public void setType(Network.Type type) {
            this.type = type;
        }

        public boolean isWifi() {
            return this.isWifi;
        }
    }

}
