package com.android.network.monitor;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by kince on 16-8-24.
 */
public class NetworkManager {

    private static final String TAG = "NetworkManager";

    /**
     * NetworkManager type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;
    /**
     * Current network is GSM {@hide}
     */
    public static final int NETWORK_TYPE_GSM = 16;
    /**
     * Current network is TD_SCDMA {@hide}
     */
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    /**
     * Current network is IWLAN {@hide}
     */
    public static final int NETWORK_TYPE_IWLAN = 18;

    private static volatile NetworkManager instance = null;
    private Context mContext;
    private NetworkCheck mOnlineChecker;
    private NetworkReceiver mNetworkReceiver;
    private NetworkObservable mNetworkObservable;
    private boolean mInitialized = false;

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    /**
     * Initializes NetworkEvents object.
     *
     * @param context
     */
    public void initialized(Context context) {
        mContext = context;
        if(!mInitialized) {
            mNetworkObservable = new NetworkObservable(context);
            mOnlineChecker = new NetworkCheck(context);
            mNetworkReceiver = new NetworkReceiver(mNetworkObservable);
            mInitialized = true;
        }
    }

    /**
     * Sets ping parameters of the host used to check Internet connection.
     * If it's not set, library will use default ping parameters.
     *
     * @param host        host to be pinged
     * @param port        port of the host
     * @param timeoutInMs timeout in milliseconds
     * @return NetworkEvents object
     */
    public NetworkManager setPingParameters(String host, int port, int timeoutInMs) {
        mOnlineChecker.setPingParameters(host, port, timeoutInMs);
        return this;
    }

    /**
     * Registers NetworkEvents.
     * It should be executed in onCreate() method in activity
     * or during creating instance of class extending Application.
     */
    public void register(NetworkObserver observer) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        mContext.registerReceiver(mNetworkReceiver, intentFilter);
        mNetworkReceiver.registerNetworkObserver(observer);
    }

    /**
     * Unregisters NetworkEvents.
     * It should be executed in onDestroy() method in activity
     * or during destroying instance of class extending Application.
     */
    public void unregister(NetworkObserver observer) {
        try {
            mContext.unregisterReceiver(mNetworkReceiver);
            mNetworkReceiver.unregisterNetworkObserver(observer);
        } catch (Exception e) {
        }
    }

    /**
     * @param context
     * @return
     */
    public static NetworkInfo getCurrentActiveNetwork(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                return connectivity.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> netWorkInterface = NetworkInterface.getNetworkInterfaces();
            while (netWorkInterface.hasMoreElements()) {
                Enumeration<InetAddress> address = netWorkInterface.nextElement().getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress inetAddr = address.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr instanceof Inet4Address) {
                            return inetAddr.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * 得到当前的手机网络类型
     *
     * @param context
     * @return
     */
    public static NetworkType getNetworkType(Context context) {
        NetworkInfo netWorkInfo = NetworkManager.getCurrentActiveNetwork(context);
        if (netWorkInfo != null && netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (netWorkInfo.getSubtype()) {
                case NETWORK_TYPE_GPRS:
                case NETWORK_TYPE_EDGE:
                case NETWORK_TYPE_CDMA:
                case NETWORK_TYPE_1xRTT:
                case NETWORK_TYPE_IDEN:
                    return NetworkType.MOBILE2G;
                case NETWORK_TYPE_UMTS:
                case NETWORK_TYPE_EVDO_0:
                case NETWORK_TYPE_EVDO_A:
                case NETWORK_TYPE_HSDPA:
                case NETWORK_TYPE_HSUPA:
                case NETWORK_TYPE_HSPA:
                case NETWORK_TYPE_EVDO_B:
                case NETWORK_TYPE_EHRPD:
                case NETWORK_TYPE_HSPAP:
                    return NetworkType.MOBILE3G;
                case NETWORK_TYPE_LTE:
                case NETWORK_TYPE_TD_SCDMA:
                    return NetworkType.MOBILE4G;
            }
            return NetworkType.MOBILE_CONNECTED;//未知的移动网络
        } else if (netWorkInfo != null && netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NetworkType.WIFI_CONNECTED;
        }
        return NetworkType.OFFLINE;//no network
    }

    /**
     * @param context
     * @return
     */
    public static String getSubTypeName(Context context) {
        NetworkInfo netWorkInfo = getCurrentActiveNetwork(context);
        return netWorkInfo != null ? netWorkInfo.getSubtypeName() : null;
    }

    /**
     * @param context
     * @return
     */
    public static String getTypeName(Context context) {
        NetworkInfo net = NetworkManager.getCurrentActiveNetwork(context);
        return net != null ? net.getTypeName() : null;
    }

    /**
     * @param context
     * @return
     */
    public static WifiSignalLevel getWifiSignalLevel(Context context) {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final int rssi = wifiManager.getConnectionInfo().getRssi();
        final int level = WifiManager.calculateSignalLevel(rssi, WifiSignalLevel.getMaxLevel());
        return WifiSignalLevel.fromLevel(level);
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo net = NetworkManager.getCurrentActiveNetwork(context);
        return net != null && net.isAvailable();
    }

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo net = NetworkManager.getCurrentActiveNetwork(context);
        return net != null && net.isConnected();
    }

}
