package fr.enlight.hpdata.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * A class keeking in memory the last update for data in cache
 */
public class DataCache {

    public static final String CACHE_PREFERENCES = "CACHE_PREFERENCES";
    public static final String LAST_CATALOG_UPDATE = "LAST_CATALOG_UPDATE";

    public static final long CACHE_DURATION = 1000 * 60 * 60 * 24;

    private final SharedPreferences sharedPreferences;

    public DataCache(Context context) {
        sharedPreferences = context.getSharedPreferences(CACHE_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Update the date when the data has been stored in cache.
     */
    public void saveCache(){
        sharedPreferences.edit()
                .putLong(LAST_CATALOG_UPDATE, System.currentTimeMillis())
                .apply();
    }

    /**
     * @return true if the data in cache has expired (the cache duration is exceeded), false otherwise.
     */
    public boolean isExpired(){
        long currentTime = System.currentTimeMillis();
        long lastUpdate = sharedPreferences.getLong(LAST_CATALOG_UPDATE, Long.MIN_VALUE);

        return (currentTime > lastUpdate + CACHE_DURATION);
    }
}
