package fr.enlight.henripotierbookshop.presentation.views.cart;

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

/**
 * A ViewHolder used to contains
 */
public class BookCartItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @Bind(R.id.book_cart_item_title)
    TextView titleTextView;

    @Bind(R.id.book_cart_item_isbn)
    TextView isbnTextView;

    @Bind(R.id.book_cart_item_price)
    TextView priceTextView;

    @Bind(R.id.book_cart_item_cover)
    ImageView coverImageView;

    private BookCartFragment.OnBookCartInteractionListener interactionListener;
    private Book bookModel;

    public BookCartItemViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setBook(Book book) {
        if (book != null) {

            bookModel = book;

            String title = book.getTitle();
            String isbn = book.getIsbn();
            short price = book.getPrice();
            String coverImageUrl = book.getCoverImageUrl();

            if (title != null) {
                titleTextView.setText(title);
            }
            if (price > 0) {
                priceTextView.setText(context.getString(R.string.country_currency_placeholder, price));
            }
            if (isbn != null) {
                isbnTextView.setText(context.getString(R.string.book_cart_isbn_placeholder, isbn));
            }

            if (coverImageUrl != null) {
                Picasso.with(context)
                        .load(coverImageUrl)
                        .fit()
                        .into(coverImageView);
            }
        }
    }

    @OnClick(R.id.book_cart_item_delete)
    public void onDeleteItemClicked() {
        if (interactionListener != null) {
            interactionListener.onDeleteBookItem(bookModel);
        }
    }

    public void setInteractionListener(BookCartFragment.OnBookCartInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }
}
