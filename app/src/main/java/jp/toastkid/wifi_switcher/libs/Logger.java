package jp.toastkid.wifi_switcher.libs;

import android.util.Log;

import jp.toastkid.wifi_switcher.BuildConfig;

/**
 * @author toastkidjp
 */
public class Logger {

    public static void i(final String s) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        Log.i("Logger", s);
    }
}
