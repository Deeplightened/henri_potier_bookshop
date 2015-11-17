package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.os.Bundle;

import fr.enlight.henripotierbookshop.R;


/**
 * Activity presenting the catalog of books available for this application.
 */
public class BookCatalogActivity extends AbstractHPActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_grid);
    }


}
