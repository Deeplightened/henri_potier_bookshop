package fr.enlight.henripotierbookshop.presentation.model;

import fr.enlight.hpdata.hpbooks.entities.HPBook;

/**
 * Created by yhuriez on 17/11/2015.
 */
public class Book {

    private String title;
    private String coverImageUrl;
    private short price;
    private String isbn;

    public static Book newInstance(HPBook hpBook) {
        Book book = new Book();
        book.setTitle(hpBook.getTitle());
        book.setCoverImageUrl(hpBook.getCover());
        book.setIsbn(hpBook.getIsbn());
        book.setPrice(hpBook.getPrice());
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
}
