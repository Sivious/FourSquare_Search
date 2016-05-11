package sivianes.foursquaresearch.framework.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Javier on 10/05/2016.
 */
//@Singleton
public class Preferences {
    private static final String REGISTRATION_TOKEN = "registrationToken";
    private static final String DEVICEPREFS = "DevicePrefs";

    private static SharedPreferences devicePreferences;

    public static String getRegistrationToken(Context context) {
        devicePreferences = context.getSharedPreferences(DEVICEPREFS, Context.MODE_PRIVATE);
        return devicePreferences.getString(REGISTRATION_TOKEN, null);
    }

    public static void setRegistrationToken(Context context, String token) {
        devicePreferences = context.getSharedPreferences(DEVICEPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = devicePreferences.edit();
        editor.putString(REGISTRATION_TOKEN, token);
        editor.commit();
    }
}
