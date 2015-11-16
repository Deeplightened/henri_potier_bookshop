package fr.enlight.hpdata.hpbooks.datasources;

import android.content.Context;

import java.io.File;
import java.util.List;

import fr.enlight.hpdata.definition.AbstractDataCache;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.utils.FileHelper;

/**
 * A class keeping in memory the last update for HPBook catalog in cache and allowing to save them in a file.
 */
public class HPBooksCache extends AbstractDataCache<List<HPBook>> {

    public static final String BOOK_CATALOG_CACHE_FILENAME = "bookCatalog";

    public static final String LAST_HPBOOKS_UPDATE = "LAST_HPBOOKS_UPDATE";

    private final Context context;

    public HPBooksCache(Context context, long cacheDuration) {
        super(context, LAST_HPBOOKS_UPDATE, cacheDuration);
        this.context = context;
    }

    @Override
    public void saveDataInCache(List<HPBook> data) {
        File filepath = getBookCatalogFile();
        FileHelper.saveFileObjects(filepath, data);
        saveLastUpdateTime();
    }

    @Override
    public List<HPBook> loadDataFromCache() {
        File filepath = getBookCatalogFile();
        return FileHelper.loadFileObjects(filepath, HPBook.class);
    }

    @Override
    public boolean isInCache() {
        File bookCatalogFile = getBookCatalogFile();
        return bookCatalogFile.exists();
    }

    public File getBookCatalogFile(){
        File cacheDir = context.getCacheDir();
        return new File(cacheDir.getAbsolutePath() + File.separator + BOOK_CATALOG_CACHE_FILENAME);
    }
}
