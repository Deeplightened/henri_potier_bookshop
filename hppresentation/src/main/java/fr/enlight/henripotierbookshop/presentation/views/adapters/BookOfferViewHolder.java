package fr.enlight.henripotierbookshop.presentation.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;

/**
 * A ViewHolder used to contains
 */
public class BookOfferViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.book_cart_offer_message)
    TextView messageTextView;

    @Bind(R.id.book_cart_offer_value)
    TextView valueTextView;

    public BookOfferViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setBookOffer(BookOffer bookOffer){
        if(bookOffer != null){

            String offerMessage = bookOffer.getOfferMessage();
            String reductionMessage = bookOffer.getReductionMessage();

            if(offerMessage != null){
                messageTextView.setText(offerMessage);
            }
            if (reductionMessage != null) {
                valueTextView.setText(reductionMessage);
            }
        }
    }

}
