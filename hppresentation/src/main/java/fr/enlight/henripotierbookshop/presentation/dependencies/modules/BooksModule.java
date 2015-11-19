package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.BookScope;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.hpdata.hpbooks.BookstoreModel;

/**
 * A Dagger module that provides the Interactor used to interact with the bookstore model.
 */
@Module
public class BooksModule {

    @Provides
    @BookScope
    BookstoreModel provideBookstoreModel(Context context) {
        return new BookstoreModel(context);
    }

    @Provides
    @BookScope
    BookCartModel provideBookCartModel() {
        return new BookCartModel();
    }
}
