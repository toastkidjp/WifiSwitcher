package jp.toastkid.wifi_switcher.settings.color;

import android.content.Context;

/**
 * @author toastkidjp
 */
class DbInitter {

    private static OrmaDatabase orma;

    static OrmaDatabase get(final Context context) {
        if (orma == null) {
            orma = OrmaDatabase.builder(context)
                    .name("saved_color.db")
                    .build();
        }
        return orma;
    }
}
