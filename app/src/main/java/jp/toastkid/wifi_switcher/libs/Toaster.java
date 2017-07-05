package jp.toastkid.wifi_switcher.libs;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import jp.toastkid.wifi_switcher.settings.SettingsActivity;

/**
 * Simple toasting utilities.
 *
 * @author toastkidjp
 */
public class Toaster {

    /**
     * Show simple snackbar on short time.
     *
     * @param view
     * @param message
     * @param color
     */
    public static void snackShort(
            final View view,
            @NonNull final String message,
            @ColorInt final int color,
            @ColorInt final int fontColor
            ) {
        final Snackbar snackbar
                = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(color);
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(fontColor);
        snackbar.show();
    }

    /**
     * Show simple snackbar on short time.
     *
     * @param view
     * @param messageId
     * @param color
     */
    public static void snackShort(
            final View view,
            @StringRes final int messageId,
            @ColorInt final int color,
            @ColorInt final int fontColor
            ) {
        snackShort(view, view.getResources().getString(messageId), color, fontColor);
    }

    /**
     * Show short toast.
     * @param context
     * @param messageId
     */
    public static void tShort(final Context context, @StringRes final int messageId) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
    }
}
