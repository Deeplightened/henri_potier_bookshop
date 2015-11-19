package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.content.Context;
import android.content.Intent;

/**
 * This class provides methods to navigate through the application (activities start and fragment transaction).
 */
public class NavigationManager {

    /**
     * Start the BookCartActivity.
     *
     * @param context the current context
     */
    public void startBookCartActivity(Context context){
        Intent intent = BookCartActivity.newIntent(context);
        context.startActivity(intent);
    }
}
