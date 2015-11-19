package fr.enlight.henripotierbookshop.presentation.views.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCartPresenter;
import fr.enlight.henripotierbookshop.presentation.utils.GridInsetDecoration;
import fr.enlight.henripotierbookshop.presentation.views.adapters.BookCartAdapter;

/**
 * This fragment purpose is to presents the book catalog to the user.
 */
public class BookCartFragment extends AbstractFragment implements BookCartPresenter.BookCartPresentableView {

    private OnBookCartInteractionListener bookCartInteractionListener;

    // The binding is made in the parent class
//    @Bind(R.id.recyclerview_book_list_cart)
    RecyclerView recyclerView;

    BookCartAdapter bookCartAdapter;

    @Override
    protected int getLayoutInflateId() {
        return R.layout.fragment_book_cart;
    }

    @Override
    protected void initViews(View view) {
        Context context = getActivity();

        recyclerView.addItemDecoration(new GridInsetDecoration(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, getResources().getInteger(R.integer.grid_layout_span)));

        // Set adapter
        bookCartAdapter = new BookCartAdapter(context);
        bookCartAdapter.setInteractionListener(bookCartInteractionListener);

        recyclerView.setAdapter(bookCartAdapter);
    }

    // region interaction listener

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnBookCartInteractionListener){
            bookCartInteractionListener = (OnBookCartInteractionListener) context;
        }
    }

    @Override
    public void updateCartContent(List<Book> cartBooks) {

    }

    @Override
    public void updateCommercialOffers(List<BookOffer> bookOffers) {

    }

    @Override
    public void updateCartTotalPrice(double total) {

    }

    /**
     * A listener used by the parent Activity to react on user interaction with this fragment.
     */
    public interface OnBookCartInteractionListener {

        /**
         * Called when the user has asked a book to be added to the cart.
         */
        void onValidateCart();
    }

    // endregion
}
