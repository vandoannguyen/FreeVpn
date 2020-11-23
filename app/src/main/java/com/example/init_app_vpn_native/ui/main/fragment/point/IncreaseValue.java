package com.example.init_app_vpn_native.ui.main.fragment.point;

import android.os.Handler;
import android.util.Log;
import android.widget.NumberPicker;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


class IncreaseValue {
    private String TAG = "IncreaseValue";
    private int counter = 0;
    private final NumberPicker picker;
    private final int incrementValue;
    private final boolean increment;

    private final Handler handler = new Handler();
    private final Runnable fire = new Runnable() { @Override public void run() { fire(); } };

    /*public*/ IncreaseValue(final NumberPicker picker, final int incrementValue) {
        this.picker = picker;
        if (incrementValue > 0) {
            increment = true;
            this.incrementValue = incrementValue;
        } else {
            increment = false;
            this.incrementValue = -incrementValue;
        }
    }

    /*public*/ void run(final int startDelay) {
        handler.postDelayed(fire, startDelay);  // This will execute the runnable passed to it (fire)
        // after [startDelay in milliseconds], ASYNCHRONOUSLY.
    }

    private void fire() {
        ++counter;
        if (counter > incrementValue) return;

        try {
            // refelction call for
            // picker.changeValueByOne(true);
            final Method method = picker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(picker, increment);

        } catch (final NoSuchMethodException | InvocationTargetException |
                IllegalAccessException | IllegalArgumentException e) {
            Log.d(TAG, "...", e);
        }

        handler.postDelayed(fire, 120);  // This will execute the runnable passed to it (fire)
        // after 120 milliseconds, ASYNCHRONOUSLY. Customize this value if necessary.
    }
}
