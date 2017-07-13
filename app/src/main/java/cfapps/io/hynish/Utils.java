package cfapps.io.hynish;


import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by prahl on 08-07-2017.
 */

public class Utils {

    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

         return (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected());
    }
}
