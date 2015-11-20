package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import fr.enlight.henripotierbookshop.presentation.HPBookshopApplication;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.ApplicationComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BooksComponent;

/**
 * The base for every Activity used in this application
 */
public abstract class AbstractActivity extends AppCompatActivity {

    public static final String DIALOG_TITLE_RESULT_TAG = "DIALOG_TITLE_RESULT";
    public static final String DIALOG_MESSAGE_RESULT_TAG = "DIALOG_MESSAGE_RESULT";

    @Inject
    NavigationManager navigationManager;

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the ApplicationComponent that allows dependencies injections of this application most
     * used services, using Dagger.
     *
     * @return the concerned ApplicationComponent
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((HPBookshopApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get the BooksComponent that allows dependencies injections of the needed books services, using Dagger.
     *
     * @return the concerned BooksComponent
     */
    protected BooksComponent getBooksComponent() {
        return ((HPBookshopApplication) getApplication()).getBooksComponent();
    }


    /**
     *  Redefined to verify if the previous activity has sended dialog informations.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null){
            String dialogTitle = data.getStringExtra(DIALOG_TITLE_RESULT_TAG);
            String dialogMessage = data.getStringExtra(DIALOG_MESSAGE_RESULT_TAG);
            if(dialogTitle != null && dialogMessage != null){
                showMessageDialog(dialogTitle, dialogMessage, null);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Show an message dialog box
     *
     * @param message  the message shown in the dialog box
     * @param listener the listener called when the user push the 'OK' button
     */
    public void showMessageDialog(final String title, final String message, final DialogInterface.OnClickListener listener) {
        if (! isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("OK", listener);
            alertDialog.show();
        }
    }

    /**
     * Show an dialog box, in function of the error message
     *
     * @param message the error message shown in the dialog box
     * @param listener the listener called when the user push the 'OK' button
     */
    public void showMessageCancelableDialog(final String title, final String message, final DialogInterface.OnClickListener listener) {
        if (! isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("OK", listener);
            alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}
