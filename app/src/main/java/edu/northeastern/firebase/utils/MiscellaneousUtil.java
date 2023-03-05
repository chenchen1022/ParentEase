package edu.northeastern.firebase.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class MiscellaneousUtil {
    /**
     * Gets the properties from a property document.
     * The demo code of this module - FirebaseDemo3.java is referenced.
     *
     * @param context the context in which the method is called
     * @return the properties
     */
    public static Properties getProperties(Context context) {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("firebase.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Gets the time stamp.
     *
     * @return the time stamp
     *
     * @author Shichang Ye
     */
    public static String getTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }
}
