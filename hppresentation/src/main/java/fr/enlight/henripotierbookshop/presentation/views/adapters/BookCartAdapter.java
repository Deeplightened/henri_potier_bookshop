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

    private final Context context;

    private List<Book> bookItemList;
    private List<BookOffer> bookOfferList;

    private BookCartFragment.OnBookCartInteractionListener interactionListener;

    public BookCartAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        if(isBookItemPosition(position)) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.cell_book_short, viewGroup, false);
            return new BookCartItemViewHolder(context, itemView);
        } else if(isBookOfferPosition(position)){
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.cell_book_short, viewGroup, false);
            return new BookOfferViewHolder(context, itemView);
        } else {
            throw new IllegalStateException("The data list doesn't match the given item count");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof BookCartItemViewHolder){
            if (bookItemList != null) {
                BookCartItemViewHolder bookCartHolder = (BookCartItemViewHolder) viewHolder;
                bookCartHolder.setBook(bookItemList.get(position));
                bookCartHolder.setInteractionListener(interactionListener);
            }
        } else if(viewHolder instanceof BookOfferViewHolder) {
            if (bookOfferList != null) {
                ((BookOfferViewHolder) viewHolder).setBookOffer(bookOfferList.get(position));
            }
        }
    }

    private boolean isBookItemPosition(int position){
        return position < bookItemList.size();
    }

    private boolean isBookOfferPosition(int position){
        return position >= bookItemList.size() && position < bookOfferList.size();
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if(bookItemList != null){
            itemCount += bookItemList.size();
        }
        if (bookOfferList != null) {
            itemCount += bookOfferList.size();
        }
        return itemCount;
    }

    /**
     * @param bookItemList the book list updated
     */
    public void updateBookItemList(List<Book> bookItemList) {
        this.bookItemList = bookItemList;
    }

    /**
     * @param bookOfferList the list of offers updated
     */
    public void updateBookOfferList(List<BookOffer> bookOfferList) {
        this.bookOfferList = bookOfferList;
    }

    /**
      * @param interactionListener the interaction listener
     */
    public void setInteractionListener(BookCartFragment.OnBookCartInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }
}
