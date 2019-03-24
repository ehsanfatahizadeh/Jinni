package ehsanfatahizadehgmail.com.jinni;

import android.app.Application;
import android.content.Context;

import com.github.johnkil.print.PrintConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        PrintConfig.initDefault(getAssets(), "fonts/material-icons.ttf");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }
}
