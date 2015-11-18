package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.DaggerBooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCatalogPresenter;
import fr.enlight.henripotierbookshop.presentation.views.fragments.BookCatalogFragment;


/**
 * Activity presenting the catalog of books available for this application.
 */
public class BookCatalogActivity extends AbstractActivity implements BookCatalogFragment.OnBookCatalogInteractionListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
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
        BooksComponent booksComponent = DaggerBooksComponent.builder()
                .applicationModule(getApplicationModule())
                .booksModule(new BooksModule())
                .build();

        booksComponent.inject(this);
    }

    private void initActivity() {
        // Action bar
        setSupportActionBar(toolbar);

        bookCatalogFragment = (BookCatalogFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_book_catalog);

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
