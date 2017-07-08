package jp.toastkid.wifi_switcher.settings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import jp.toastkid.wifi_switcher.BaseActivity;
import jp.toastkid.wifi_switcher.BuildConfig;
import jp.toastkid.wifi_switcher.R;
import jp.toastkid.wifi_switcher.advertisement.AdInitializers;
import jp.toastkid.wifi_switcher.appwidget.Updater;
import jp.toastkid.wifi_switcher.databinding.ActivitySettingsBinding;
import jp.toastkid.wifi_switcher.libs.SettingIntentFactory;
import jp.toastkid.wifi_switcher.libs.Toaster;
import jp.toastkid.wifi_switcher.libs.preference.PreferenceApplier;
import jp.toastkid.wifi_switcher.settings.color.ColorSettingActivity;

/**
 * Settings activity.
 *
 * @author toastkidjp
 */
public class SettingsActivity extends BaseActivity {

    /** Layout ID. */
    private static final int LAYOUT_RES_ID = R.layout.activity_settings;

    /** Binding object. */
    private ActivitySettingsBinding binding;

    /** Preference wrapper. */
    private PreferenceApplier mPreferenceApplier;

    /** Interstitial AD. */
    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(LAYOUT_RES_ID);
        binding = DataBindingUtil.setContentView(this, LAYOUT_RES_ID);
        binding.setActivity(this);

        mPreferenceApplier = new PreferenceApplier(this);
        initToolbar(binding.toolbar);

        ((TextView) findViewById(R.id.settings_app_version)).setText(BuildConfig.VERSION_NAME);

        initInterstitialAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        applyColorToToolbar(
                binding.toolbar, mPreferenceApplier.getColor(), mPreferenceApplier.getFontColor());
        final WifiManager wifiManager
                = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        binding.wifiState.setText(wifiManager.isWifiEnabled() ? "ON" : "OFF");
        DrawableCompat.setTint(binding.toolbar.getOverflowIcon(), mPreferenceApplier.getFontColor());
    }

    private void initInterstitialAd() {
        if (interstitialAd == null) {
            interstitialAd = new InterstitialAd(getApplicationContext());
        }
        interstitialAd.setAdUnitId(getString(R.string.production_interstitial_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Toaster.snackShort(
                        binding.toolbar,
                        R.string.thank_you_for_using,
                        mPreferenceApplier.getColor(),
                        mPreferenceApplier.getFontColor()
                );
            }
        });
        AdInitializers.find(this).invoke(interstitialAd);
    }

    public void color(final View v) {
        attemptToShowingAd();
        startActivity(ColorSettingActivity.makeIntent(this));
    }

    public void wifi(final View v) {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.wifi());
    }

    public void wifiIp(final View v) {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.wifiIp());
    }

    public void wireless(final View v) {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.wireless());
    }

    public void deviceSettings(final View v) {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.device());
    }

    public void privacyPolicy(final View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_privacy_policy))));
    }

    public void wifiSwitch(final View v) {
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final boolean newState = !wifiManager.isWifiEnabled();
        wifiManager.setWifiEnabled(newState);
        final String wifiStateText = newState ? "ON" : "OFF";
        Toaster.snackShort(
                binding.toolbar,
                "Wi-Fi: " + wifiStateText,
                mPreferenceApplier.getColor(),
                mPreferenceApplier.getFontColor()
        );
        binding.wifiState.setText(wifiStateText);
    }

    public void license(final View v) {
        new LicenseViewer(this).invoke();
    }

    public void clearSettings(final View v) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_clear)
                .setMessage(Html.fromHtml(getString(R.string.confirm_clear_all_settings)))
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (d, i) -> d.cancel())
                .setPositiveButton(R.string.ok,      (d, i) -> {
                    mPreferenceApplier.clear();
                    Updater.update(this);
                    refresh();
                    Toaster.snackShort(
                            binding.toolbar,
                            R.string.done_clear,
                            mPreferenceApplier.getColor(),
                            mPreferenceApplier.getFontColor()
                    );
                })
                .show();
    }

    private void attemptToShowingAd() {
        if (interstitialAd.isLoaded() && mPreferenceApplier.allowShowingAd()) {
            Toaster.snackShort(
                    binding.toolbar,
                    R.string.message_please_view_ad,
                    mPreferenceApplier.getColor(),
                    mPreferenceApplier.getFontColor()
            );
            interstitialAd.show();
            mPreferenceApplier.updateLastAd();
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.title_settings;
    }

}