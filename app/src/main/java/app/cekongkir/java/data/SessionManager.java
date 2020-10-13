package app.cekongkir.java.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LazdayCekOngkirV1";

    private static final String IS_EXIST = "IsExist";

    public static final String ORIGIN_ID            = "origin_id";
    public static final String ORIGIN_NAME          = "origin_name";
    public static final String DESTIONATION_ID      = "destionation_id";
    public static final String DESTIONATION_NAME    = "destionation_name";
    public static final String SUBDISTRICT_ID       = "subdistrict_id";
    public static final String SUBDISTRICT_NAME     = "subdistrict_name";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSession(String origin_id, String origin_name, String destionation_id, String destionation_name,
                              String subdistrict_id, String subdistrict_name){
        editor.putBoolean(IS_EXIST, true);
        editor.putString(ORIGIN_ID, origin_id);
        editor.putString(ORIGIN_NAME, origin_name);
        editor.putString(DESTIONATION_ID, destionation_id);
        editor.putString(DESTIONATION_NAME, destionation_name);
        editor.putString(SUBDISTRICT_ID, subdistrict_id);
        editor.putString(SUBDISTRICT_NAME, subdistrict_name);
        editor.commit();
    }

    public void checkExist(){
        if(this.isExist()){
//            Intent i = new Intent(_context, MainActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            _context.startActivity(i);
        }
    }

    public HashMap<String, String> getData(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(ORIGIN_ID, pref.getString(ORIGIN_ID, null));
        user.put(ORIGIN_NAME, pref.getString(ORIGIN_NAME, null));
        user.put(DESTIONATION_ID, pref.getString(DESTIONATION_ID, null));
        user.put(DESTIONATION_NAME, pref.getString(DESTIONATION_NAME, null));
        user.put(SUBDISTRICT_ID, pref.getString(SUBDISTRICT_ID, null));
        user.put(SUBDISTRICT_NAME, pref.getString(SUBDISTRICT_NAME, null));
        return user;
    }

    public boolean isExist(){
        return pref.getBoolean(IS_EXIST, false);
    }
}
