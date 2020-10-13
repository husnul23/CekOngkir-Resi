package app.cekongkir.java.utils;

import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Muhammad Irsyad
 * on 6/11/2017.
 */

public class CommonUtils {

    public static void Toast(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void Snackbar(View view, String string){
        Snackbar.make(view, string, Snackbar.LENGTH_LONG).show();
    }
}
