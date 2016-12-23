package com.android.network.monitor;

public class NetworkAction {

    private NetworkType type;
    private WifiSignalLevel wifiSignalLevel;

    public NetworkAction(NetworkType type, WifiSignalLevel wifiSignalLevel) {
        super();
        this.type = type;
        this.wifiSignalLevel = wifiSignalLevel;
    }

    public NetworkType getType() {
        return type;
    }

    public void setType(NetworkType type) {
        this.type = type;
    }

    public WifiSignalLevel getWifiSignalLevel() {
        return wifiSignalLevel;
    }

    public void setWifiSignalLevel(WifiSignalLevel wifiSignalLevel) {
        this.wifiSignalLevel = wifiSignalLevel;
    }

}