package jp.toastkid.wifi_switcher.settings;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.toastkid.wifi_switcher.BaseActivity;
import jp.toastkid.wifi_switcher.BuildConfig;
import jp.toastkid.wifi_switcher.R;
import jp.toastkid.wifi_switcher.advertisement.AdInitializers;
import jp.toastkid.wifi_switcher.appwidget.Updater;
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

    @BindView(R.id.settings_toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.settings_clear)
    public TextView clear;

    @BindView(R.id.settings_color_text)
    public TextView colorText;

    @BindView(R.id.settings_licenses)
    public TextView license;

    @BindView(R.id.wifi_state)
    public TextView wifiState;

    private PreferenceApplier mPreferenceApplier;

    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mPreferenceApplier = new PreferenceApplier(this);
        initToolbar(mToolbar);
        clear.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle(R.string.title_clear)
                .setMessage(Html.fromHtml(getString(R.string.confirm_clear_all_settings)))
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (d, i) -> d.cancel())
                .setPositiveButton(R.string.ok,      (d, i) -> {
                    mPreferenceApplier.clear();
                    Updater.update(this);
                    refresh();
                    Toaster.snackShort(
                            mToolbar,
                            R.string.done_clear,
                            mPreferenceApplier.getColor(),
                            mPreferenceApplier.getFontColor()
                    );
                })
                .show());
        license.setOnClickListener(v -> new LicenseViewer(this).invoke());

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
                mToolbar, mPreferenceApplier.getColor(), mPreferenceApplier.getFontColor());
        final WifiManager wifiManager
                = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiState.setText(wifiManager.isWifiEnabled() ? "ON" : "OFF");
        DrawableCompat.setTint(mToolbar.getOverflowIcon(), mPreferenceApplier.getFontColor());
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
                        mToolbar,
                        R.string.thank_you_for_using,
                        mPreferenceApplier.getColor(),
                        mPreferenceApplier.getFontColor()
                );
            }
        });
        AdInitializers.find(this).invoke(interstitialAd);
    }

    @OnClick(R.id.settings_color)
    public void color() {
        attemptToShowingAd();
        startActivity(ColorSettingActivity.makeIntent(this));
    }

    @OnClick(R.id.settings_wifi_detail)
    public void wifi() {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.wifi());
    }

    @OnClick(R.id.settings_wifi_ip)
    public void wifiIp() {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.wifiIp());
    }

    @OnClick(R.id.settings_wireless)
    public void wireless() {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.wireless());
    }

    @OnClick(R.id.settings_device)
    public void deviceSettings() {
        attemptToShowingAd();
        startActivity(SettingIntentFactory.device());
    }

    @OnClick(R.id.settings_background)
    public void close() {
        finish();
    }

    @OnClick(R.id.settings_wifi_switch)
    public void wifiSwitch() {
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final boolean newState = !wifiManager.isWifiEnabled();
        wifiManager.setWifiEnabled(newState);
        final String wifiStateText = newState ? "ON" : "OFF";
        Toaster.snackShort(
                mToolbar,
                "Wi-Fi: " + wifiStateText,
                mPreferenceApplier.getColor(),
                mPreferenceApplier.getFontColor()
        );
        wifiState.setText(wifiStateText);
    }

    private void attemptToShowingAd() {
        if (interstitialAd.isLoaded() && mPreferenceApplier.allowShowingAd()) {
            Toaster.snackShort(
                    mToolbar,
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