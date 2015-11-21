package fr.enlight.henripotierbookshop.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * Describe the book cart data, containing the list of books added to cart and the commercial offers
 * given for theses books.
 */
@Singleton
public class BookCartModel {

    private final List<Book> listBooks = new ArrayList<>();
    private List<BookOffer> offerList;

    /**
     * Calculate the raw total (without reduction) of all books cost in cart.
     * @return the concerned total
     */
    public double computeRawTotal() {
        double result = 0;
        for (Book book : listBooks) {
            result += book.getPrice();
        }
        return result;

    }

    /**
     * Calculate the real total (with reduction) of all books cost in cart using the reductions.
     * @return the real total
     */
    public double computeTotalWithOffers() {
        double result;
        double rawTotal = computeRawTotal();

        if(rawTotal <= 0){
            return 0;
        }

        result = rawTotal;

        float minusReduction = 0;

        for (BookOffer bookOffer : offerList) {
            String reductionType = bookOffer.getReductionType();

            if(reductionType.equals(BookOffer.MINUS_REDUCTION_TYPE)){
                minusReduction += bookOffer.getReductionValue();

            }
        }

        if(minusReduction > 0){
            result -= minusReduction;
        }
        // Result must always be positive
        result = Math.max(result, 0);

        return result;
    }

    // region getters and delegate methods

    public boolean addBook(Book object) {
        // If list already contains the book, we do nothing (only one item for each book).
        return !containsBook(object) && listBooks.add(object);
    }

    public boolean containsBook(Book object) {
        return listBooks.contains(object);
    }

    public boolean removeBook(Book object) {
        return listBooks.remove(object);
    }

    public void clearBookList() {
        listBooks.clear();
    }

    public List<Book> getListBooks() {
        return listBooks;
    }

    public List<BookOffer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<BookOffer> offerList) {
        this.offerList = offerList;
    }

    // endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookCartModel that = (BookCartModel) o;

        if (!listBooks.equals(that.listBooks)) return false;
        return !(offerList != null ? !offerList.equals(that.offerList) : that.offerList != null);

    }

    @Override
    public int hashCode() {
        int result = listBooks.hashCode();
        result = 71 * result + (offerList != null ? offerList.hashCode() : 0);
        return result;
    }

    public int bookListSize() {
        return listBooks.size();
    }
}
