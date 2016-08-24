package com.android.network.monitor;

public enum NetworkType {

    UNKNOWN("unknown"),
    WIFI_CONNECTED("connected to WiFi"),
    WIFI_CONNECTED_HAS_INTERNET("connected to WiFi (Internet available)"),
    WIFI_CONNECTED_HAS_NO_INTERNET("connected to WiFi (Internet not available)"),
    MOBILE_CONNECTED("connected to mobile network"),
    MOBILE2G("mobile2g"),
    MOBILE3G("mobile3g"),
    MOBILE4G("mobile4g"),
    OFFLINE("offline");

    private final String status;

    NetworkType(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
