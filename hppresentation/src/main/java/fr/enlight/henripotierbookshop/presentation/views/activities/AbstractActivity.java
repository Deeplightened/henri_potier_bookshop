package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import fr.enlight.henripotierbookshop.presentation.HPBookshopApplication;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.ApplicationComponent;

/**
 * The base for every Activity used in this application
 */
public abstract class AbstractActivity extends AppCompatActivity {

    @Inject
    NavigationManager navigationManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getApplicationComponent().inject(this);
    }

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
}
