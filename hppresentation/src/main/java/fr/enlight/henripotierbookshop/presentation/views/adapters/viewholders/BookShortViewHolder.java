package fr.enlight.henripotierbookshop.presentation.views.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.presentation.model.Book;

/**
 * Created by yhuriez on 17/11/2015.
 */
public class BookShortViewHolder extends RecyclerView.ViewHolder {

    public BookShortViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, itemView);
    }

    public void setBook(Book book){

    }
}
