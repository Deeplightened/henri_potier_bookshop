package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BookCartComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BookCartModule;
import fr.enlight.henripotierbookshop.presentation.model.Book;
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
        BookCartComponent bookCartComponent = getBooksComponent().addModule(new BookCartModule());
        bookCartComponent.inject(this);
    }

    private void initActivity() {
        // Action bar
        setSupportActionBar(toolbar);

        bookCartFragment = (BookCartFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_book_cart);

        bookCartPresenter.setPresentableView(bookCartFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookCartPresenter.destroy();
    }

    @Override
    public void onRetry(Class<? extends AbstractFragment> fragmentOrigin) {
        bookCartPresenter.loadCartContent();
    }

    @Override
    public void onValidateCart() {
        int bookCount = bookCartPresenter.getCartBookCount();
        String messageDialog = getResources().getQuantityString(R.plurals.confirm_cart_message_dialog, bookCount, bookCount);

        showMessageCancelableDialog(getString(R.string.confirm_cart_title_dialog), messageDialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bookCartPresenter.clearCart();

                bookCartFragment.showLoadingView();

                // PLEASE READ : this operation is made only to simulate
                // an operation behind validation (the application "faking" a
                // book store)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String dialogTitle = getString(R.string.validated_cart_title_dialog);
                        String dialogMessage = getString(R.string.validated_cart_message_dialog);

                        navigationManager.finishActivityWithDialogResult(BookCartActivity.this, dialogTitle, dialogMessage, null);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onDeleteBookItem(Book bookItem) {
        bookCartPresenter.deleteBookItem(bookItem);
    }
}
