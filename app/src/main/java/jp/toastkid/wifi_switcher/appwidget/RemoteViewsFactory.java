package jp.toastkid.wifi_switcher.appwidget;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import jp.toastkid.wifi_switcher.R;
import jp.toastkid.wifi_switcher.libs.preference.PreferenceApplier;
import jp.toastkid.wifi_switcher.wifi.WifiSwitcher;

/**
 * App Widget's RemoteViews factory.
 *
 * @author toastkidjp
 */
class RemoteViewsFactory {

    /**
     * Method name.
     */
    private static final String METHOD_NAME_SET_COLOR_FILTER = "setColorFilter";

    /**
     * Make RemoteViews.
     *
     * @param context
     * @return RemoteViews
     */
    @NonNull
    static RemoteViews make(final Context context) {
        final RemoteViews remoteViews
                = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        setTapActions(context, remoteViews);

        final PreferenceApplier preferenceApplier = new PreferenceApplier(context);

        final WifiManager wifiManager
                = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final boolean wifiEnabled = wifiManager.isWifiEnabled();
        setColor(
                remoteViews,
                wifiEnabled
                        ? preferenceApplier.getColor()
                        : ContextCompat.getColor(context, R.color.disbaled)
        );

        setFontColor(remoteViews, preferenceApplier.getFontColor());
        remoteViews.setTextViewText(R.id.widget_text, wifiEnabled ? "ON" : "OFF");

        return remoteViews;
    }

    /**
     * Set background color to remote views.
     * @param remoteViews
     * @param backgroundColor
     */
    private static void setColor(
            final RemoteViews remoteViews,
            @ColorInt final int backgroundColor
    ) {
        remoteViews.setInt(R.id.widget_wifi, METHOD_NAME_SET_COLOR_FILTER, backgroundColor);
    }

    /**
     * Set font color to remote views.
     * @param remoteViews
     * @param fontColor
     */
    private static void setFontColor(
            final RemoteViews remoteViews,
            @ColorInt final int fontColor
    ) {
        remoteViews.setTextColor(R.id.widget_text, fontColor);
    }

    /**
     * Set pending intents.
     * @param context
     * @param remoteViews
     */
    private static void setTapActions(final Context context, final RemoteViews remoteViews) {
        remoteViews.setOnClickPendingIntent(
                R.id.widget_wifi, WifiSwitcher.makePendingIntent(context));
    }

}
