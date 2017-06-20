package jp.toastkid.wifi_switcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.toastkid.wifi_switcher.libs.Toaster;
import jp.toastkid.wifi_switcher.libs.preference.PreferenceApplier;

/**
 * Dummy activity for using Snackbar on App-Widget.
 *
 * @author toastkidjp
 */
public class SnackbarActivity extends AppCompatActivity {

    /** Key of Extra. */
    private static final String EXTRA_KEY_COLOR = "color";

    /** Key of Extra. */
    private static final String EXTRA_KEY_MESSAGE = "message";

    /** Hold for using Snackbar. */
    private View view;

    /** Snackbar's color. */
    private int mColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = makeDummyView();
        setContentView(view);
        overridePendingTransition(0, 0);

        mColor = getIntent().getIntExtra(EXTRA_KEY_COLOR, Color.BLACK);
        setStatusBarTransparent();
        executeFinisher();
    }

    private View makeDummyView() {
        final View view = new View(this);
        view.setOnClickListener(v -> finish());
        return view;
    }

    private void executeFinisher() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        });
        executorService.shutdown();
    }

    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(mColor);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_KEY_MESSAGE)) {
            return;
        }
        Toaster.snackShort(view, intent.getStringExtra(EXTRA_KEY_MESSAGE), mColor);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public static Intent makeIntent(
            @NonNull final Context context,
            @NonNull final String  message
    ) {
        final Intent intent = new Intent(context, SnackbarActivity.class);
        intent.putExtra(EXTRA_KEY_MESSAGE, message);
        intent.putExtra(EXTRA_KEY_COLOR, new PreferenceApplier(context).getColor());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}