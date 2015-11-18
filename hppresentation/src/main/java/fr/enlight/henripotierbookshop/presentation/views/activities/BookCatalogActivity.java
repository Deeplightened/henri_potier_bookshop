package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.DaggerBooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;
import fr.enlight.henripotierbookshop.presentation.model.Book;
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

    // Internal fragment, contained in the activity_book_catalog layout
    BookCatalogFragment bookCatalogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalog);

        ButterKnife.bind(this);

        initInjection();
        initActivity();

        bookCatalogPresenter.create();
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

        bookCatalogPresenter.setPresentableView(bookCatalogFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookCatalogPresenter.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_cart_button){
            // On cart icon clicked, we redirect to the BookCartActivity
            navigationManager.startBookCartActivity(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddToCartSelected(Book book) {
        bookCatalogPresenter.addToCart(book);
    }

    @Override
    public void onRetryBookLoading() {
        bookCatalogPresenter.updateBookCatalog();
    }
}
