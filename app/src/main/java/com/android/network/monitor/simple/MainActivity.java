package com.android.network.monitor.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.network.monitor.NetworkAction;
import com.android.network.monitor.NetworkManager;
import com.android.network.monitor.NetworkObserver;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class MainActivity extends AppCompatActivity {

    private HTextView mNetworkTextView;
    private HTextView mSignalTextView;

    private NetworkObserver mNetworkObserver = new NetworkObserver() {

        @Override
        public void onNetworkStateChanged(NetworkAction action) {
            Log.i(MainActivity.class.getSimpleName(), "网络可用 > " + "网络类型:" + action.getType().toString());
            Log.i(MainActivity.class.getSimpleName(), "网络可用 > " + "网络质量:" + action.getWifiSignalLevel().toString());
            mNetworkTextView.animateText(action.getType().toString());
            mSignalTextView.animateText(action.getWifiSignalLevel().toString());
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetworkTextView = (HTextView) findViewById(R.id.network);
        mSignalTextView =  (HTextView) findViewById(R.id.signal);
        mNetworkTextView.setAnimateType(HTextViewType.LINE);
        mSignalTextView.setAnimateType(HTextViewType.LINE);

        NetworkManager.getInstance().initialized(this);
        NetworkManager.getInstance().register(this.mNetworkObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unregister(this.mNetworkObserver);
    }

}
