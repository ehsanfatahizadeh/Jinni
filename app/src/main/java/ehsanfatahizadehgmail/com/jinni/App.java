package ehsanfatahizadehgmail.com.jinni;

import android.app.Application;

import com.github.johnkil.print.PrintConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PrintConfig.initDefault(getAssets(), "fonts/material-icons.ttf");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }
}
