package jp.toastkid.wifi_switcher.advertisement;

import android.support.annotation.NonNull;

import com.google.android.gms.ads.AdView;

/**
 * @author toastkidjp
 */
public interface AdInitializer {
    public void invoke(@NonNull final AdView adView);
}
