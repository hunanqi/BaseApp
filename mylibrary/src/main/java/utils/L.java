package utils;

import android.util.Log;


public class L {
    private static String TAG = "ttgg";
    private static String Misuzu = "Misuzu";
    private static String hunaqi = "hunanqi";
    private static boolean isDeBug = true;

    public static void dm(String s) {
        if (isDeBug)
            Log.d(Misuzu, s);
    }

    public static void h(String s) {
        if (isDeBug)
            Log.d(hunaqi, s);
    }

    public static void d( String msg) {
        if (isDeBug) {
            if (msg.length() > 2000) {
                for (int i = 0; i < msg.length(); i += 2000) {
                    if (i + 2000 < msg.length()) {
                        android.util.Log.i(TAG, msg.substring(i, i + 2000));
                    } else {
                        android.util.Log.i(TAG, msg.substring(i, msg.length()));
                    }
                }
            } else {
                android.util.Log.i(TAG, msg);
            }
        }

    }

}
