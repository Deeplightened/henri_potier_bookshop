package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.app.Activity;
import android.content.Intent;

/**
 * This class provides methods to navigate through the application (activities start and fragment transaction).
 */
public class NavigationManager {

    public static final int BOOK_CART_ACTIVITY_REQUEST_CODE = 2864;

    /**
     * Start the BookCartActivity.
     *
     * @param activity the current activity
     */
    public void startBookCartActivity(Activity activity){
        Intent intent = BookCartActivity.newIntent(activity);
        activity.startActivityForResult(intent, BOOK_CART_ACTIVITY_REQUEST_CODE);
    }

    /**
     * Finish an activity and ask in the activity result to show a dialog box in the receiver activity.
     *
     * @param activity the current activity to finish
     * @param dialogTitle the title of the dialog to show
     * @param dialogMessage the title of the dialog to show
     * @param data the data intent created with some data inside. If there is no data to add, put this parameter to null
     */
    public void finishActivityWithDialogResult(Activity activity, String dialogTitle, String dialogMessage, Intent data) {
        if(data == null){
            data = new Intent();
        }
        data.putExtra(AbstractActivity.DIALOG_TITLE_RESULT_TAG, dialogTitle);
        data.putExtra(AbstractActivity.DIALOG_MESSAGE_RESULT_TAG, dialogMessage);

        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }
}
