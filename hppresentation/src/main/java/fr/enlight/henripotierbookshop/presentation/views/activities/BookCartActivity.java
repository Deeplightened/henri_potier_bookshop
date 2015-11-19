package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.content.Context;
import android.content.Intent;
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
import fr.enlight.henripotierbookshop.presentation.presenter.BookCartPresenter;
import fr.enlight.henripotierbookshop.presentation.views.fragments.AbstractFragment;
import fr.enlight.henripotierbookshop.presentation.views.fragments.BookCartFragment;

/**
 * Activity presenting the user cart, containing the reserved books.
 */
public class BookCartActivity extends AbstractActivity implements BookCartFragment.OnBookCartInteractionListener, AbstractFragment.OnRetryListener {

    /**
     * Creates the intent to start this activity.
     *
     * @param context the context from where the activity must be started
     * @return the concerned intent
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, BookCartActivity.class);
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    BookCartPresenter bookCartPresenter;

    // Internal fragment, contained in the activity_book_cart layout
    BookCartFragment bookCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cart);

        ButterKnife.bind(this);

        initInjection();
        initActivity();

        bookCartPresenter.create();
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

        bookCartFragment = (BookCartFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_book_catalog);

        bookCartPresenter.setPresentableView(bookCartFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookCartPresenter.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO

//        if(item.getItemId() == R.id.menu_cart_previous){
//            // On cart icon clicked, we redirect to the BookCartActivity
//            navigationManager.startBookCartActivity(this);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRetry(Class<? extends AbstractFragment> fragmentOrigin) {

    }

    @Override
    public void onValidateCart() {

    }
}
