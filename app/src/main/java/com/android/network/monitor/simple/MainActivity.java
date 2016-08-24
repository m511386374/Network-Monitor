package com.android.network.monitor.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.network.monitor.NetMonitor;
import com.android.network.monitor.NetObserver;

public class MainActivity extends AppCompatActivity {

    private NetObserver mNetObserver = new NetObserver() {
        @Override
        public void onNetworkStateChanged(NetObserver.NetAction action) {
            Log.e(MainActivity.class.getSimpleName(), "网络可用 > " + "网络类型:" + action.getType().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetMonitor.getInstance().initialized(this);
        NetMonitor.getInstance().registerNetworkObserver(this.mNetObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetMonitor.getInstance().unregisterNetworkObserver(this.mNetObserver);
    }

}
