package fr.enlight.hpdata.datasources;

import android.content.Context;

import fr.enlight.hpdata.cache.DataCache;

/**
 * Created by enlight on 15/11/2015.
 */
public class BookDataSourceFactory {

    private Context context;

    public BookDataSourceFactory(Context context) {
        this.context = context;
    }

    public IBookDataSource createBookDataSourceWithCache(DataCache cache){
        if(cache.isExpired()){
            return createCloudBookDataSource();
        }
        return createDiskBookDataSource();
    }

    public IBookDataSource createCloudBookDataSource(){
        return new CloudBookDataSource(context);
    }

    public IBookDataSource createDiskBookDataSource(){
        return new DiskBookDataSource(context);
    }
}
