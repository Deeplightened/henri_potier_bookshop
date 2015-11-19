package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import fr.enlight.henripotierbookshop.presentation.HPBookshopApplication;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.ApplicationComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BooksComponent;

/**
 * The base for every Activity used in this application
 */
public abstract class AbstractActivity extends AppCompatActivity {

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
}
