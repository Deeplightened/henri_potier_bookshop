package fr.enlight.henripotierbookshop.presentation.views.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;

/**
 * An adapter to presents a list of Book
 */
public class BookCartAdapter extends RecyclerView.Adapter<BookCartItemViewHolder>{

    private final Context context;

    private List<Book> cartItems;

    private BookCartFragment.OnBookCartInteractionListener interactionListener;

    public BookCartAdapter(Context context){
        this.context = context;
    }

    @Override
    public BookCartItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.cell_book_cart_item, viewGroup, false);
        return new BookCartItemViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(BookCartItemViewHolder bookCartHolder, int position) {
        if (cartItems != null) {
            bookCartHolder.setBook(cartItems.get(position));
            bookCartHolder.setInteractionListener(interactionListener);
        }
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
    public void updateCartItemList(List<Book> cartItems) {
        this.cartItems = cartItems;
    }

    /**
      * @param interactionListener the interaction listener
     */
    public void setInteractionListener(BookCartFragment.OnBookCartInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }
}
