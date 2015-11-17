package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;

/**
 * A Dagger module that provides an activity context.
 */
@Module
public class ActivityModule {

    private Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Provides @ActivityScope Context providesContext(){
        return context;
    }
}
