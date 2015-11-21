package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        invalidateOptionsMenu();
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

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_book_catalog, menu);

        MenuItem item = menu.findItem(R.id.menu_cart_button);
        MenuItemCompat.setActionView(item, R.layout.view_cart_button);
        View view = MenuItemCompat.getActionView(item);

        TextView counterTextView = (TextView) view.findViewById(R.id.cart_book_counter_textview);
        ImageView logoTextView = (ImageView) view.findViewById(R.id.cart_icon_imageview);
        int bookCartSize = bookCatalogPresenter.getBookCartSize();
        if(bookCartSize == 0){
            counterTextView.setVisibility(View.INVISIBLE);
            logoTextView.setEnabled(false);
        } else {
            counterTextView.setVisibility(View.VISIBLE);
            counterTextView.setText(Integer.toString(bookCartSize));
            logoTextView.setEnabled(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigationManager.startBookCartActivity(BookCatalogActivity.this);
                }
            });
        }
        return true;
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
