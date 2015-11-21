package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BookCatalogComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BookCatalogModule;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCatalogPresenter;
import fr.enlight.henripotierbookshop.presentation.views.fragments.AbstractFragment;
import fr.enlight.henripotierbookshop.presentation.views.fragments.BookCatalogFragment;


/**
 * Activity presenting the catalog of books available for this application.
 */
public class BookCatalogActivity extends AbstractActivity implements BookCatalogFragment.OnBookCatalogInteractionListener, AbstractFragment.OnRetryListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    BookCatalogPresenter bookCatalogPresenter;

    // Internal fragment, contained in the activity_book_catalog layout
    BookCatalogFragment bookCatalogFragment;

    private boolean cartActivated = false;

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
        BookCatalogComponent bookCatalogComponent = getBooksComponent().addModule(new BookCatalogModule());
        bookCatalogComponent.inject(this);
    }

    @SuppressWarnings("ConstantConditions")
    private void initActivity() {
        // Action bar
        toolbar.setTitle(getString(R.string.book_catalog_toolbar_title));
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);

        bookCatalogFragment = (BookCatalogFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_book_catalog);

        bookCatalogPresenter.setPresentableView(bookCatalogFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookCatalogPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bookCatalogPresenter.pause();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem cartItem = menu.getItem(0);
        if(cartItem != null){
            cartItem.setEnabled(cartActivated);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAddToCartSelected(Book book) {
        bookCatalogPresenter.addToCart(book);
        if(bookCatalogPresenter.getBookCartSize() > 0){
            cartActivated = true;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onRetry(Class<? extends AbstractFragment> fragmentOrigin) {
        bookCatalogPresenter.updateBookCatalog();
    }
}
