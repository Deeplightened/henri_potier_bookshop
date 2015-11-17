package fr.enlight.henripotierbookshop.presentation.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;

/**
 * A ViewHolder used to contains
 */
public class BookShortViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @Bind(R.id.book_title_textview)
    TextView titleTextView;

    @Bind(R.id.book_price_textview)
    TextView priceTextView;

    @Bind(R.id.book_cover_imageview)
    ImageView coverImageView;

    public BookShortViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setBook(Book book){
        if(book != null){
            String title = book.getTitle();
            short price = book.getPrice();
            String coverImageUrl = book.getCoverImageUrl();

            if(title != null){
                titleTextView.setText(title);
            }
            if(price > 0){
                priceTextView.setText(context.getString(R.string.country_currency_placeholder, price));
            }

            if(coverImageUrl != null){
                Picasso.with(context)
                        .load(coverImageUrl)
                        .into(coverImageView);
            }
        }
    }

}
