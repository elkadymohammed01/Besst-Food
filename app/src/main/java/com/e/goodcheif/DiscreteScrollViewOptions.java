package com.e.goodcheif;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class DiscreteScrollViewOptions {

    private static DiscreteScrollViewOptions instance;

    private final String KEY_TRANSITION_TIME;

    public static void init(Context context) {
        instance = new DiscreteScrollViewOptions(context);
    }

    private DiscreteScrollViewOptions(Context context) {
        KEY_TRANSITION_TIME = context.getString(R.string.pref_key_transition_time);
    }


    public static int getTransitionTime() {
        return defaultPrefs().getInt(instance.KEY_TRANSITION_TIME, 15);
    }

    @SuppressWarnings("deprecation")
    private static SharedPreferences defaultPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance());
    }



}
