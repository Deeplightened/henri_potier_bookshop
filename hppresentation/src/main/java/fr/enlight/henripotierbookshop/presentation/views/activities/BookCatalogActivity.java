package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.DaggerBooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.ActivityModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCatalogPresenter;
import fr.enlight.henripotierbookshop.presentation.views.fragments.BookCatalogFragment;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;


/**
 * Activity presenting the catalog of books available for this application.
 */
public class BookCatalogActivity extends AbstractActivity implements BookCatalogFragment.OnBookCatalogInteractionListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    BookCatalogPresenter bookCatalogPresenter;

    BooksComponent booksComponent;

    BookCatalogFragment bookCatalogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalog);

        ButterKnife.bind(this);

        initInjection();
        initActivity();
    }

    private void initInjection() {
        booksComponent = DaggerBooksComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .booksModule(new BooksModule(new BookstoreModel(this), null))
                .build();
    }

    private void initActivity() {
//        booksComponent.inject(this);

        // Action bar
//        setSupportActionBar(toolbar);

        bookCatalogFragment = (BookCatalogFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_book_catalog);

        bookCatalogPresenter = new BookCatalogPresenter(new BookCatalogInteractor(new BookstoreModel(this)));
        bookCatalogPresenter.registerPresenterView(bookCatalogFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookCatalogPresenter.updateData();
    }

    @Override
    public void onAddToCartSelected(int bookPosition) {
        // TODO
    }
}
