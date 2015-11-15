package fr.enlight.hpdata.entities;

import java.io.Serializable;

/**
 * A class defining a Henri Potier book entity.
 */
public class HPBook implements Serializable {

    private static final long serialVersionUID = 2322252399547132088L;

    private String isbn;
    private String title;
    private short price;
    private String cover;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getPrice() {
        return price;
    }

    public void setPrice(short price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
