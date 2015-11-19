package fr.enlight.henripotierbookshop.presentation.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;
import fr.enlight.henripotierbookshop.presentation.views.fragments.BookCartFragment;

/**
 * An adapter to presents a list of Book
 */
public class BookCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int BOOK_ITEM_VIEWTYPE = 0;
    public static final int OFFER_ITEM_VIEWTYPE = 1;

    private final Context context;

    private List cartItems;

    private BookCartFragment.OnBookCartInteractionListener interactionListener;

    public BookCartAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == BOOK_ITEM_VIEWTYPE) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.cell_book_cart_item, viewGroup, false);
            return new BookCartItemViewHolder(context, itemView);
        } else if(viewType == OFFER_ITEM_VIEWTYPE){
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.cell_book_cart_offer, viewGroup, false);
            return new BookOfferViewHolder(itemView);
        } else {
            throw new IllegalStateException("The data list doesn't match the given item count");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(cartItems != null) {
            if (viewHolder instanceof BookCartItemViewHolder) {
                BookCartItemViewHolder bookCartHolder = (BookCartItemViewHolder) viewHolder;
                bookCartHolder.setBook((Book) cartItems.get(position));
                bookCartHolder.setInteractionListener(interactionListener);
            } else if (viewHolder instanceof BookOfferViewHolder) {
                ((BookOfferViewHolder) viewHolder).setBookOffer((BookOffer) cartItems.get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object cartItem = cartItems.get(position);
        if(cartItem.getClass() == Book.class){
            return BOOK_ITEM_VIEWTYPE;
        } else if (cartItem.getClass() == BookOffer.class){
            return OFFER_ITEM_VIEWTYPE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if(cartItems == null){
            return 0;
        }
        return cartItems.size();
    }

    /**
     * @param cartItems the cart items to update
     */
    public void updateCartItemList(List cartItems) {
        this.cartItems = cartItems;
    }

    /**
      * @param interactionListener the interaction listener
     */
    public void setInteractionListener(BookCartFragment.OnBookCartInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }
}
