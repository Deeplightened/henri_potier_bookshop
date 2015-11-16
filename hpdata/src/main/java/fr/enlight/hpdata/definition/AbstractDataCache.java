package fr.enlight.hpdata.definition;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * A class keeping in memory the last update for data in cache
 */
public abstract class AbstractDataCache<D> {

    public static final String CACHE_PREFERENCES = "CACHE_PREFERENCES";

    private final SharedPreferences sharedPreferences;
    private final String lastUpdateTag;
    private final long cacheDuration;

    public AbstractDataCache(Context context, String lastUpdateTag, long cacheDuration) {
        sharedPreferences = context.getSharedPreferences(CACHE_PREFERENCES, Context.MODE_PRIVATE);
        this.lastUpdateTag = lastUpdateTag;
        this.cacheDuration = cacheDuration;
    }

    /**
     * Update the date when the data has been stored in cache.
     */
    public void saveLastUpdateTime(){
        sharedPreferences.edit()
                .putLong(lastUpdateTag, System.currentTimeMillis())
                .apply();
    }


    /**
     * Save the data managed by this AbstractDataCache into the appropriate storage system. Works together with the method loadDataFromCache
     *
     * @param data the managed data
     */
    public abstract void saveDataInCache(D data);

    /**
     * Load the data managed by this AbstractDataCache from the appropriate storage system. Works together with the method saveDataInCache
     * @return the managed data
     */
    public abstract D loadDataFromCache();

    /**
     * Verify if the managed storage has been stored in cache
     *
     * @return true if the data is in cache, false otherwise
     */
    public abstract boolean isInCache();

    /**
     * @return true if the data in cache has expired (the cache duration is exceeded), false otherwise.
     */
    public boolean isExpired(){
        long currentTime = System.currentTimeMillis();
        long lastUpdate = sharedPreferences.getLong(lastUpdateTag, Long.MIN_VALUE);

        return (currentTime > lastUpdate + cacheDuration);
    }

}
