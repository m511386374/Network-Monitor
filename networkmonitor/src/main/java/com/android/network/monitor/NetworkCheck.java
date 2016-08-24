
package com.android.network.monitor;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public final class NetworkCheck {

    private static final String DEFAULT_PING_HOST = "www.google.com";
    private static final int DEFAULT_PING_PORT = 80;
    private static final int DEFAULT_PING_TIMEOUT_IN_MS = 2000;

    private final Context context;
    private String pingHost;
    private int pingPort;
    private int pingTimeout;

    public NetworkCheck(Context context) {
        this.context = context;
        this.pingHost = DEFAULT_PING_HOST;
        this.pingPort = DEFAULT_PING_PORT;
        this.pingTimeout = DEFAULT_PING_TIMEOUT_IN_MS;
    }

    public void check() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                boolean isOnline = false;
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(pingHost, pingPort), pingTimeout);
                    isOnline = socket.isConnected();
                } catch (IOException e) {
                    isOnline = false;
                } finally {
                   // sendBroadcast(isOnline);
                }
                return null;
            }
        }.execute();
    }

    public void setPingParameters(String host, int port, int timeoutInMs) {
        this.pingHost = host;
        this.pingPort = port;
        this.pingTimeout = timeoutInMs;
    }

}
