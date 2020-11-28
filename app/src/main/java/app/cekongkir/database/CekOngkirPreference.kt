package app.cekongkir.database

import android.content.Context
import android.content.SharedPreferences

private const val pref_name = "CekOngkirLazday.pref"

class CekOngkirPreference (context: Context) {

    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
                .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
                .apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun clear() {
        editor.clear()
                .apply()
    }
}