package ehsanfatahizadehgmail.com.jinni.amounts;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;

import ehsanfatahizadehgmail.com.jinni.App;

public class Constants {



    public static String SHARED_USER = "info_user";
    public static int INT_CAMERA = 0;
    public static int INT_PICK_GALLERY =1 ;
    public static int INT_CROP = 2;
    public static int INT_AFTER_CROP = 3;


    public static String cities = loadJSONCitiesFromAsset();



    public static String loadJSONCitiesFromAsset() {
        String json = null;
        try {
            InputStream is = App.context.getAssets().open("jsons/cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }






}
