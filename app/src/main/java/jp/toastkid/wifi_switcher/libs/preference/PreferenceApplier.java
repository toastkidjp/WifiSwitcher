package jp.toastkid.wifi_switcher.libs.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author toastkidjp
 */
public class PreferenceApplier {

    private enum Key {
        BG_COLOR, FONT_COLOR, LAST_AD_DATE;
    }

    private SharedPreferences mPreferences;

    public PreferenceApplier(final Context c) {
        mPreferences = c.getSharedPreferences(getClass().getCanonicalName(), Context.MODE_PRIVATE);
    }

    public int getColor() {
        return mPreferences.getInt(Key.BG_COLOR.name(), Color.argb(128, 0, 34, 119));
    }

    public void setColor(final int color) {
        mPreferences.edit().putInt(Key.BG_COLOR.name(), color).apply();
    }

    public int getFontColor() {
        return mPreferences.getInt(Key.FONT_COLOR.name(), Color.WHITE);
    }

    public void setFontColor(final int color) {
        mPreferences.edit().putInt(Key.FONT_COLOR.name(), color).apply();
    }

    public void updateLastAd() {
        mPreferences.edit()
                .putInt(Key.LAST_AD_DATE.name(), Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_YEAR))
                .apply();
    }

    public boolean allowShowingAd() {
        final int today = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_YEAR);
        return today != mPreferences.getInt(Key.LAST_AD_DATE.name(), -1);
    }

    public void clear() {
        mPreferences.edit().clear().apply();
    }

}
