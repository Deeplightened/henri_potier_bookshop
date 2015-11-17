package fr.enlight.henripotierbookshop.presentation.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.views.adapters.viewholders.BookShortViewHolder;

/**
 * An adapter to presents a list of Book
 */
public class BookCatalogAdapter extends RecyclerView.Adapter<BookShortViewHolder>{

    private final LayoutInflater layoutInfalter;

    public BookCatalogAdapter(Activity activity){
        layoutInfalter = activity.getLayoutInflater();
    }

    @Override
    public BookShortViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View bookShortView = layoutInfalter.inflate(R.layout.cell_book_short, viewGroup, false);
        return new BookShortViewHolder(bookShortView);
    }

    @Override
    public void onBindViewHolder(BookShortViewHolder bookShortViewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
