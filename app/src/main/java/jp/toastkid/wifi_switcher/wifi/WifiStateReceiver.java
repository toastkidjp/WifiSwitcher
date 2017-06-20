package jp.toastkid.wifi_switcher.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import jp.toastkid.wifi_switcher.appwidget.Updater;

/**
 * @author toastkidjp
 */
public class WifiStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (intent == null || !intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            return;
        }

        final NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (!networkInfo.getTypeName().equals("WIFI")) {
            return;
        }

        final NetworkInfo.State state = networkInfo.getState();

        switch (state) {
            case CONNECTED:
                Updater.update(new ContextWrapper(context));
                break;
            case DISCONNECTED:
                Updater.update(new ContextWrapper(context));
                break;
            default:
                return;
        }

    }
}
