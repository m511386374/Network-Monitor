package com.android.network.monitor.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.network.monitor.NetworkManager;
import com.android.network.monitor.NetworkObserver;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class MainActivity extends AppCompatActivity {

    private HTextView mHTextView;

    private NetworkObserver mNetworkObserver = new NetworkObserver() {
        @Override
        public void onNetworkStateChanged(NetworkObserver.NetAction action) {
            Log.i(MainActivity.class.getSimpleName(), "网络可用 > " + "网络类型:" + action.getType().toString());
            mHTextView.animateText(action.getType().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHTextView = (HTextView) findViewById(R.id.text);
        mHTextView.setAnimateType(HTextViewType.LINE);

        NetworkManager.getInstance().initialized(this);
        NetworkManager.getInstance().register(this.mNetworkObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unregister(this.mNetworkObserver);
    }

}
