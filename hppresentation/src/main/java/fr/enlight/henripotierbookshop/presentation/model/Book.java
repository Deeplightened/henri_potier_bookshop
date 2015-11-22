package fr.enlight.henripotierbookshop.presentation.model;

/**
 * An entity representing a book.
 */
public class Book {

    private String title;
    private String coverImageUrl;
    private short price;
    private String isbn;
    private boolean inCart = false;

    public static Book newInstance(String title, String cover, short price, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setCoverImageUrl(cover);
        book.setIsbn(isbn);
        book.setPrice(price);
        return book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public short getPrice() {
        return price;
    }

    public void setPrice(short price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return !(isbn != null ? !isbn.equals(book.isbn) : book.isbn != null);

    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }
}
