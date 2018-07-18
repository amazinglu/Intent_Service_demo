package com.example.amazinglu.intent_service_demo;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;

public class CashBackService extends IntentService {

    public static final String KEY_KIND = "kind";
    public static final String KEY_CASHBACK = "cash_back";

    public static final String ACTION_CASHBACK_INFO = "com.example.amazinglu.intent_service_demo_cashback_info";

    public static final String FOOD = "food";
    public static final String BOOK = "books";
    public static final String OTHER = "other";

    public CashBackService() {
        super("CashBackService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String kind = intent.getStringExtra(KEY_KIND);
        String cashback = getCashBack(kind);
        /**
         * service communicate with UI thread using broadcast
         * */
        sentCaskbackBack(cashback);
    }

    private void sentCaskbackBack(String cashback) {
//        Intent intent = new Intent();
//        intent.setAction(ACTION_CASHBACK_INFO);
        Intent intent = new Intent(this, MainActivity.CashBackReceiver.class);
        intent.putExtra(KEY_CASHBACK, cashback);
        sendBroadcast(intent);
    }

    private String getCashBack(String kind) {
        if (kind.equals(FOOD)) {
            return "Up to 60% cash back on all food items";
        } else if (kind.equals(BOOK)) {
            return "Up to 50% cash back on all food items";
        } else {
            return "Up to 40% cash back on all other items";
        }
    }
}
