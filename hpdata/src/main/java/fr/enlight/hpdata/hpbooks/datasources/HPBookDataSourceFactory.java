package fr.enlight.hpdata.hpbooks.datasources;

import android.content.Context;

import fr.enlight.hpdata.definition.IDataSourceFactory;

/**
 * Created by enlight on 15/11/2015.
 */
public class HPBookDataSourceFactory implements IDataSourceFactory<IHPBookDataSource> {

    private final Context context;
    private final HPBooksCache booksCache;

    public HPBookDataSourceFactory(Context context, HPBooksCache booksCache) {
        this.context = context;
        this.booksCache = booksCache;
    }

    @Override
    public IHPBookDataSource createDataSource() {
        if(booksCache.isInCache() && !booksCache.isExpired()){
            return createDiskBookDataSource();
        }
        return createCloudBookDataSource();
    }

    public IHPBookDataSource createCloudBookDataSource(){
        return new CloudHPBookDataSource(context, booksCache);
    }

    public IHPBookDataSource createDiskBookDataSource(){
        return new DiskHPBookDataSource(booksCache);
    }

}
