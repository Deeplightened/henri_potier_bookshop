package fr.enlight.henripotierbookshop.presentation.views.catalog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.views.catalog.BookCatalogFragment;

/**
 * A ViewHolder used to contains
 */
public class BookCatalogViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @Bind(R.id.book_title_textview)
    TextView titleTextView;

    @Bind(R.id.book_price_textview)
    TextView priceTextView;

    @Bind(R.id.book_cover_imageview)
    ImageView coverImageView;

    @Bind(R.id.book_add_to_cart_button)
    TextView addToCartButton;


    private BookCatalogFragment.OnBookCatalogInteractionListener interactionListener;
    private Book bookModel;

    public BookCatalogViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setBook(Book book){
        if(book != null){

            bookModel = book;

            String title = book.getTitle();
            short price = book.getPrice();
            final String coverImageUrl = book.getCoverImageUrl();

            if(title != null){
                titleTextView.setText(title);
            }
            if(price > 0){
                priceTextView.setText(context.getString(R.string.country_currency_placeholder, price));
            }

            if(coverImageUrl != null) {
                Picasso.with(context)
                        .load(coverImageUrl)
                        .fit()
                        .into(coverImageView);
            }

            updateCartButton();
        }
    }

    @OnClick(R.id.book_add_to_cart_button)
    public void onAddToCartClicked(){
        if(interactionListener != null){
            interactionListener.onAddToCartSelected(bookModel);
            bookModel.setInCart(true);
            updateCartButton();
        }
    }

    private void updateCartButton() {
        if(bookModel.isInCart()){
            addToCartButton.setEnabled(false);
            addToCartButton.setText(context.getString(R.string.book_catalog_added_to_cart));
        } else {
            addToCartButton.setEnabled(true);
            addToCartButton.setText(context.getString(R.string.book_catalog_add_to_cart));
        }
    }

    public void setInteractionListener(BookCatalogFragment.OnBookCatalogInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }
}
