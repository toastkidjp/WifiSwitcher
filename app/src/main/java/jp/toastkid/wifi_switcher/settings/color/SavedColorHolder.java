package jp.toastkid.wifi_switcher.settings.color;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import jp.toastkid.wifi_switcher.R;

/**
 * @author toastkidjp
 */
class SavedColorHolder extends RecyclerView.ViewHolder {

    final Button textView;
    final View   remove;

    SavedColorHolder(final View itemView) {
        super(itemView);
        textView = (Button) itemView.findViewById(R.id.color);
        remove   = itemView.findViewById(R.id.color_remove);
    }
}