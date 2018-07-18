package com.example.amazinglu.intent_service_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cask_back_result) TextView result;
    @BindView(R.id.cash_back_kind) EditText kind;
    @BindView(R.id.request_cash_back) Button request;

    private CashBackReceiver cashBackReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reqestText = kind.getText().toString();
                // start the service to get the cash back
                Intent intent = new Intent(MainActivity.this, CashBackService.class);
                intent.putExtra(CashBackService.KEY_KIND, reqestText);
                startService(intent);
            }
        });
    }

    /**
     * if we want to register the BroadcastReceiver in manifest, this recevier can not be the inner class of activity
     * and receiver should have its own life cycle
     *
     * 也是可以理解的，在manifest中register就是说在app的任何时间包括activity不存在的时候搜可以随便调用，但是
     * 如何这个receiver的生命周期和activity是绑在一起的话，这就不可能了
     *
     * 所以说在manifest中register的元素都应该保证有自己单独的不受其他控件影响的life cycle
     *
     * 所以当BroadcastReceiver 作为 activity的 inner class的时候，用explicit intent 在broadcast 和 BroadcastReceiver
     * 之间是不行的
     * */
    public class CashBackReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            result.setText(intent.getStringExtra(CashBackService.KEY_CASHBACK));
        }
    }
 }
