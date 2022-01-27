package com.reactnativevibrationeffect;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.facebook.react.bridge.ReadableArray;
import android.util.Log;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Build;
import android.content.Context;

@ReactModule(name = VibrationEffectModule.NAME)
public class VibrationEffectModule extends ReactContextBaseJavaModule {
    public static final String NAME = "VibrationEffect";

    ReactApplicationContext reactContext;


    public VibrationEffectModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    private boolean checkVersion() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @ReactMethod
    public void hasAmplitudeControl(final Promise promise) {
        Vibrator v = (Vibrator) reactContext.getSystemService(Context.VIBRATOR_SERVICE);
        promise.resolve(v.hasAmplitudeControl());
    }

    @ReactMethod
    public void createWaveform(ReadableArray timings, ReadableArray amplitudes, double repeat) {
        Vibrator v = (Vibrator) reactContext.getSystemService(Context.VIBRATOR_SERVICE);

        if (v != null && this.checkVersion()) {

            // Conversions
            long[] _timings = new long[timings.size()];
            for (int i = 0; i < timings.size(); i++) {
                _timings[i] = timings.getInt(i);
            }

            int _repeat = (int) repeat;

            if (amplitudes != null && v.hasAmplitudeControl()) {

                // Conversion
                int[] _amplitudes = new int[amplitudes.size()];
                for (int i = 0; i < amplitudes.size(); i++) {
                    _amplitudes[i] = amplitudes.getInt(i);
                }

                VibrationEffect effect = VibrationEffect.createWaveform(_timings, _amplitudes, _repeat);
                v.vibrate(effect);
            } else {
                VibrationEffect effect = VibrationEffect.createWaveform(_timings, _repeat);
                v.vibrate(effect);
            }
        }
    }

    @ReactMethod
    public void cancel() {      
        Vibrator v = (Vibrator) reactContext.getSystemService(Context.VIBRATOR_SERVICE);

        if (v != null) {
            v.cancel();
        }
    }
}
