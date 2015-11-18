package fr.enlight.henripotierbookshop.presentation.views.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCatalogPresenter;
import fr.enlight.henripotierbookshop.presentation.utils.GridInsetDecoration;
import fr.enlight.henripotierbookshop.presentation.views.adapters.BookCatalogAdapter;

/**
 * This fragment purpose is to presents the book catalog to the user.
 */
public class BookCatalogFragment extends AbstractFragment implements BookCatalogPresenter.BookCatalogPresentableView {

    private OnBookCatalogInteractionListener bookCatalogInteractionListener;

    // The binding is made in the parent class
    @Bind(R.id.recyclerview_book_grid)
    RecyclerView recyclerView;

    BookCatalogAdapter bookCatalogAdapter;

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
    protected void initViews(View view) {
        Context context = getActivity();

        recyclerView.addItemDecoration(new GridInsetDecoration(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, getResources().getInteger(R.integer.grid_layout_span)));

        // Set adapter
        bookCatalogAdapter = new BookCatalogAdapter(context);
        bookCatalogAdapter.setInteractionListener(bookCatalogInteractionListener);

        recyclerView.setAdapter(bookCatalogAdapter);
    }

    // region interaction listener

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnBookCatalogInteractionListener){
            bookCatalogInteractionListener = (OnBookCatalogInteractionListener) context;
        }
    }

    @Override
    public void updateBookCatalog(List<Book> bookList) {
        if(bookCatalogAdapter != null){
            bookCatalogAdapter.updateBookList(bookList);
            bookCatalogAdapter.notifyDataSetChanged();
        }
    }

    /**
     * A listener used by the parent Activity to react on user interaction with this fragment.
     */
    public interface OnBookCatalogInteractionListener {

        /**
         * Called when the user has asked a book to be added to the cart.
         * @param book the concerned book
         */
        void onAddToCartSelected(Book book);

        /**
         * Retry the loading of the book list
         */
        void onRetryBookLoading();
    }

    // endregion
}
