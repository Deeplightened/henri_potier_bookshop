package fr.enlight.henripotierbookshop.presentation.views.cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCartPresenter;
import fr.enlight.henripotierbookshop.presentation.views.base.AbstractFragment;
import fr.enlight.henripotierbookshop.presentation.views.widget.ItemDividerDecorator;

/**
 * This fragment purpose is to presents the book catalog to the user.
 */
public class BookCartFragment extends AbstractFragment implements BookCartPresenter.BookCartPresentableView {

    private OnBookCartInteractionListener bookCartInteractionListener;

    // The binding is made in the parent class
    @Bind(R.id.recyclerview_book_cart)
    RecyclerView recyclerView;

    @Bind(R.id.book_cart_total_viewgroup)
    ViewGroup totalViewGroup;

    @Bind(R.id.book_cart_total_value)
    TextView totalValueTextView;

    @Bind(R.id.book_cart_tva_textview)
    TextView tvaTextView;

    @Bind(R.id.book_cart_empty_viewgroup)
    ViewGroup emptyViewGroup;

    @Bind(R.id.book_cart_total_offer_viewgroup)
    ViewGroup offerListViewGroup;

    // Book adapter
    BookCartAdapter bookCartAdapter;

    // Progress dialog for loading after first one
    ProgressDialog progressDialog;

    // A boolean determining if the asked loading is the first one or not
    boolean firstLoading = true;

    @Override
    protected int getLayoutInflateId() {
        return R.layout.fragment_book_cart;
    }

    @Override
    protected void initViews(View view) {
        Context context = getActivity();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new ItemDividerDecorator(getActivity(), ItemDividerDecorator.VERTICAL_LIST,
                ContextCompat.getDrawable(getContext(), R.drawable.transparent_divider)));

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
        if(cartBooks.isEmpty() || bookOffers.isEmpty()){
            showEmptyView();

        } else {
            hideEmptyView();

            bookCartAdapter.updateCartItemList(cartBooks);
            bookCartAdapter.notifyDataSetChanged();

            updateBookOffersViews(bookOffers);
        }
    }

    /**
     * Show an information panel to inform the user his cart is empty.
     */
    private void showEmptyView() {
        totalViewGroup.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyViewGroup.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the cart empty information panel and show the other view group.
     */
    private void hideEmptyView() {
        emptyViewGroup.setVisibility(View.GONE);
        totalViewGroup.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Update the list of book offer in the total view, using the list of books model.
     *
     * @param bookOffers the book offer model
     */
    private void updateBookOffersViews(List<BookOffer> bookOffers) {
        // First we clear all views in the offer view group
        offerListViewGroup.removeAllViews();

        // Then we creates all corresponding view offers
        View offerView;
        for (BookOffer bookOffer : bookOffers) {
            offerView = LayoutInflater.from(getActivity()).inflate(R.layout.cell_book_cart_offer, offerListViewGroup, false);

            TextView offerMessage = (TextView) offerView.findViewById(R.id.book_cart_offer_message);
            TextView offerValue = (TextView) offerView.findViewById(R.id.book_cart_offer_value);

            offerMessage.setText(bookOffer.getOfferMessage());
            offerValue.setText(bookOffer.getReductionMessage());

            offerListViewGroup.addView(offerView);
        }
    }

    @Override
    public void updateCartTotalPrice(double total, double tva) {
        NumberFormat numberFormat = new DecimalFormat("#.00");
        String formattedTotal = numberFormat.format(total);
        totalValueTextView.setText(getString(R.string.country_currency_placeholder, formattedTotal));

        // Set TVA
        String formattedTva = numberFormat.format(tva);
        tvaTextView.setText(getString(R.string.book_cart_tva_placeholder, formattedTva));
    }

    @Override
    public void showLoadingView() {
        if(firstLoading){
            super.showLoadingView();
        } else {
            if(progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.loading_message_dialog), false, false, null);
            }
        }
    }

    @Override
    public void hideLoadingView() {
        if(firstLoading){
            super.hideLoadingView();
            firstLoading = false;
        } else {
            if(progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    public void onLoadingFailed(String errorMessage) {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onLoadingFailed(errorMessage);
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
