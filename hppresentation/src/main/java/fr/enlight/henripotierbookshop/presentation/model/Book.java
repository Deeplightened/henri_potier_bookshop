package fr.enlight.henripotierbookshop.presentation.model;

/**
 * Created by yhuriez on 17/11/2015.
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

}
