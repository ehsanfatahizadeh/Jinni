package ehsanfatahizadehgmail.com.jinni.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CheckReceiver extends BroadcastReceiver {
    public CheckReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, CheckIntentService.class);
        context.startService(intent1);
    }
}