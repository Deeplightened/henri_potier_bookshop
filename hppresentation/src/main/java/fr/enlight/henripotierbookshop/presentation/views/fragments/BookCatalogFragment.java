package fr.enlight.henripotierbookshop.presentation.views.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import fr.enlight.henripotierbookshop.R;

/**
 * This fragment purpose is to presents the book catalog to the user.
 *
 */
public class BookCatalogFragment extends AbstractHPFragment {

    private OnBookCatalogInteractionListener bookCatalogInteractionListener;

    @Bind(R.id.recyclerview_book_grid)
    RecyclerView recyclerView;

    /**
     * Creates a new instance of this fragment.
     *
     * @return A new instance of BookCatalogFragment.
     */
    public static BookCatalogFragment newInstance() {
        return new BookCatalogFragment();
    }

    @Override
    protected int getLayoutInflateId() {
        return R.layout.fragment_book_catalog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnBookCatalogInteractionListener){
            bookCatalogInteractionListener = (OnBookCatalogInteractionListener) context;
        }
    }

    /**
     * Notify the bookCatalogInteractionListener that the user asked a book to be added to the cart.
     *
     * @param bookPosition the position of the concerned book.
     */
    private void notifyAddToCartSelected(int bookPosition){
        if(bookCatalogInteractionListener != null){
            bookCatalogInteractionListener.onAddToCartSelected(bookPosition);
        }
    }

    /**
     * A listener used by the parent Activity to react on user interaction with this fragment.
     */
    public interface OnBookCatalogInteractionListener {

        /**
         * Called when the user has asked a book to be added to the cart.
         * @param bookPosition the position of the concerned book
         */
        void onAddToCartSelected(int bookPosition);
    }

}
