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
        registerCashBackReceiver();

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

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(cashBackReceiver);
    }

    private void registerCashBackReceiver() {
        cashBackReceiver = new CashBackReceiver();
        /**
         * the action in the intent filter must the same as the broadcast's implicit intent's action
         * */
        IntentFilter cashBackIntentFilter = new IntentFilter();
        cashBackIntentFilter.addAction(CashBackService.ACTION_CASHBACK_INFO);
        registerReceiver(cashBackReceiver, cashBackIntentFilter);
    }

    class CashBackReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            result.setText(intent.getStringExtra(CashBackService.KEY_CASHBACK));
        }
    }
 }
