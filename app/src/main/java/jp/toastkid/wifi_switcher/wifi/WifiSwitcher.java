package jp.toastkid.wifi_switcher.wifi;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import jp.toastkid.wifi_switcher.SnackbarActivity;
import jp.toastkid.wifi_switcher.appwidget.Updater;

/**
 * @author toastkidjp
 */

public class WifiSwitcher extends Service {

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (intent == null) {
            return START_STICKY_COMPATIBILITY;
        }

        final Context context = getApplicationContext();
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final boolean newState = !wifiManager.isWifiEnabled();
        wifiManager.setWifiEnabled(newState);
        getApplicationContext().startActivity(
                SnackbarActivity.makeIntent(context, "Wi-Fi: " + (newState ? "ON" : "OFF"))
        );
        Updater.update(getApplication());
        return START_STICKY_COMPATIBILITY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Make switch Wi-Fi state intent.
     * @param context
     * @return {@link WifiSwitcher}'s pending intent
     */
    public static PendingIntent makePendingIntent(final Context context) {
        final Intent intent = new Intent(context, WifiSwitcher.class);

        return PendingIntent.getService(
                context,
                WifiSwitcher.class.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

}
