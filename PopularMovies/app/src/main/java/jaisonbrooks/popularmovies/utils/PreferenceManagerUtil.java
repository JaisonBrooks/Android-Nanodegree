package jaisonbrooks.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jbrooks on 8/23/15.
 */
public class PreferenceManagerUtil {

    private static final String PREFS = "movie_prefs";

    private Context context;
    private SharedPreferences prefs;

    public static final String PREF_MOVIE_FAVORITES = "movie_favorites";

    public PreferenceManagerUtil(Context context) {
        this.context = context;
        this.prefs = initPrefs();

    }
    public SharedPreferences initPrefs() {
        return context.getSharedPreferences(PREFS, 0);
    }
    private SharedPreferences get() {
        if (this.prefs == null) {
            return initPrefs();
        }
        return this.prefs;
    }

    public boolean has(String key) {
        return this.get().contains(key);
    }
    private SharedPreferences.Editor edit() {
        return get().edit();
    }
    public void putStr(String key, String val) {
        edit().putString(key,val).commit();
    }
    public boolean hasStrInSet(String key, String val) {
        Set<String> stringSet = getStrSet(key);
        for (String string : stringSet) {
            if (string.equalsIgnoreCase(val)) {
                return true;
            }
        }
        return false;
    }
    public void removeStrFromSet(String key, String val) {
        log("Prefs - Removed " + val + " from " + key);
        Set<String> current = getStrSet(key);
        current.remove(val);
        edit().putStringSet(key, current).commit();

    }
    public void addStrToSet(String key, String val) {
        log("Prefs - Added " + val + " to " + key);
        Set<String> current = getStrSet(key);
        current.add(val);
        edit().putStringSet(key, current).commit();
    }
    public Set<String> getStrSet(String key) {
        return this.get().getStringSet(key, new HashSet<String>());
    }
    public void putStrSet(String key, HashSet<String> set) {
        edit().putStringSet(key, set).commit();
    }
    public String getStr(String key) {
        return this.get().getString(key, "");
    }
    public void putInt(String key, int val) {
        edit().putInt(key, val).commit();
    }
    public int getInt(String key) {
        return this.get().getInt(key, 0);
    }

    public void delete(String key) {
        edit().remove(key).commit();
    }

    private void log(String text) {
        Log.e("SharedPreferences", text);
    }
    private void logKeyValue(String key, String value) {
        this.log("Key: " + key + " , Value: " + value);
    }
}
