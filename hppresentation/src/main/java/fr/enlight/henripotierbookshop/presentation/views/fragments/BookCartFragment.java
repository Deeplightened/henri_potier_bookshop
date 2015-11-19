package fr.enlight.henripotierbookshop.presentation.views.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCartPresenter;
import fr.enlight.henripotierbookshop.presentation.views.adapters.BookCartAdapter;

/**
 * This fragment purpose is to presents the book catalog to the user.
 */
public class BookCartFragment extends AbstractFragment implements BookCartPresenter.BookCartPresentableView {

    private OnBookCartInteractionListener bookCartInteractionListener;

    // The binding is made in the parent class
    @Bind(R.id.recyclerview_book_cart)
    RecyclerView recyclerView;

    @Bind(R.id.book_cart_total_value)
    TextView totalValueTextView;

    @Bind(R.id.book_cart_tva_textview)
    TextView tvaTextView;


    BookCartAdapter bookCartAdapter;

    @Override
    protected int getLayoutInflateId() {
        return R.layout.fragment_book_cart;
    }

    @Override
    protected void initViews(View view) {
        Context context = getActivity();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

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

    @OnClick(R.id.book_cart_validate)
    public void onValidateClick(){
        if(bookCartInteractionListener != null){
            bookCartInteractionListener.onValidateCart();
        }
    }

    @Override
    public void updateCartContent(List<Book> cartBooks, List<BookOffer> bookOffers) {
        List<Object> cartItems = new ArrayList<>();
        cartItems.addAll(cartBooks);
        cartItems.addAll(bookOffers);

        bookCartAdapter.updateCartItemList(cartItems);
        bookCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCartTotalPrice(double total) {
        NumberFormat numberFormat = new DecimalFormat("#.00");
        String formattedTotal = numberFormat.format(total);
        totalValueTextView.setText(getString(R.string.country_currency_placeholder, formattedTotal));

        // Set TVA
        double tva = getResources().getInteger(R.integer.tva_percentage_per_100) / total;
        String formattedTva = numberFormat.format(tva);
        tvaTextView.setText(getString(R.string.book_cart_tva_placeholder, formattedTva));
    }

    /**
     * A listener used by the parent Activity to react on user interaction with this fragment.
     */
    public interface OnBookCartInteractionListener {

        /**
         * Called when the user has asked a book to be added to the cart.
         */
        void onValidateCart();

        /**
         * Called when the user has asked to delete a book from the cart.
         *
         * @param bookModel the concerned book to delete
         */
        void onDeleteBookItem(Book bookModel);
    }

    // endregion
}
