package jp.toastkid.wifi_switcher.libs;

import android.content.Intent;
import android.provider.Settings;

/**
 * @author toastkidjp
 */

public class SettingIntentFactory {

    /**
     * Make launch settings intent.
     * @return {@link Intent}
     */
    public static Intent device() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_SETTINGS);
        return intent;
    }

    /**
     * Make launch Wi-Fi settings intent.
     * @return {@link Intent}
     */
    public static Intent wifi() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_WIFI_SETTINGS);
        return intent;
    }

    /**
     * Make launch Wi-Fi IP settings intent.
     * @return {@link Intent}
     */
    public static Intent wifiIp() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_WIFI_IP_SETTINGS);
        return intent;
    }

    /**
     * Make launch wireless settings intent.
     * @return {@link Intent}
     */
    public static Intent wireless() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
        return intent;
    }

    /**
     * Make launch airplane settings intent.
     * @return {@link Intent}
     */
    public static Intent airplane() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        return intent;
    }

}
