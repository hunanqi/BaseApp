package base;

import android.app.Application;
import android.content.Context;


public class LibApp extends Application{

    private static LibApp instance;


    public LibApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}
