package fr.enlight.hpdata.definition;

/**
 * Created by yhuriez on 16/11/2015.
 */
public interface IDataSourceFactory<S extends IDataSource> {

    S createDataSource();
}
