package android.room.play.com.nasadaily.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Chirag Pc on 8/23/2015.
 */
public final class AppUtils {
    public static final boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
