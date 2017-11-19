package jp.toastkid.wifi_switcher.advertisement;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import jp.toastkid.wifi_switcher.BuildConfig;
import jp.toastkid.wifi_switcher.R;

/**
 * @author toastkidjp
 */
class TestAdInitializer implements AdInitializer {

    /**
     * @param context need ApplicationContext
     */
    TestAdInitializer(@NonNull final Context context) {
        if (!BuildConfig.DEBUG) {
            throw new IllegalStateException();
        }
        final String appAdId = context.getString(R.string.production_app_admob_id);
        MobileAds.initialize(context, appAdId);
    }

    /**
     * Do AdRequest.
     */
    @Override
    public void invoke(@NonNull final AdView adView) {
        if (!BuildConfig.DEBUG) {
            throw new IllegalStateException();
        }
        adView.loadAd(makeTestAdRequest());
    }

    @Override
    public void invoke(@NonNull final InterstitialAd interstitialAd) {
        if (!BuildConfig.DEBUG) {
            throw new IllegalStateException();
        }
        interstitialAd.loadAd(makeTestAdRequest());
    }

    @NonNull
    private AdRequest makeTestAdRequest() {
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("59A864957D348217B858A8CE956AA352")
                .addTestDevice("FF30448442F5EAE65974D6E0FEB4C1BD")
                .build();
    }

}
