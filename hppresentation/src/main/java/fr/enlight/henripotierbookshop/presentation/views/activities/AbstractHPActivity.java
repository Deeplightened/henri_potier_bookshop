package fr.enlight.henripotierbookshop.presentation.views.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import fr.enlight.henripotierbookshop.presentation.HPBookshopApplication;

/**
 * The base for every Activity used in this application
 */
public abstract class AbstractHPActivity extends AppCompatActivity {

    @Inject NavigationManager navigationManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initInjection();
    }

    private void initInjection() {
        HPBookshopApplication.getApplicationComponent().inject(this);
    }
}
