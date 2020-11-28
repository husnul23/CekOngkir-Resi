package app.cekongkir.kotlin.database

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

    fun put(key: String, value: Int) {
        editor.putInt(key, value)
                .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
                .apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getInt(key: String): Int? {
        return sharedPref.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun clear() {
        editor.clear()
                .apply()
    }
}