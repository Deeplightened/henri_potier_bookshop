package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.content.Context;
import android.content.Intent;

/**
 * Activity presenting the user cart, containing the reserved books.
 */
public class BookCartActivity extends AbstractActivity {

    /**
     * Creates the intent to start this activity.
     *
     * @param context the context from where the activity must be started
     * @return the concerned intent
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, BookCartActivity.class);
    }


}
