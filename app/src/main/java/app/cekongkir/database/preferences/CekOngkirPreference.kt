package app.cekongkir.database.preferences

import android.content.Context
import android.content.SharedPreferences

private const val prefName = "CekOngkirLazday.pref"

class CekOngkirPreference (context: Context) {

    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
                .apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }
}