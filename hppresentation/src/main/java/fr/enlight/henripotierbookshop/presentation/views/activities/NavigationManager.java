package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

/**
 * This class provides methods to navigate through the application (activities start and fragment transaction).
 */
public class NavigationManager {

    @Inject
    public NavigationManager(){}

    /**
     * Start the BookCartActivity.
     *
     * @param context the current context
     */
    public void startBookCart(Context context){
        Intent intent = BookCartActivity.newIntent(context);
        context.startActivity(intent);
    }
}
