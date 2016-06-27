package in.sobs.simpletodo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SNS on 22-Jun-16.
 */
public class L {
    public static void  l(String message){
        Log.d("myTag", message);
    }

    public static void t(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
